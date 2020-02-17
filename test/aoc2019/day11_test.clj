(ns aoc2019.day11-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [run parse]]
            [aoc2019.day11.core :refer :all]))

(deftest day11
  (testing "int->color : black"
    (def expected :black)
    (def actual (int->color 0))
    (is (= expected actual)))
  (testing "int->color : white"
    (def expected :white)
    (def actual (int->color 1))
    (is (= expected actual)))
  (testing "color->int : 0"
    (def expected 0)
    (def actual (color->int :black))
    (is (= expected actual)))
  (testing "color->int : 1"
    (def expected 1)
    (def actual (color->int :white))
    (is (= expected actual)))
  (testing "int-direction : left"
    (def expected :left)
    (def actual (int->direction 0))
    (is (= expected actual)))
  (testing "int-direction : right"
    (def expected :right)
    (def actual (int->direction 1))
    (is (= expected actual)))
  (testing "set-input"
    (def expected {:memory [], :address 0, :relative 0, :input 1, :output 3})
    (def actual (set-input :white {:memory [] :address 0 :relative 0 :input 10 :output 3}))
    (is (= expected actual)))
  (testing "find-color"
    (def expected :white)
    (def actual (find-color 1 -1 [[0 0 :black] [1 -1 :black] [1 -1 :white]]))
    (is (= expected actual)))
  (testing "day11a"
    (def expected 0)
    (def actual (day11a
                 {:position [0 0 :black] :direction :north :breadcrumbs []}
                 {:memory (parse "src/aoc2019/day11/input.txt") :address 0 :relative 0 :input 0}))
    (is (= expected actual))))