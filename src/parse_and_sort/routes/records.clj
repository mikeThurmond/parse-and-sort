(ns parse-and-sort.routes.records
  (:require [parse-and-sort.services.core :as s]
            [clojure.spec.alpha :as spec]))

(def records-spec {:LastName      string?
                   :FirstName     string?
                   :Gender        string?
                   :FavoriteColor string?
                   :DateOfBirth   string?})

;;with-redefs on this fn?
(defn get-gender [_]
  {:status 200
   :body   (s/sort-gender-lastname-asc @s/global-state)})

#_(fn [_]
    {:status 200
     :body   (s/sort-gender-lastname-asc @s/global-state)})

(def routes
  ["/records"
   ["/gender" {:get (fn [_]
                      (get-gender _))}]
   ["/birthdate" {:get (fn [_]
                         {:status 200
                          :body   (s/sort-dob-asc @s/global-state)})}]
   ["/name" {:get (fn [_]
                    {:status 200

                     :body   (s/sort-lastname-desc @s/global-state)})}]
   ;;start program and add same post data twice
   ;;do not allow duplicates

   ;;todo
   ;;test what happens if i have to parse a string on post
   ;;validate with spec
   ["" {:post
        {:parameters {:body records-spec}
         ;;:responses {200 {:body records-spec}}
         :handler    (fn [{params :body-params}]
                       (swap! s/global-state s/add-to-set params)
                       {:status 201
                        :body  params})}}]])

