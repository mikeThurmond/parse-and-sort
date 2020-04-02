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

(defn sort-gender->last-asc [data]
  (sort-by (juxt :Gender :LastName) data))

(defn sort-dob-asc [data]
  (sort-by :DateOfBirth #(compare-dates %1 %2) data))

(defn sort-lastname-desc [data]
  (sort-by :LastName #(compare %2 %1) data))


;;A -> Z
;;oldest -> most recent
;;Z -> A

;;try into #{}
(defn output-set [data]

  ;;new fn??
  (println "Output 1 – sorted by gender (females before males) then by last name ascending.")
  (println (sort-gender->last-asc data))
  (println)

  (println "Output 2 – sorted by birth date, ascending.")
  (println (sort-dob-asc data))
  (println)

  (println "Output 3 – sorted by last name, descending.")
  (println (sort-lastname-desc data))
  (println)

  )


(defn create-set [keys data]

  #_(into #{}
    (map #(zipmap keys %) data))

 #_(reset! state (into #{}
                  (map #(zipmap keys %) data)))

  (->> data
    (map #(zipmap keys %))
    ;;(into #{})
    (reset! state))

  )


(defn parse-and-sort [text]
  (let [[header & data] (handle-str text)
        keys (map #(keyword %) header)
        result (create-set keys data)]
    (output-set result)))