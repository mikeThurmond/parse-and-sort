(ns parse-and-sort.core
  (:require
    [parse-and-sort.server :refer [app]]
    [ring.adapter.jetty :refer [run-jetty]]
    [parse-and-sort.services.core :as s]

    [parse-and-sort.routes.records :as r]
    [cheshire.core :as c]
    [ring.mock.request :refer [request json-body]]
    )
  (:gen-class))


;;todo
;;document assumptions
;; - formatting of docs (\n at end of line, properely formatted input),
;; output to console,
;;post input as json


;;lein run file.txt
;;lein test

;;issues
;; figuring out how to restructure the string to something I could work with
;; pipe symbol in the regex took me by surprise cause I finsihed the comma ...
;; read docs for spec and reitit


;;todo
;;add test cases for routes and service logic
;;add api test cases
;;add cloverage

;;add test case for conj function

;;test condition where I post a duplicate, should not insert

;;test parsing formatting date to state on input and then returning via gets
;;add spec for date

;;try/catch for slurp?
;;test args input multiple or no val
;;(.exists (clojure.java.io/file "Example.txt"))
;;run server last??
(defn -main [& args]
  (if (= (count args) 1)
    (let [_ (run-jetty #'app {:port 3000, :join? false})
          text (slurp (first args))]
      (->> text
        (s/parse-and-sort)
        (s/output-set)
        (reset! s/global-state)))
    (do
      (println "Error: Please check your command line args and try again")
      (println "lein run your-file.txt")
      ;(System/exit 0)
      )))

