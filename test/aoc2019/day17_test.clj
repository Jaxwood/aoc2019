(ns aoc2019.day17-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day17.core :refer :all]))

(deftest day17
  (testing "day17a"
    (let [expected 4688
          actual (day17a (parse "src/aoc2019/day17/input.txt"))]
      (is (= expected actual))))
  (testing "translate"
    (let [expected [65 44 66 44 67 44 66 44 65 44 67 10]
          actual (translate "A,B,C,B,A,C")]
      (is (= expected actual))))
  (testing "day17b"
    (let [expected 0
          actual (day17b (parse "src/aoc2019/day17/input.txt"))]
      (is (= expected actual)))))