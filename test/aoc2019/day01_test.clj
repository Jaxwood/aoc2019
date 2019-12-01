(ns aoc2019.day01-test
  (:require [clojure.test :refer :all]
            [aoc2019.day01.core :refer :all]))

(deftest day01
  (testing "a"
    (def expected 2)
    (def actual (day01a 12))
    (is (= expected actual)))
  (testing "b"
    (def expected 2)
    (def actual (day01a 14))
    (is (= expected actual)))
  (testing "c"
    (def expected 654)
    (def actual (day01a 1969))
    (is (= expected actual)))
  (testing "d"
    (def expected 33583)
    (def actual (day01a 100756))
    (is (= expected actual))))