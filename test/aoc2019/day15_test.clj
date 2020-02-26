(ns aoc2019.day15-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day15.core :refer :all]))

(deftest day15
  (testing "day15a"
    (def expected 218)
    (def actual (day15a (parse "src/aoc2019/day15/input.txt")))
    (is (= expected actual))))