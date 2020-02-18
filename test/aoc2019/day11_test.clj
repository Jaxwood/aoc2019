(ns aoc2019.day11-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [run parse]]
            [aoc2019.day11.core :refer :all]))

(deftest day11
  (testing "day11a"
    (def expected 0)
    (def actual (day11a
                 {:position [0 0 black] :direction :north :breadcrumbs {}}
                 {:memory (parse "src/aoc2019/day11/input.txt") :address 0 :relative 0 :input []}))
    (is (= expected actual))))