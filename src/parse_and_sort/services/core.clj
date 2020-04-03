(ns parse-and-sort.services.core
  (:require [clojure.string :as s]
            [clj-time.format :as f]))

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

(defn compare-dates [one two]
  (compare
    (f/parse custom-formatter one)
    (f/parse custom-formatter two)))

(defn sort-gender->lastname-asc [data]
  (sort-by (juxt :Gender :LastName) data))

(defn sort-dob-asc [data]
  (sort-by :DateOfBirth #(compare-dates %1 %2) data))

(defn sort-lastname-desc [data]
  (sort-by :LastName #(compare %2 %1) data))

(def output [{:text output1 :sort-fn sort-gender->lastname-asc}
             {:text output2 :sort-fn sort-dob-asc}
             {:text output3 :sort-fn sort-lastname-desc}])

(defn output-set [data]
  (doseq [{:keys [text sort-fn]} output]
    (println text)
    (println (into #{} (sort-fn data)))
    (println)))


;;A -> Z
;;oldest -> most recent
;;Z -> A
;;try into #{}
#_(into #{}
    (map #(zipmap keys %) data))

#_(reset! global-state (into #{}
                  (map #(zipmap keys %) data)))

;;test repeats on input to make sure duplicates are dropped
;;test condition where I post a duplicate, should not insert
(defn create-set [keys data state]
  (->> data
    (map #(zipmap keys %))
    (into #{})
    (reset! state)))

(defn parse-and-sort [text]
  (let [[header & data] (str->rows-and-cols text)
        keys (map #(keyword %) header)]
    (create-set keys data global-state)))