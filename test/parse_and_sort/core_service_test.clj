(ns parse-and-sort.core-service-test
  (:require [clojure.test :refer :all]
            [parse-and-sort.services.core :refer :all]
            [parse-and-sort.services.core :as s]))


(def test-state #{{:LastName "Smith" :FirstName "Jane" :Gender "Female" :DateOfBirth "11/25/1991"}
                  {:LastName "Thurmond" :FirstName "Michael" :Gender "Male" :DateOfBirth "09/12/1986"}
                  {:LastName "Allen" :FirstName "Will" :Gender "Male" :DateOfBirth "01/01/2016"}
                  {:LastName "Zell" :FirstName "Zana" :Gender "Female" :DateOfBirth "08/01/2020"}})

(deftest create-set-test
  (testing "create set with keys and values"
    (let [keys [:FirstName :LastName]
          vals [["Jim" "Smith"]]
          result #{{:FirstName "Jim"
                    :LastName  "Smith"}}]
      (is (= result (create-set keys vals)))))
  (testing "create set with keys and values does not insert duplicates"
    (let [keys [:FirstName :LastName]
          dup-vals [["Jim" "Smith"]
                    ["Jim" "Smith"]]
          result #{{:FirstName "Jim"
                    :LastName  "Smith"}}]
      (is (= result (create-set keys dup-vals))))))

(deftest add-to-set-test
  (testing "fn used in swap! to add new items to set")
  (let [curr #{{:FirstName "Jim"
                  :LastName "Smith"}}
        new {:FirstName "New"
             :LastName "Person"}
        result #{{:FirstName "Jim" :LastName "Smith"}
                 {:FirstName "New" :LastName "Person"}}]
    (is (= result (add-to-set curr new)))))

(deftest update-set-test
  (testing "updating set used in POST"
    (let [_ (reset! s/global-state #{})
          _ (reset! s/global-keys [:LastName :FirstName :Gender :FavoriteColor :DateOfBirth])
          text "Deputy, Zach, Male, Red, 01/01/0011"
          result (s/update-set text)]
      (is (= (count @s/global-state) 1))
      (is (= result {:LastName "Deputy"
                     :FirstName "Zach"
                     :Gender "Male"
                     :FavoriteColor "Red"
                     :DateOfBirth "01/01/0011"})))))

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


(def base "LastName FirstName Gender FavoriteColor DateOfBirth\nZell Zana Female Orange 08/01/1979")
(def comma "LastName, FirstName, Gender, FavoriteColor, DateOfBirth\nZell, Zana, Female, Orange, 08/01/1979")
(def pipe "LastName | FirstName | Gender | FavoriteColor | DateOfBirth\nZell | Zana | Female | Orange | 08/01/1979")
(def expected-header ["LastName" "FirstName" "Gender" "FavoriteColor" "DateOfBirth"])
(def expected-data [["Zell" "Zana" "Female" "Orange" "08/01/1979"]])

(deftest str->rows-and-cols-test
  (testing "parse base input string into rows and columns"
    (let [[header & data] (str->rows-and-cols base)]
      (is (= header expected-header))
      (is (= data expected-data))))
  (testing "parse comma input string into rows and columns"
    (let [[header & data] (str->rows-and-cols comma)]
      (is (= header expected-header))
      (is (= data expected-data))))
  (testing "parse pipe input string into rows and columns"
    (let [[header & data] (str->rows-and-cols pipe)]
      (is (= header expected-header))
      (is (= data expected-data)))))

