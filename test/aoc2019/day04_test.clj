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
  (testing "valid? - a"
    (def expected false)
    (def actual (valid? [2 2 3 4 5 0]))
    (is (= expected actual)))
  (testing "valid? - b"
    (def expected true)
    (def actual (valid? [1 2 3 7 8 9]))
    (is (= expected actual)))
  (testing "adjacent-two? - a"
    (def expected true)
    (def actual (adjacent-two? [0 1 1 2 3]))
    (is (= expected actual)))
  (testing "adjacent-two? - b"
    (def expected false)
    (def actual (adjacent-two? [1 1 1 2 3]))
    (is (= expected actual)))
  (testing "adjacent-two? - c"
    (def expected true)
    (def actual (adjacent-two? [1 1 1 2 3 3]))
    (is (= expected actual)))
  (testing "day04a"
    (def expected 2779)
    (def actual (day04a 108457 562041))
    (is (= expected actual)))
  (testing "day04b"
    (def expected 1972)
    (def actual (day04b 108457 562041))
    (is (= expected actual))))