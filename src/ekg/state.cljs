(ns ekg.state
  (:require [reagent.core :as r]))

(def ^:private initial-current-state {:P+ ""
                                      :P- ""})

(defonce state (r/atom {:history []
                        :current initial-current-state}))

(defn init-current []
  (swap! state assoc-in [:current] initial-current-state))

(defn reset-history [new-history]
  (println new-history)
  (swap! state assoc-in [:histoty] new-history))

(defn cursor-for [path]
  (let [full-path (into [:current] path)]
    (r/cursor state full-path)))
