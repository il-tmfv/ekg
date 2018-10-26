(ns ekg.state
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]))

(def ^:private initial-current-state {:P+ ""
                                      :P- ""})

(defonce state (r/atom {:history []
                        :current initial-current-state}))

(def history (r/cursor state [:history]))
(def current (r/cursor state [:current]))

(defn init-current! []
  (reset! current initial-current-state))

(defn reset-history [new-history]
  (reset! history new-history))

(defn get-history-as-str []
  (-> @history
      clj->js
      js/JSON.stringify))

(defn cursor-for [path]
  (let [full-path (into [:current] path)]
    (r/cursor state full-path)))

(defn add-current-to-history []
  (swap! history #(conj % @current)))

(def current-already-saved?
  (reaction (some #(= % @current) @history)))

(def records-count
  (reaction (count @history)))
