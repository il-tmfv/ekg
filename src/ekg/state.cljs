(ns ekg.state
  (:require [reagent.core :as r]))

(defonce state (r/atom {:history []
                        :current {}}))

(def ^:private initial-current-state {:P+ ""
                                      :P- ""})

(defn init-current []
  (swap! state assoc-in [:current] initial-current-state))

(defn cursor-for [path]
  (let [full-path (into [:current] path)]
    (r/cursor state full-path)))
