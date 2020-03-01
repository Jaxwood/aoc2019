(ns aoc2019.day10-test
  (:require [clojure.test :refer :all]
            [aoc2019.day10.core :refer :all]))

(deftest day10
  (def planets [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]])
  (testing "los? - return false when planet blocks"
    (let [expected false
          actual (los? planets [3 4] [1 0])]
      (is (= expected actual))))
  (testing "los? - return true if no planet blocks"
    (let [expected true
          actual (los? planets [3 4] [2 2])]
      (is (= expected actual))))
  (testing "intercepting? - return false if the planets is not intercepting"
    (let [expected false
          actual (intercepting? [3 4] [2 2] [1 0])]
      (is (= expected actual))))
  (testing "intercepting? - return true the planets is intercepting"
    (let [expected true
          actual (intercepting? [3 4] [1 0] [2 2])]
      (is (= expected actual))))
  (testing "parse - returns planets as list of tuples"
    (let [expected [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]]
          actual (parse "src/aoc2019/day10/a.txt")]
      (is (= expected actual))))
  (testing "planet counter - a"
    (let [expected 8
          actual (planet-counter (parse "src/aoc2019/day10/a.txt") [3 4])]
      (is (= expected actual))))
  (testing "planet counter - b"
    (let [expected 33
          actual (planet-counter (parse "src/aoc2019/day10/b.txt") [5 8])]
      (is (= expected actual))))
  (testing "planet counter - c"
    (let [expected 35
          actual (planet-counter (parse "src/aoc2019/day10/c.txt") [1 2])]
      (is (= expected actual))))
  (testing "planet counter - d"
    (let [expected 41
          actual (planet-counter (parse "src/aoc2019/day10/d.txt") [6 3])]
      (is (= expected actual))))
  (testing "planet counter - e"
    (let [expected 210
          actual (planet-counter (parse "src/aoc2019/day10/e.txt") [11 13])]
      (is (= expected actual))))
  (testing "planet counter - e"
    (let [expected 286
          actual (day10a (parse "src/aoc2019/day10/input.txt"))]
      (is (= expected actual))))
  (testing "1,1 is 45 degree"
    (let [expected 45.0
          actual (degree-from-yAxis [1 1])]
      (is (= expected actual))))
  (testing "1,0 is 90 degree"
    (let [expected 90.0
          actual (degree-from-yAxis [1 0])]
      (is (= expected actual))))
  (testing "1,-1 is 135 degree"
    (let [expected 135.0
          actual (degree-from-yAxis [1 -1])]
      (is (= expected actual))))
  (testing "0,-1 is 180 degree"
    (let [expected 180.0
          actual (degree-from-yAxis [0 -1])]
      (is (= expected actual))))
  (testing "-1,-1 is 225 degree"
    (let [expected 225.0
          actual (degree-from-yAxis [-1 -1])]
      (is (= expected actual))))
  (testing "-1,0 is 270 degree"
    (let [expected 270.0
          actual (degree-from-yAxis [-1 0])]
      (is (= expected actual))))
  (testing "-1,1 is 315 degree"
    (let [expected 315.0
          actual (degree-from-yAxis [-1 1])]
      (is (= expected actual))))
  (testing "0,1 is 0 degree"
    (let [expected 0.0
          actual (degree-from-yAxis [0 1])]
      (is (= expected actual))))
  (testing "translate"
    (let [expected [[-1 1] [0 0]]
          actual (translate [[1 1] [2 2]] 2 2)]
      (is (= expected actual))))
  (testing "day10b"
    (let [expected 802
          actual (day10b (parse "src/aoc2019/day10/e.txt") [11 13])]
      (is (= expected actual))))
  (testing "day10b"
    (let [expected 504
          actual (day10b (parse "src/aoc2019/day10/input.txt") [22 25])]
      (is (= expected actual)))))