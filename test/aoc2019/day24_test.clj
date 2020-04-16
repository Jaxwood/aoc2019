(ns aoc2019.day24-test
  (:require [clojure.test :refer :all]
            [aoc2019.day24.core :refer :all]))

(deftest day24
  (testing "find the diversity rating for a board"
    (let [expected 2129920
          actual (biodiversity-rating [
            [0 0 :space] [1 0 :space] [2 0 :space] [3 0 :space] [4 0 :space]
            [0 1 :space] [1 1 :space] [2 1 :space] [3 1 :space] [4 1 :space]
            [0 2 :space] [1 2 :space] [2 2 :space] [3 2 :space] [4 2 :space]
            [0 2 :bug] [1 2 :space] [2 2 :space] [3 2 :space] [4 2 :space]
            [0 2 :space] [1 2 :bug] [2 2 :space] [3 2 :space] [4 2 :space]])]
      (is (= expected actual))))
  (testing "day24a solution"
    (let [expected 18400817
          actual (day24a (parse "src/aoc2019/day24/input.txt"))]
      (is (= expected actual))))
  (testing "day24b solution"
    (let [expected 99
          actual (day24b (parse "src/aoc2019/day24/a.txt") 10)]
      (is (= expected actual)))))