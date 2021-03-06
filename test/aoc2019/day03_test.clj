(ns aoc2019.day03-test
  (:require [clojure.test :refer :all]
            [aoc2019.day03.core :refer :all]))

(deftest day03
  (testing "a"
    (let [expected 6
          actual (day03a ["R8,U5,L5,D3", "U7,R6,D4,L4"])]
      (is (= expected actual))))
  (testing "ab"
    (let [expected 30
          actual (day03b ["R8,U5,L5,D3", "U7,R6,D4,L4"])]
      (is (= expected actual))))
  (testing "b"
    (let [expected 159
          actual (day03a ["R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83"])]
      (is (= expected actual))))
  (testing "bb"
    (let [expected 610
          actual (day03b ["R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83"])]
      (is (= expected actual))))
  (testing "c"
    (let [expected 135
          actual (day03a ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])]
      (is (= expected actual))))
  (testing "cc"
    (let [expected 410
          actual (day03b ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])]
      (is (= expected actual))))
  (testing "parse"
    (let [expected ["R8" "U5" "L5" "D3"]
          actual (parse "R8,U5,L5,D3")]
      (is (= expected actual))))
  (testing "toTuples"
    (let [expected [["R" 8] ["U" 5] ["L" 5] ["D" 3]]
          actual (map toTuples ["R8" "U5" "L5" "D3"])]
      (is (= expected actual))))
  (testing "generateX"
    (let [expected [[1 0] [2 0] [3 0]]
          actual (take 3 (generateX [0 0] inc))]
      (is (= expected actual))))
  (testing "generateY"
    (let [expected [[0 -1] [0 -2] [0 -3]]
          actual (take 3 (generateY [0 0] dec))]
      (is (= expected actual))))
  (testing "path right"
    (let [expected [[1 0] [2 0] [3 0]]
          actual (path [0 0] ["R" 3])]
      (is (= expected actual))))
  (testing "path left"
    (let [expected [[-1 0] [-2 0] [-3 0]]
          actual (path [0 0] ["L" 3])]
      (is (= expected actual))))
  (testing "path up"
    (let [expected [[0 1] [0 2] [0 3]]
          actual (path [0 0] ["U" 3])]
      (is (= expected actual))))
  (testing "path down"
    (let [expected [[0 -1] [0 -2] [0 -3]]
          actual (path [0 0] ["D" 3])]
      (is (= expected actual))))
  (testing "follow"
    (let [expected [[0 -1] [0 -2] [0 -3] [0 -2] [0 -1] [0 0]]
          actual (follow [] [0 0] [["D" 3] ["U" 3]])]
      (is (= expected actual))))
  (testing "steps a"
    (let [expected 20
          actual (steps [3 3] (follow [] [0 0] (map toTuples (parse "R8,U5,L5,D3"))))]
      (is (= expected actual))))
  (testing "steps b"
    (let [expected 20
          actual (steps [3 3] (follow [] [0 0] (map toTuples (parse "U7,R6,D4,L4"))))]
      (is (= expected actual))))
  (testing "day03a"
    (let [expected 209
          actual (day03a (clojure.string/split-lines (slurp "src/aoc2019/day03/input.txt")))]
      (is (= expected actual))))
  (testing "day03b"
    (let [expected 43258
          actual (day03b (clojure.string/split-lines (slurp "src/aoc2019/day03/input.txt")))]
      (is (= expected actual)))))