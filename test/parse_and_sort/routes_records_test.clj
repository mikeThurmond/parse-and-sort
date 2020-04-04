(ns parse-and-sort.routes-records-test
  (:require [parse-and-sort.server :refer [app]]
            [clojure.test :refer :all]
            [cheshire.core :as c] ;;rm
            [parse-and-sort.routes.records :as r]
            [parse-and-sort.services.core :as s]
            [ring.mock.request :refer [request json-body]]))

(def test-state #{{:LastName "Smith" :FirstName "Jane" :Gender "Female" :DateOfBirth "11/25/1991"}
                  {:LastName "Thurmond" :FirstName "Michael" :Gender "Male" :DateOfBirth "09/12/1986"}
                  {:LastName "Allen" :FirstName "Will" :Gender "Male" :DateOfBirth "01/01/2016"}
                  {:LastName "Zell" :FirstName "Zana" :Gender "Female" :DateOfBirth "08/01/2020"}})

(def gender-json "[{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"},{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"}]")
(def dob-json "[{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"},{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"},{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"}]")
(def name-json "[{\"LastName\":\"Zell\",\"FirstName\":\"Zana\",\"Gender\":\"Female\",\"DateOfBirth\":\"08/01/2020\"},{\"LastName\":\"Thurmond\",\"FirstName\":\"Michael\",\"Gender\":\"Male\",\"DateOfBirth\":\"09/12/1986\"},{\"LastName\":\"Smith\",\"FirstName\":\"Jane\",\"Gender\":\"Female\",\"DateOfBirth\":\"11/25/1991\"},{\"LastName\":\"Allen\",\"FirstName\":\"Will\",\"Gender\":\"Male\",\"DateOfBirth\":\"01/01/2016\"}]")

;;try on create with reset! on create and tear down
;;mock out sort fns
;;try bad params on input for post


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

  #_(deftest your-handler-test
    (is (= (r/get-gender (request :get "/records/gender"))
          {:status  200
           :headers {"content-type" "text/plain"}
           :body    "Your expected result"})))


  )