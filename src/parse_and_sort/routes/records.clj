(ns parse-and-sort.routes.records
  (:require [parse-and-sort.services.core :as s]
            [clojure.spec.alpha :as spec]))

(def records-spec {:LastName      string?
                   :FirstName     string?
                   :Gender        string?
                   :FavoriteColor string?
                   :DateOfBirth   string?})

(def routes
  ["/records"
   ["/gender" {:get (fn [_]
                      {:status 200
                       :body   (s/sort-gender-lastname-asc @s/global-state)})}]
   ["/birthdate" {:get (fn [_]
                         {:status 200
                          :body   (s/sort-dob-asc @s/global-state)})}]
   ["/name" {:get (fn [_]
                    {:status 200
                     :body   (s/sort-lastname-desc @s/global-state)})}]
   ["" {:post
        {
         :parameters {:body string?}
         :responses {201 {:body records-spec}}
         :handler    (fn [{single-line :body-params}]
                       (let [result (s/update-set single-line)]
                         {:status 201
                          :body   result}))}}]])

