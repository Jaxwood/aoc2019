(ns aoc2019.day12-test
  (:require [clojure.test :refer :all]
            [aoc2019.day12.core :refer :all]))

(deftest day12
  (testing "calculate"
    (let [expected 179
          actual (calculate [[2 1 -3] [1 -8 0] [3 -6 1] [2 0 4]] [[-3 -2 1] [-1 1 3] [3 2 -3] [1 -1 -1]])]
      (is (= expected actual))))
  (testing "gravity"
    (let [expected [3 1 -3 -1]
          actual (gravity [-1 2 4 3])]
      (is (= expected actual))))
  (testing "tick"
    (let [expected [[[2 -1 1] [3 -7 -4] [1 -7 5] [2 2 0]] [[3 -1 -1] [1 3 3] [-3 1 -3] [-1 -3 1]]]
          actual (tick [[-1 0 2] [2 -10 -7] [4 -8 8] [3 5 -1]] [[0 0 0] [0 0 0] [0 0 0] [0 0 0]])]
      (is (= expected actual))))
  (testing "multiple ticks"
    (let [expected 179
          actual (day12a [[-1 0 2] [2 -10 -7] [4 -8 8] [3 5 -1]] [[0 0 0] [0 0 0] [0 0 0] [0 0 0]] 10)]
      (is (= expected actual))))
  (testing "find repeating steps"
    (let [expected 18
          actual (find-repeating-step [-1 2 4 3 0 0 0 0] 0 {})]
      (is (= expected actual))))
  (testing "day12a"
    (let [expected 9958
          actual (day12a [[7 10 17] [-2 7 0] [12 5 12] [5 -8 6]] [[0 0 0] [0 0 0] [0 0 0] [0 0 0]] 1000)]
      (is (= expected actual))))
  (testing "day12b"
    (let [expected 2772
          actual (day12b [[-1 2 4 3] [0 -10 -8 5] [2 -7 8 -1]])]
      (is (= expected actual)))))