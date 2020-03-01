(ns aoc2019.day08-test
  (:require [clojure.test :refer :all]
            [aoc2019.day08.core :refer :all]))

(deftest day08
  (testing "day08a"
    (let [expected 2440
          actual (day08a "src/aoc2019/day08/input.txt")]
      (is (= expected actual))))
  (testing "transpose"
    (let [expected [[1 1] [2 2]]
          actual (transpose [[1 2] [1 2]])]
      (is (= expected actual))))
  (testing "day08b"
    (let [expected [[0 1 1 0 0 1 1 1 1 0 0 1 1 0 0 0 0 1 1 0 0 1 1 0 0] [1 0 0 1 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 0 1 0 0 1 0] [1 0 0 1 0 0 0 1 0 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 0] [1 1 1 1 0 0 1 0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 0 0 0] [1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1 0] [1 0 0 1 0 1 1 1 1 0 0 1 1 0 0 0 1 1 0 0 0 1 1 0 0]]
          actual (day08b "src/aoc2019/day08/input.txt")]
      (is (= expected actual)))))