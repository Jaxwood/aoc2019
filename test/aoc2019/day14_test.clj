(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
  (testing "string->reaction with comma"
    (def expected {:in [[3 "A"] [4 "B"]] :out [1 "AB"]})
    (def actual (string->reaction "3 A, 4 B => 1 AB"))
    (is (= expected actual)))
  (testing "string->reaction without comma"
    (def expected {:in [[3 "A"]] :out [1 "AB"]})
    (def actual (string->reaction "3 A => 1 AB"))
    (is (= expected actual)))
  (testing "ore?"
    (def expected true)
    (def actual (ore? [7 "ORE"]))
    (is (= expected actual)))
  (testing "ore?"
    (def expected false)
    (def actual (ore? [7 "WE"]))
    (is (= expected actual)))
  ;; (testing "ore"
    ;; (def expected 10)
    ;; (def actual (ore (parse "src/aoc2019/day14/input.txt") [7 "A"]))
    ;; (is (= expected actual)))
  (testing "ore"
    (def expected 31)
    (def actual (ore (parse "src/aoc2019/day14/input.txt") [1 "FUEL"]))
    (is (= expected actual)))
  (testing "day14a"
    (def expected [])
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual))))