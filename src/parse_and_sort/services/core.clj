(ns parse-and-sort.services.core
  (:require [clojure.string :as s]
            [clj-time.format :as f]))

(defonce state (atom #{}))

(def custom-formatter (f/formatter "MM/DD/YYY"))

;;normalize str
;;restructure str
(defn handle-str [file]
  (as-> file f
    (s/split f #"\n")
    (map #(s/split % #" ") f)))


(defn compare-dates [one two]
  (compare
    (f/parse custom-formatter one)
    (f/parse custom-formatter two)))


(defn output-set [data]

  ;Output 1 – sorted by gender (females before males) then by last name ascending. A -> Z
  (sort-by (juxt :Gender :LastName) data)


  ;;bring in clj time
  ;;Output 2 – sorted by birth date, ascending.
  ;; oldest -> most recent
  ;;oldest first
  (sort-by :DateOfBirth #(compare-dates %1 %2) data)

  ;Output 3 – sorted by last name, descending. Z -> A
  (sort-by :LastName #(compare %2 %1) data)
  )


(defn create-set [keys data]
  (into #{}
    (map #(zipmap keys %) data)))

(defn create-and-output [text]
  (let [[header & data] (handle-str text)
        keys (map #(keyword %) header)
        result (create-set keys data)]
    (reset! state result)

    ))