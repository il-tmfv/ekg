(ns ekg.utils)

(defn value-from [event]
  (-> event .-target .-value))

(defn parse-file-reader-result [result]
  (-> result
      .-target
      .-result
      js/JSON.parse
      (js->clj {:keywordize-keys true})))