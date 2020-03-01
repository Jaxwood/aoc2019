(ns aoc2019.day09-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day09.core :refer :all]))

(deftest day09
  (testing "day09a"
    (let [expected 3345854957
          actual (day09a (parse "src/aoc2019/day09/input.txt"))]
      (is (= expected actual))))
  (testing "day09b"
    (let [expected 68938
          actual (day09b (parse "src/aoc2019/day09/input.txt"))]
      (is (= expected actual)))))