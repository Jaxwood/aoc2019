(ns aoc2019.day06-test
  (:require [clojure.test :refer :all]
            [aoc2019.day06.core :refer :all]))

(deftest day06
  (testing "FIX ME"
    (def expected [["COM" "B"] ["B" "C"] ["C" "D"] ["D" "E"] ["E" "F"] ["B" "G"] ["G" "H"] ["D" "I"] ["E" "J"] ["J" "K"] ["K" "L"]])
    (def actual (parse (slurp "src/aoc2019/day06/a.txt")))
    (is (= expected actual)))
  (testing "tuple->map"
    (def expected { "A" ["B" "C"], "D" ["E"]})
    (def actual (tuple->map { "A" ["B"] "D" ["E"] } ["A", "C"]))
    (is (= expected actual)))
  (testing "orbit-counter 1"
    (def expected 1)
    (def actual (orbit-counter {"A" ["B"], "B" ["C"]} ["C"] 0))
    (is (= expected actual)))
  (testing "orbit-counter 2"
    (def expected 2)
    (def actual (orbit-counter {"A" ["B"], "B" ["C"]} ["B"] 0))
    (is (= expected actual)))
  (testing "orbit-counter 3"
    (def expected 6)
    (def actual (orbit-counter {"D" ["E" "I"], "E" ["F" "J"], "J" ["K"], "K" ["L"]} ["E" "I"] 0))
    (is (= expected actual)))
  (testing "day06a"
    (def expected 42)
    (def actual (day06a "src/aoc2019/day06/a.txt"))
    (is (= expected actual)))
  (testing "day06a"
    (def expected 151345)
    (def actual (day06a "src/aoc2019/day06/input.txt"))
    (is (= expected actual)))
  )