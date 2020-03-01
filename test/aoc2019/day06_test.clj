(ns aoc2019.day06-test
  (:require [clojure.test :refer :all]
            [aoc2019.day06.core :refer :all]))

(deftest day06
  (testing "parse"
    (let [expected [["COM" "B"] ["B" "C"] ["C" "D"] ["D" "E"] ["E" "F"] ["B" "G"] ["G" "H"] ["D" "I"] ["E" "J"] ["J" "K"] ["K" "L"]]
          actual (parse (slurp "src/aoc2019/day06/a.txt"))]
      (is (= expected actual))))
  (testing "tuple->map"
    (let [expected {"A" ["B" "C"], "D" ["E"]}
          actual (tuple->map {"A" ["B"] "D" ["E"]} ["A", "C"])]
      (is (= expected actual))))
  (testing "orbit-counter 1"
    (let [expected 1
          actual (orbit-counter {"A" ["B"], "B" ["C"]} ["C"] 0)]
      (is (= expected actual))))
  (testing "orbit-counter 2"
    (let [expected 2
          actual (orbit-counter {"A" ["B"], "B" ["C"]} ["B"] 0)]
      (is (= expected actual))))
  (testing "orbit-counter 3"
    (let [expected 6
          actual (orbit-counter {"D" ["E" "I"], "E" ["F" "J"], "J" ["K"], "K" ["L"]} ["E" "I"] 0)]
      (is (= expected actual))))
  (testing "day06a"
    (let [expected 42
          actual (day06a "src/aoc2019/day06/a.txt")]
      (is (= expected actual))))
  (testing "day06a"
    (let [expected 151345
          actual (day06a "src/aoc2019/day06/input.txt")]
      (is (= expected actual))))
  (testing "neighbors"
    (let [expected ["D"]
          actual (neighbors {"A" ["B" "C"], "B" ["D"], "C" ["E"]} ["A"] "B")]
      (is (= expected actual))))
  (testing "neighbors"
    (let [expected []
          actual (neighbors {"A" ["B"], "B" ["D"]} ["B"] "D")]
      (is (= expected actual))))
  (testing "day06b"
    (let [expected 5
          actual (day06b "src/aoc2019/day06/b.txt")]
      (is (= expected actual)))))