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

;;handle other formats
;;remove | or ,

;;test parsing fomratting date to state on input and then returning via gets
;;finish up with post req

;;;;args logic for input
(defn -main [& args]
  (let [_ (run-jetty #'app {:port 3000, :join? false})
        text (slurp "test.txt")
        result (s/parse-and-sort text)] ;;change this to args
    (s/output-set result)))
