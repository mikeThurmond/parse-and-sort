(ns parse-and-sort.routes-records-test
  (:require [parse-and-sort.server :refer [app]]
            [clojure.test :refer :all]
            [parse-and-sort.services.core :as s]
            [ring.mock.request :refer [request json-body]]))

(def test-state #{{:LastName "Smith" :FirstName "Jane" :Gender "Female" :DateOfBirth "11/25/1991"}
                  {:LastName "Thurmond" :FirstName "Michael" :Gender "Male" :DateOfBirth "09/12/1986"}
                  {:LastName "Allen" :FirstName "Will" :Gender "Male" :DateOfBirth "01/01/2016"}
                  {:LastName "Zell" :FirstName "Zana" :Gender "Female" :DateOfBirth "08/01/2020"}})

(def gender-json "[{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"},{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"}]")
(def dob-json "[{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"},{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"},{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"}]")
(def name-json "[{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"},{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"},{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"}]")
(def post-json "{\"LastName\":\"Deputy\",\"FirstName\":\"Zach\",\"Gender\":\"Male\",\"FavoriteColor\":\"Red\",\"DateOfBirth\":\"01/01/0011\"}")

(deftest routes
  (testing "GET /records/gender"
    (reset! s/global-state test-state)
    (is (= (-> (request :get "/records/gender")
             app :body slurp)
          gender-json)))
  (testing "GET /records/birthdate"
    (reset! s/global-state test-state)
    (is (= (-> (request :get "/records/birthdate")
             app :body slurp)
          dob-json)))
  (testing "GET /records/name"
    (reset! s/global-state test-state)
    (is (= (-> (request :get "/records/name")
             app :body slurp)
          name-json)))
  (testing "POST /records"
    (reset! s/global-state #{})
    (is (= (-> (request :post "/records")
             (json-body "Deputy, Zach, Male, Red, 01/01/0011")
             app :body slurp)
          post-json))))