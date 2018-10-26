(ns ekg.core
  (:require [reagent.core :as r]
            [ekg.state :refer [state reset-history cursor-for]]
            [ekg.utils :refer [value-from parse-file-reader-result]]))

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

(defn app []
  [:div.page
   [Upload]
   [:img {:src "/images/header.png"}]
   [:table
    [:tbody
     [TextRow "P (+)" [:P+]]
     [TextRow "P (-)" [:P-]]]]
   [:img {:src "/images/footer.png"}]])

(r/render [app] (.getElementById js/document "app"))
