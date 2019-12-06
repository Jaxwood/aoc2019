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
  )