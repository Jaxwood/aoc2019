(ns aoc2019.day04-test
  (:require [clojure.test :refer :all]
            [aoc2019.day04.core :refer :all]))

(deftest day04
  (testing "a"
    (def expected 6)
    (def actual (day04a []))
    (is (= expected actual))))