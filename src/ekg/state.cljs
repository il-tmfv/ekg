(ns ekg.state
  (:require [reagent.core :as r]))

(def ^:private initial-current-state {:P+ ""
                                      :P- ""})

(defonce state (r/atom {:history []
                        :current initial-current-state}))

(defn init-current []
  (swap! state assoc-in [:current] initial-current-state))

(defn cursor-for [path]
  (let [full-path (into [:current] path)]
    (r/cursor state full-path)))
