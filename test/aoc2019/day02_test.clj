(ns aoc2019.day02-test
  (:require [clojure.test :refer :all]
            [aoc2019.day02.core :refer :all]))

(deftest day02
  (testing "a"
    (def expected [3500 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (intCode [1 9 10 3 2 3 11 0 99 30 40 50]))
    (is (= expected actual)))
)