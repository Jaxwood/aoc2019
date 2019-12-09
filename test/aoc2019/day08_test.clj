(ns aoc2019.day08-test
  (:require [clojure.test :refer :all]
            [aoc2019.day08.core :refer :all]))

(deftest day08
  (testing "day08a"
    (def expected 2440) 
    (def actual (day08a "src/aoc2019/day08/input.txt"))
    (is (= expected actual)))
  (testing "transpose"
    (def expected [[1 1] [2 2]]) 
    (def actual (transpose [[1 2] [1 2]]))
    (is (= expected actual)))
  (testing "day08b"
    (def expected []) 
    (def actual (day08b "src/aoc2019/day08/input.txt"))
    (is (= expected actual))))