(ns ekg.core
  (:require [reagent.core :as r]
            [ekg.state :refer [state cursor-for]]
            [ekg.utils :refer [value-from]]))

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

(defn app []
  [:div.page
   [:img {:src "/images/header.png"}]
   [:table
    [:tbody
     [TextRow "P (+)" [:P+]]
     [TextRow "P (-)" [:P-]]]]
   [:img {:src "/images/footer.png"}]])

(r/render [app] (.getElementById js/document "app"))
