(ns parse-and-sort.routes.records
  (:require [parse-and-sort.services.core :as s]))


;POST /records - Post a single data line
; in any of the 3 formats supported by your existing code
;GET /records/gender - returns records sorted by gender
;GET /records/birthdate - returns records sorted by birthdate
;GET /records/name

(def routes
  ["/records"
   ["/gender" {:get (fn [_]
                      {:status 200
                       :body   (s/sort-gender->last-asc @s/state)})}]
   ["/birthdate" {:get (fn [_]
                         {:status 200
                          :body   (s/sort-dob-asc @s/state)})}]
   ["/name" {:get (fn [_]
                    {:status 200
                     :body   (s/sort-lastname-desc @s/state)})}]
   ["" {:post (fn [{params :body-params}]

                (println :body-params "one")
                (println params)
                (println)

                {:status 200
                 :body   {:yes "success!"}})}]])
