(ns aoc2019.day13-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day13.core :refer :all]))

(deftest day13
  (testing "day13a"
    (let [expected 239
          actual (day13a (parse "src/aoc2019/day13/input.txt"))]
      (is (= expected actual))))
  (testing "day13b"
    (let [expected 12099
          actual (day13b (assoc (parse "src/aoc2019/day13/input.txt") 0 2))]
      (is (= expected actual)))))