(ns parse-and-sort.core
  (:require
    [parse-and-sort.server :refer [app]]
    [ring.adapter.jetty :refer [run-jetty]]
    [parse-and-sort.services.core :as s])
  (:gen-class))

(defn -main [& args]
  (if (= (count args) 1)
    (let [text (slurp (first args))]
      (->> text
        (s/parse->create-set)
        (reset! s/global-state)
        (s/output-set))
      (run-jetty #'app {:port 3000, :join? false}))
    (do
      (println "Error: Please check your command line args and try again.")
      (println "lein run your-file.txt")
      (System/exit 0))))

