(ns aoc2019.day05-test
  (:require [clojure.test :refer :all]
            [aoc2019.day05.core :refer :all]))

(deftest day05
  (testing "pointer 1"
    (def expected 4)
    (def actual (pointer 1))
    (is (= expected actual)))
  (testing "pointer 2"
    (def expected 4)
    (def actual (pointer 2))
    (is (= expected actual)))
  (testing "pointer 3"
    (def expected 2)
    (def actual (pointer 3))
    (is (= expected actual)))
  (testing "pointer 4"
    (def expected 2)
    (def actual (pointer 4))
    (is (= expected actual))))