(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "reating"
    (def expected [0 0 1 1 1 0 0 0 -1 -1 -1 0])
    (def actual (repeating 3 12))
    (is (= expected actual))))