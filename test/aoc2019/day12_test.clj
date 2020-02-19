(ns aoc2019.day12-test
  (:require [clojure.test :refer :all]
            [aoc2019.day12.core :refer :all]))

(deftest day12
  (testing "calculate"
    (def expected 179)
    (def actual (calculate [[2 1 -3] [1 -8 0] [3 -6 1] [2 0 4]] [[-3 -2 1] [-1 1 3] [3 2 -3] [1 -1 -1]]))
    (is (= expected actual)))
  (testing "day12a"
    (def expected 179)
    (def actual (day12a [[-1 0 2] [2 -10 -7] [4 -8 8] [3 5 -1]] [[0 0 0] [0 0 0] [0 0 0] [0 0 0]] 10))
    (is (= expected actual))))