(ns parse-and-sort.core
  (:require

    [clojure.string :as s]
    []

            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))


;;todo
;;add to gitignore
;;add test cases for routes and logic
;;test cases
;;add api
;;add api test cases


;;normalize str
;;restructure str
(defn handle-str [file]
  (as-> file f
    (s/split f #"\n")
    (map #(s/split % #" ") f)))

;;todo
;;try/catch? for repeat data
;;with-open for slurp

;;handle other formats
;;remove | or ,

;;add service folder
;;add sort fn to services






(defn output-set [data]

  ;Output 1 – sorted by gender (females before males) then by last name ascending. A -> Z
  (sort-by (juxt :Gender :LastName) data)


  ;;bring in clj time
  ;;Output 2 – sorted by birth date, ascending.
  ;; oldest -> most recent
  ;;oldest first

  ;Output 3 – sorted by last name, descending. Z -> A
  (sort-by :LastName #(compare %2 %1) data)
  )


(defn create-set [keys data]
  (into #{}
    (map #(zipmap keys %) data)))


(defn tfn [text]
  (let [[header & data] (handle-str text)
         keys (map #(keyword %) header)
        result (create-set keys data)]
    result

    ))

(defn -main
  "add here"
  [& args]
  (let [text (slurp "test.txt")
        ;[header & remaining] (handle-str file)
        ; keys (map #(keyword %) header)
        ]

      (tfn text)

    ;;todo
    ;;commit to repo
    ;;save off as atom as dataset



    )

  ;(run-jetty #'app {:port 3000, :join? false})
  ;(println "server running in port 3000")


  )
