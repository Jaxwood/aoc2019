(ns aoc2019.day03-test
  (:require [clojure.test :refer :all]
            [aoc2019.day03.core :refer :all]))

(deftest day03
  (testing "a"
    (def expected 6)
    (def actual (day03a ["R8,U5,L5,D3", "U7,R6,D4,L4"]))
    (is (= expected actual)))
  (testing "parse"
    (def expected ["R8" "U5" "L5" "D3"]))
    (def actual (parse "R8,U5,L5,D3"))
    (is (= expected actual))
  (testing "toTuples"
    (def expected [["R" 8] ["U" 5] ["L" 5] ["D" 3]]))
    (def actual (map toTuples ["R8" "U5" "L5" "D3"]))
    (is (= expected actual)))