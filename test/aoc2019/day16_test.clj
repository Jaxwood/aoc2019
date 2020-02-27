(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "reating"
    (def expected '(0 0 1 1 1 0 0 0 -1 -1 -1 0))
    (def actual (repeating 3 12))
    (is (= expected actual)))
  (testing "string->numbers"
    (def expected '(0 1 4 2 5 3))
    (def actual (string->numbers "014253"))
    (is (= expected actual))))