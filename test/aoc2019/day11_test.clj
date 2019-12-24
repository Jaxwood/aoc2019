(ns aoc2019.day11-test
  (:require [clojure.test :refer :all]
            [aoc2019.day09.core :refer [parse]]
            [aoc2019.day11.core :refer :all]))

(deftest day11
  (testing "facing north move left"
    (def expected {:direction :west :breadcrumbs [[0 0]] :position [-1 0]}) 
    (def actual (move {:direction :north :breadcrumbs [] :position [0 0] } :left))
    (is (= expected actual)))
  (testing "facing north move right"
    (def expected {:direction :east :breadcrumbs [[0 0]] :position [1 0]}) 
    (def actual (move {:direction :north :breadcrumbs [] :position [0 0] } :right))
    (is (= expected actual)))
  (testing "facing south move left"
    (def expected {:direction :east :breadcrumbs [[0 0]] :position [1 0]}) 
    (def actual (move {:direction :south :breadcrumbs [] :position [0 0] } :left))
    (is (= expected actual)))
  (testing "facing south move right"
    (def expected {:direction :west :breadcrumbs [[0 0]] :position [-1 0]}) 
    (def actual (move {:direction :south :breadcrumbs [] :position [0 0] } :right))
    (is (= expected actual)))
  (testing "facing west move left"
    (def expected {:direction :south :breadcrumbs [[0 0]] :position [0 -1]}) 
    (def actual (move {:direction :west :breadcrumbs [] :position [0 0] } :left))
    (is (= expected actual)))
  (testing "facing west move right"
    (def expected {:direction :north :breadcrumbs [[0 0]] :position [0 1]}) 
    (def actual (move {:direction :west :breadcrumbs [] :position [0 0] } :right))
    (is (= expected actual)))
  (testing "facing east move left"
    (def expected {:direction :north :breadcrumbs [[0 0]] :position [0 1]}) 
    (def actual (move {:direction :east :breadcrumbs [] :position [0 0] } :left))
    (is (= expected actual)))
  (testing "facing east move right"
    (def expected {:direction :south :breadcrumbs [[0 0]] :position [0 -1]}) 
    (def actual (move {:direction :east :breadcrumbs [] :position [0 0] } :right))
    (is (= expected actual)))
  (testing "paint black if white"
    (def expected :black)
    (def actual (paint :white))
    (is (= expected actual)))
  (testing "paint white if black"
    (def expected :white)
    (def actual (paint :black))
    (is (= expected actual))))