(ns aoc2019.day04-test
  (:require [clojure.test :refer :all]
            [aoc2019.day04.core :refer :all]))

(deftest day04
  (testing "adjacent? - a"
    (def expected true)
    (def actual (adjacent? (str 111111)))
    (is (= expected actual)))
  (testing "adjacent? - b"
    (def expected false)
    (def actual (adjacent? (str 123789)))
    (is (= expected actual)))
  (testing "adjacent? - c"
    (def expected false)
    (def actual (adjacent? (str 123289)))
    (is (= expected actual)))
  (testing "increments? - a"
    (def expected false)
    (def actual (increments? (str 223450)))
    (is (= expected actual)))
  (testing "increments? - b"
    (def expected true)
    (def actual (increments? (str 123789)))
    (is (= expected actual)))
  (testing "day04a"
    (def expected 2779)
    (def actual (day04a 108457 562041))
    (is (= expected actual))))