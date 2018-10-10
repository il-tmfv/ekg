(ns ekg.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(println "This text is printed from src/ekg/core.cljs. Go ahead and edit it and see reloading in action.")

(defonce app-state (r/atom {:a 0 :b 0 :res 0}))

(defonce a (r/cursor app-state [:a]))
(defonce b (r/cursor app-state [:b]))
(defonce res (r/cursor app-state [:res]))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(defn- calc []
  (reset! res (+ (js/parseFloat @a) (js/parseFloat @b))))

(defn app []
  [:div {:style {:display "flex" :flex-direction "column" :width 300}}
   [:span (str "a + b = " @res)]
   [:input {:type "number"
            :value @a
            :on-change (fn [e]
                         (reset! a (-> e .-target .-value)))}]
   [:input {:type "number"
            :value @b
            :on-change (fn [e]
                         (reset! b (-> e .-target .-value)))}]
   [:button {:on-click calc} "Расчитать"]])

(r/render [app] (.getElementById js/document "app"))
