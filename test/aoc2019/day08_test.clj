(ns aoc2019.day08-test
  (:require [clojure.test :refer :all]
            [aoc2019.day08.core :refer :all]))

(deftest day08
  (testing "day08a"
    (def expected 2440) 
    (def actual (day08a "src/aoc2019/day08/input.txt"))
    (is (= expected actual))))