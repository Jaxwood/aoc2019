(ns aoc2019.day21-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day21.core :refer :all]))

(deftest day21
  (testing "day21a"
    (let [expected 19348840
          actual (day21a (parse "src/aoc2019/day21/input.txt"))]
      (is (= expected actual))))
  (testing "day21b"
    (let [expected 1141857182
          actual (day21b (parse "src/aoc2019/day21/input.txt"))]
      (is (= expected actual)))))