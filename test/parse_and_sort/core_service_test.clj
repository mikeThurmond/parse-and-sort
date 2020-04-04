(ns parse-and-sort.core-service-test
  (:require [clojure.test :refer :all]
            [parse-and-sort.services.core :refer :all]))


(def test-state #{{:LastName "Smith" :FirstName "Jane" :Gender "Female" :DateOfBirth "11/25/1991"}
                  {:LastName "Thurmond" :FirstName "Michael" :Gender "Male" :DateOfBirth "09/12/1986"}
                  {:LastName "Allen" :FirstName "Will" :Gender "Male" :DateOfBirth "01/01/2016"}
                  {:LastName "Zell" :FirstName "Zana" :Gender "Female" :DateOfBirth "08/01/2020"}})

;;todo
;;parse and sort with delemiters
;;look int juxt function for reversal of sort

(deftest create-set-test
  (testing "create set")
  (let [keys [:FirstName :LastName]
        vals [["Jim" "Smith"]]
        result #{{:FirstName "Jim"
                  :LastName "Smith"}}]
    (is (= result (create-set keys vals)))))

(deftest compare-dates-test
  (testing "date comparison")
  (is (= -1 (compare-dates "11/25/2011" "03/14/2020")))
  (is (= 1 (compare-dates "03/14/2020" "11/25/2011")))
  (is (= 0 (compare-dates "03/14/2020" "03/14/2020"))))

(deftest sort-dob-asc-test
  (testing "sorting state by dob asc. oldest -> most recent")
  (let [[r1 r2 r3 r4] (sort-dob-asc test-state)]
    (is (= (:DateOfBirth r1) "09/12/1986"))
    (is (= (:DateOfBirth r2) "11/25/1991"))
    (is (= (:DateOfBirth r3) "01/01/2016"))
    (is (= (:DateOfBirth r4) "08/01/2020"))))

(deftest sort-lastname-desc-test
  (testing "sorting state by last name desc")
  (let [[r1 r2 r3 r4] (sort-lastname-desc test-state)]
    (is (= (:LastName r1) "Zell"))
    (is (= (:LastName r2) "Thurmond"))
    (is (= (:LastName r3) "Smith"))
    (is (= (:LastName r4) "Allen"))))

(deftest sort-gender-lastname-asc-test
  (testing "sorting state by gender asc and last name asc")
  (let [[r1 r2 r3 r4] (sort-gender-lastname-asc test-state)]
    (is (= (and (= (:Gender r1) "Female") (= (:LastName r1) "Smith"))))
    (is (= (and (= (:Gender r2) "Female") (= (:LastName r1) "Zell"))))
    (is (= (and (= (:Gender r3) "Male") (= (:LastName r1) "Allen"))))
    (is (= (and (= (:Gender r4) "Male") (= (:LastName r1) "Thurmond"))))))



