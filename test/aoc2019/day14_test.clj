(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
  (testing "day14a"
    (def expected 0)
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual))))