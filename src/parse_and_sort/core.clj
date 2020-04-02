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

;;todo
;;try/catch? for repeat data
;;with-open for slurp

;;handle other formats
;;remove | or ,

;;add service folder
;;add sort fn to services

;;;;args logic for input
(defn -main
  "add here"
  [& args]

  (run-jetty #'app {:port 3000, :join? false})
  (println "server running in port 3000")

  (let [text (slurp "test.txt")] ;;change this to args
    (s/create-and-output text))

  ;;todo
  ;;test this
  ;;(run-jetty #'app {:port 3000, :join? false})
  ;;(println "server running in port 3000")


  )
