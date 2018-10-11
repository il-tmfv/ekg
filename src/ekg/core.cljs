(ns ekg.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(println "This text is printed from src/ekg/core.cljs. Go ahead and edit it and see reloading in action.")

(defonce app-state (r/atom {}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(defn TextRow [label value*]
  [:tr
   [:td label]
   [:td
    [:input {:type "text" :on-change #(reset! value* (-> % .-target .-value))}]]])

(defn app []
  [:div.page
   [:img {:src "/images/header.png"}]
   [:table
    [:tbody
     [TextRow "P (+)" (r/atom "")]
     [TextRow "P (-)" (r/atom "")]]]
   [:img {:src "/images/footer.png"}]])

(r/render [app] (.getElementById js/document "app"))
