(ns parse-and-sort.services.core
  (:require [clojure.string :as s]
            [clj-time.format :as f]
            [clojure.pprint :as p]))

(defonce global-state (atom #{}))

(def output1 "Output 1 – sorted by gender (females before males) then by last name ascending.")
(def output2 "Output 2 – sorted by birth date, ascending.")
(def output3 "Output 3 – sorted by last name, descending.")

(def custom-formatter (f/formatter "MM/DD/YYY"))

(defn str->rows-and-cols [string]
  (let [string (s/split string #"\n")]
    (->> string
      (map #(s/replace % #"," ""))
      (map #(s/replace % #"\| " ""))
      (map #(s/split % #" ")))))

(defn parse-date [date]
  (f/parse custom-formatter date))

(defn compare-dates [one two]
  (compare
    (parse-date one)
    (parse-date two)))

(defn sort-gender-lastname-asc [data]
  (sort-by (juxt :Gender :LastName) data))

(defn sort-dob-asc [data]
  (sort-by :DateOfBirth #(compare-dates %1 %2) data))

(defn sort-lastname-desc [data]
  (sort-by :LastName #(compare %2 %1) data))

(def output [{:text output1 :sort-fn sort-gender-lastname-asc}
             {:text output2 :sort-fn sort-dob-asc}
             {:text output3 :sort-fn sort-lastname-desc}])

(defn output-set [data]
  (doseq [{:keys [text sort-fn]} output]
    (println text)
    (p/pprint (sort-fn data))
    (println)))

(defn add-to-set [current new]
  (into #{} (conj current new)))

(defn create-set [keys data]
  (into #{}
    (map #(zipmap keys %) data)))

(defn parse->create-set [text]
  (let [[header & data] (str->rows-and-cols text)
        keys (map #(keyword %) header)]
    (create-set keys data)))