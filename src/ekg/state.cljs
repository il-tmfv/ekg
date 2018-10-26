(ns ekg.state
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]))

(def ^:private initial-current-state {:P+ ""
                                      :P- ""})

(defonce state (r/atom {:history []
                        :current initial-current-state}))

(defn init-current! []
  (swap! state assoc-in [:current] initial-current-state))

(defn reset-history [new-history]
  (swap! state assoc-in [:history] new-history))

(defn get-history-as-str []
  (-> (get @state :history)
      clj->js
      js/JSON.stringify))

(defn cursor-for [path]
  (let [full-path (into [:current] path)]
    (r/cursor state full-path)))

(defn add-current-to-history []
  (let [current (:current @state)]
    (swap! state update-in [:history] #(conj % current))))

(def current-already-saved?
  (reaction (let [current (:current @state)
                  history (:history @state)]
              (some #(= % current) history))))
