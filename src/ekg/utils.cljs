(ns ekg.utils)

(defn value-from [event]
  (-> event .-target .-value))

(defn parse-file-reader-result [result]
  (-> result
      .-target
      .-result
      js/JSON.parse
      (js->clj {:keywordize-keys true})))

(defn generate-filename []
  (let [timestamp (-> js/Date new .toISOString)]
    (str "base_" timestamp "_.json")))

(defn download-as-file [filename text]
  (let [element (.createElement js/document "a")
        href (str "data:application/json;charset=utf-8," (js/encodeURIComponent text))]
    (.setAttribute element "href" href)
    (.setAttribute element "download" filename)
    (set! (-> element .-style .-display) "none")
    (-> js/document .-body (.appendChild element))
    (.click element)
    (-> js/document .-body (.removeChild element))))
