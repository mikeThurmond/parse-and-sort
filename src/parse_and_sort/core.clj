(ns parse-and-sort.core
  (:require
    [parse-and-sort.server :refer [app]]
    [ring.adapter.jetty :refer [run-jetty]]
    [parse-and-sort.services.core :as s])
  (:gen-class))


;;todo
;;add test cases for routes and logic
;;test cases
;;add api
;;add api test cases
;;document assumptions
;; - formatting of docs (\n at end of line, properely formatted input),
;; output to console,
;;post input as json

;;todo
;;try/catch? for repeat data
;;with-open for slurp

;;test parsing fomratting date to state on input and then returning via gets

;;test args input
(defn -main [& args]
  (if (= (count args) 1)
    (let [_ (run-jetty #'app {:port 3000, :join? false})
          text (slurp (first args))
          result (s/parse-and-sort text)]
      (s/output-set result))
    (System/exit 0)))
