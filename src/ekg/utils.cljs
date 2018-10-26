(ns ekg.utils)

(defn value-from [event]
  (-> event .-target .-value))
