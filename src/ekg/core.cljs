(ns ekg.core
  (:require [reagent.core :as r]
            [ekg.state :refer [state
                               reset-history
                               cursor-for
                               get-history-as-str
                               add-current-to-history
                               current-already-saved?
                               init-current!
                               records-count]]
            [ekg.utils :refer [value-from
                               parse-file-reader-result
                               download-as-file
                               generate-filename
                               with-confirm]]))

(enable-console-print!)

(defn TextRow [label path]
  (let [value* (cursor-for path)]
    (fn []
      [:tr
       [:td label]
       [:td
        [:input {:type "text"
                 :value @value*
                 :on-change #(reset! value* (value-from %))}]]])))

(defn Upload []
  (let [ref* (r/atom nil)
        capture-ref #(reset! ref* %)
        change-input (fn [event]
                       (let [file (-> event .-target .-files (aget 0))
                             reader (new js/FileReader)]
                         (set! (.-onload reader) #(-> % parse-file-reader-result reset-history))
                         (.readAsText reader file)
                         (set! (.-value @ref*) nil)))]
    (fn []
      [:input.non-printable {:type "file"
                             :accept ".json"
                             :ref capture-ref
                             :on-change change-input}])))

(defn Download []
  [:button.non-printable {:on-click #(download-as-file
                                      (generate-filename)
                                      (get-history-as-str))}
   "Сохранить базу"])

(defn Save []
  [:button.non-printable {:on-click #(add-current-to-history)
                          :disabled @current-already-saved?}
   "Сохранить в базу"])

(defn Print []
  [:button.non-printable {:on-click #(.print js/window)}
   "Распечатать"])

(defn Clear []
  [:button.non-printable {:on-click (with-confirm "Точно очистить?" init-current!)}
   "Очистить"])

(defn DatabaseCounter []
  [:div.non-printable (str "В базе " @records-count " записей")])

(defn app []
  [:div.page
   [Upload]
   [Download]
   [Save]
   [Print]
   [Clear]
   [DatabaseCounter]
   [:img {:src "/images/header.png"}]
   [:table
    [:tbody
     [TextRow "P (+)" [:P+]]
     [TextRow "P (-)" [:P-]]]]
   [:img {:src "/images/footer.png"}]])

(r/render [app] (.getElementById js/document "app"))
