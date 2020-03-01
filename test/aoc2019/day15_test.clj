(ns aoc2019.day15-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day15.core :refer :all]))

(deftest day15
  (testing "day15a"
    (let [expected 218
          actual (day15a (parse "src/aoc2019/day15/input.txt"))]
      (is (= expected actual))))
  (testing "day15b"
    (let [expected 544
          actual (day15b (parse "src/aoc2019/day15/input.txt"))]
      (is (= expected actual)))))