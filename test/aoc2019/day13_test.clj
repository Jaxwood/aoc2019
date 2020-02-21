(ns aoc2019.day13-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day13.core :refer :all]))

(deftest day13
  (testing "day13a"
    (def expected 239)
    (def actual (day13a (parse "src/aoc2019/day13/input.txt")))
    (is (= expected actual))))