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
                       :body   (s/sort-gender->lastname-asc @s/global-state)})}]
   ["/birthdate" {:get (fn [_]
                         {:status 200
                          :body   (s/sort-dob-asc @s/global-state)})}]
   ["/name" {:get (fn [_]
                    {:status 200
                     :body   (s/sort-lastname-desc @s/global-state)})}]
   ["" {:post (fn [{params :body-params}]
                (swap! s/global-state conj params)
                {:status 200
                 :body   params})}]])
