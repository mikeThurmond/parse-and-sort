(ns parse-and-sort.routes.records
  (:require [parse-and-sort.services.core :as s]))


;POST /records - Post a single data line
; in any of the 3 formats supported by your existing code
;GET /records/gender - returns records sorted by gender
;GET /records/birthdate - returns records sorted by birthdate
;GET /records/name

(def routes
  ["/records"

   ;;rm
   ["/plus" {:get  (fn [{{:strs [x y]} :query-params :as req}]
                     {:status 200
                      :body   {:total (+ (Long/parseLong x) (Long/parseLong y))}})
             :post (fn [{{:keys [x y]} :body-params}]
                     {:status 200
                      :body   {:total (+ x y)}})}]

   ["/gender" {:get  (fn [_]
                     {:status 200
                      :body   @s/state})}]

   ["/birthdate" {:get  (fn [{{:strs [x y]} :query-params :as req}]
                       {:status 200
                        :body   {:total (+ (Long/parseLong x) (Long/parseLong y))}})}]
   ["/name" {:get  (fn [{{:strs [x y]} :query-params :as req}]
                       {:status 200
                        :body   {:total (+ (Long/parseLong x) (Long/parseLong y))}})}]
   ])
