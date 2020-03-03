(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
  (testing "unlocks"
    (let [expected :A
          actual (unlocks :a)]
      (is (= expected actual))))
  (testing "day18a"
    (let [expected 8
          actual (day18a (parse "src/aoc2019/day18/a.txt"))]
      (is (= expected actual)))))