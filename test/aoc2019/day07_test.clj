(ns aoc2019.day07-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day07.core :refer :all]
            [clojure.math.combinatorics :as combo]))

(deftest day07
  (testing "thrusters"
    (let [expected 43210
          actual (get (last (thrusters [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0] [4 3 2 1 0] 0)) :output)]
      (is (= expected actual))))
  (testing "thrusters"
    (let [expected 54321
          actual (get (last (thrusters [3 23 3 24 1002 24 10 24 1002 23 -1 23 101 5 23 23 1 24 23 23 4 23 99 0 0] [0 1 2 3 4] 0)) :output)]
      (is (= expected actual))))
  (testing "thrusters"
    (let [expected 65210
          actual (get (last (thrusters [3 31 3 32 1002 32 10 32 1001 31 -2 31 1007 31 0 33 1002 33 7 33 1 33 31 31 1 32 31 31 4 31 99 0 0 0] [1 0 4 3 2] 0)) :output)]
      (is (= expected actual))))
  (testing "day07a"
    (let [expected 116680
          actual (day07a (parse "src/aoc2019/day07/input.txt") (combo/permutations [0 1 2 3 4]) [])]
      (is (= expected actual))))
  (testing "feedback"
    (let [expected 139629729
          iteration1 (thrusters [3 26 1001 26 -4 26 3 27 1002 27 2 27 1 27 26 27 4 27 1001 28 -1 28 1005 28 6 99 0 0 5] [9 8 7 6 5] 0)
          actual (feedback iteration1)]
      (is (= expected actual))))
  (testing "feedback"
    (let [expected 18216
          iteration1 (thrusters [3 52 1001 52 -5 52 3 53 1 52 56 54 1007 54 5 55 1005 55 26 1001 54 -5 54 1105 1 12 1 53 54 53 1008 54 0 55 1001 55 1 55 2 53 55 53 4 53 1001 56 -1 56 1005 56 6 99 0 0 0 0 10] [9 7 8 5 6] 0)
          actual (feedback iteration1)]
      (is (= expected actual))))
  (testing "day07b"
    (let [expected 89603079
          actual (day07b (parse "src/aoc2019/day07/input.txt") (combo/permutations [5 6 7 8 9]) [])]
      (is (= expected actual)))))