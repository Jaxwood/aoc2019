(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "calculate"
    (let [expected 4
          actual (calculate [1 2 3 4 5 6 7 8] 0 1)]
      (is (= expected actual))))
  (testing "fft"
    (let [expected '(0 1 0 2 9 4 9 8)
          actual (fft [1 2 3 4 5 6 7 8] 0 4)]
      (is (= expected actual))))
  (testing "day16a"
    (let [expected '(7 3 1 2 7 5 2 3)
          actual (day16a "src/aoc2019/day16/input.txt" 100)]
      (is (= expected actual))))
  (testing "day16b testcase"
    (let [expected '(8 0 2 8 4 4 2 0)
          actual (day16b (slurp "src/aoc2019/day16/input.txt") 100)]
      (is (= expected actual)))))