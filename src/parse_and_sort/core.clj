(ns parse-and-sort.core
  (:require
    [parse-and-sort.server :refer [app]]
    [ring.adapter.jetty :refer [run-jetty]]
    [parse-and-sort.services.core :as s])
  (:gen-class))


;;todo
;;document assumptions
;; - formatting of docs (\n at end of line, properely formatted input),
;; output to console,
;;post input as json

;;issues
;; figuring out how to restructure the string to something I could work with
;; pipe symbol in the regex took me by surprise cause I finsihed the comma ...


;;todo
;;coercion
;;add test cases for routes and service logic
;;test cases
;;add api test cases


;;test parsing fomratting date to state on input and then returning via gets
;;add spec for date

;;try/catch for slurp?
;;test args input multiple or no val
;;(.exists (clojure.java.io/file "Example.txt"))
(defn -main [& args]
  (if (= (count args) 1)
    (let [_ (run-jetty #'app {:port 3000, :join? false})
          text (slurp (first args))
          result (s/parse-and-sort text)]
      (s/output-set result))
    (do
      (println "Error: Please check your command line args and try again")
      (println "lein run your-file.txt")
      (System/exit 0))))
