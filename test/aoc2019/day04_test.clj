(ns aoc2019.day04-test
  (:require [clojure.test :refer :all]
            [aoc2019.day04.core :refer :all]))

(deftest day04
  (testing "adjacent? - a"
    (let [expected true
          actual (adjacent? (str 111111))]
      (is (= expected actual))))
  (testing "adjacent? - b"
    (let [expected false
          actual (adjacent? (str 123789))]
      (is (= expected actual))))
  (testing "adjacent? - c"
    (let [expected false
          actual (adjacent? (str 123289))]
      (is (= expected actual))))
  (testing "valid? - a"
    (let [expected false
          actual (valid? [2 2 3 4 5 0])]
      (is (= expected actual))))
  (testing "valid? - b"
    (let [expected true
          actual (valid? [1 2 3 7 8 9])]
      (is (= expected actual))))
  (testing "adjacent-two? - a"
    (let [expected true
          actual (adjacent-two? [0 1 1 2 3])]
      (is (= expected actual))))
  (testing "adjacent-two? - b"
    (let [expected false
          actual (adjacent-two? [1 1 1 2 3])]
      (is (= expected actual))))
  (testing "adjacent-two? - c"
    (let [expected true
          actual (adjacent-two? [1 1 1 2 3 3])]
      (is (= expected actual))))
  (testing "day04a"
    (let [expected 2779
          actual (day04a 108457 562041)]
      (is (= expected actual))))
  (testing "day04b"
    (let [expected 1972
          actual (day04b 108457 562041)]
      (is (= expected actual)))))