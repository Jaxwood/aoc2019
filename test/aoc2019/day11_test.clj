(ns aoc2019.day11-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [run parse]]
            [aoc2019.day11.core :refer :all]))

(deftest day11
  (testing "north; move left"
    (def expected {:position [-1 0 black] :direction :west :breadcrumbs { [0 0] white}})
    (def actual (move {:position [0 0 black] :direction :north :breadcrumbs {}} 1 0))
    (is (= expected actual)))
  (testing "west; move left"
    (def expected {:position [-1 -1 black] :direction :south :breadcrumbs { [-1 0] black}})
    (def actual (move {:position [-1 0 black] :direction :west :breadcrumbs {}} 0 0))
    (is (= expected actual)))
  (testing "south; move left"
    (def expected {:position [0 -1 black] :direction :east :breadcrumbs { [-1 -1] white}})
    (def actual (move {:position [-1 -1 black] :direction :south :breadcrumbs {}} 1 0))
    (is (= expected actual)))
  (testing "east; move left"
    (def expected {:position [0 0 white] :direction :north :breadcrumbs { [0 0] white [0 -1] white}})
    (def actual (move {:position [0 -1 black] :direction :east :breadcrumbs {[0 0] white}} 1 0))
    (is (= expected actual)))
  (testing "north; move right"
    (def expected {:position [1 0 black] :direction :east :breadcrumbs {[0 0] black}})
    (def actual (move {:position [0 0 white] :direction :north :breadcrumbs {}} 0 1))
    (is (= expected actual)))
  (testing "day11a"
    (def expected 0)
    (def actual (day11a
                 {:position [0 0 black] :direction :north :breadcrumbs {}}
                 {:memory (parse "src/aoc2019/day11/input.txt") :address 0 :relative 0 :input []}))
    (is (= expected actual))))