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
    (is (= expected actual)))
  (testing "planet counter - a"
    (def expected 8)
    (def actual (planet-counter (parse "src/aoc2019/day10/a.txt") [3 4]))
    (is (= expected actual)))
  (testing "planet counter - b"
    (def expected 33)
    (def actual (planet-counter (parse "src/aoc2019/day10/b.txt") [5 8]))
    (is (= expected actual)))
  (testing "planet counter - c"
    (def expected 35)
    (def actual (planet-counter (parse "src/aoc2019/day10/c.txt") [1 2]))
    (is (= expected actual)))
  (testing "planet counter - d"
    (def expected 41)
    (def actual (planet-counter (parse "src/aoc2019/day10/d.txt") [6 3]))
    (is (= expected actual)))
  (testing "planet counter - e"
    (def expected 210)
    (def actual (planet-counter (parse "src/aoc2019/day10/e.txt") [11 13]))
    (is (= expected actual)))
  (testing "planet counter - e"
    (def expected 286)
    (def actual (day10a (parse "src/aoc2019/day10/input.txt")))
    (is (= expected actual))))