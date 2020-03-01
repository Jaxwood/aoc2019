(ns aoc2019.day11-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [run parse]]
            [aoc2019.day11.core :refer :all]))

(deftest day11
  (testing "north; move left"
    (let [expected {:position [-1 0 black] :direction :west :breadcrumbs {[0 0] white}}
          actual (move {:position [0 0 black] :direction :north :breadcrumbs {}} 1 0)]
      (is (= expected actual))))
  (testing "west; move left"
    (let [expected {:position [-1 -1 black] :direction :south :breadcrumbs {[-1 0] black}}
          actual (move {:position [-1 0 black] :direction :west :breadcrumbs {}} 0 0)]
      (is (= expected actual))))
  (testing "south; move left"
    (let [expected {:position [0 -1 black] :direction :east :breadcrumbs {[-1 -1] white}}
          actual (move {:position [-1 -1 black] :direction :south :breadcrumbs {}} 1 0)]
      (is (= expected actual))))
  (testing "east; move left"
    (let [expected {:position [0 0 white] :direction :north :breadcrumbs {[0 0] white [0 -1] white}}
          actual (move {:position [0 -1 black] :direction :east :breadcrumbs {[0 0] white}} 1 0)]
      (is (= expected actual))))
  (testing "north; move right"
    (let [expected {:position [1 0 black] :direction :east :breadcrumbs {[0 0] black}}
          actual (move {:position [0 0 white] :direction :north :breadcrumbs {}} 0 1)]
      (is (= expected actual))))
  (testing "day11a"
    (let [expected 2339
          actual (day11a
                  {:position [0 0 black] :direction :north :breadcrumbs {}}
                  {:memory (parse "src/aoc2019/day11/input.txt") :address 0 :relative 0 :input []})]
      (is (= expected actual))))
  (testing "day11b"
    (let [expected 249
          actual (day11b
                  {:position [0 0 white] :direction :north :breadcrumbs {}}
                  {:memory (parse "src/aoc2019/day11/input.txt") :address 0 :relative 0 :input []})]
      (is (= expected actual)))))