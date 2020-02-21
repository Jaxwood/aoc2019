(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
  (testing "string->reaction"
    (def expected {:in [[3 "A"] [4 "B"]] :out [1 "AB"]})
    (def actual (string->reaction "3 A, 4 B => 1 AB"))
    (is (= expected actual)))
  (testing "string->reaction without comma"
    (def expected {:in [[3 "A"]] :out [1 "AB"]})
    (def actual (string->reaction "3 A => 1 AB"))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 0)
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual))))