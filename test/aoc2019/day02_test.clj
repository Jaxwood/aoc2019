(ns aoc2019.day02-test
  (:require [clojure.test :refer :all]
            [aoc2019.day02.core :refer :all]))

(deftest day02
  (testing "a"
    (def expected 2)
    (def actual 3)
    (is (= expected actual)))
)