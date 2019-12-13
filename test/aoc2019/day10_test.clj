(ns aoc2019.day10-test
  (:require [clojure.test :refer :all]
            [aoc2019.day10.core :refer :all]))

(deftest day10
  (def planets [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]])
  (testing "los? - return false when planet blocks"
    (def expected false) 
    (def actual (los? planets [3 4] [1 0]))
    (is (= expected actual)))
  (testing "los? - return true if no planet blocks"
    (def expected true) 
    (def actual (los? planets [3 4] [2 2]))
    (is (= expected actual)))
  (testing "intercepting? - return false if the planets is not intercepting"
    (def expected false) 
    (def actual (intercepting? [3 4] [2 2] [1 0]))
    (is (= expected actual)))
  (testing "intercepting? - return true the planets is intercepting"
    (def expected true) 
    (def actual (intercepting? [3 4] [1 0] [2 2]))
    (is (= expected actual)))
  (testing "parse - returns planets as list of tuples"
    (def expected [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]]) 
    (def actual (parse "src/aoc2019/day10/a.txt"))
    (is (= expected actual))))