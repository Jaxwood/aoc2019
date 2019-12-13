(ns aoc2019.day10-test
  (:require [clojure.test :refer :all]
            [aoc2019.day10.core :refer :all]))

(deftest day10
  (testing "line of sight"
    (def expected false) 
    (def actual (los? [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]] [3 4] [1 0]))
    (is (= expected actual)))
  (testing "line of sight"
    (def expected true) 
    (def actual (los? [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]] [3 4] [1 2]))
    (is (= expected actual))))