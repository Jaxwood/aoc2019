(ns aoc2019.day12-test
  (:require [clojure.test :refer :all]
            [aoc2019.day12.core :refer :all]))

(deftest day12
  (testing "day12a"
    (def expected 179)
    (def actual (day12a [[-1 0 2] [2 -10 -7] [4 -8 8] [3 5 -1]] 10))
    (is (= expected actual))))