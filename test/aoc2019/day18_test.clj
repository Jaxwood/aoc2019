(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
  (testing "day18a"
    (let [expected 0
          actual (day18a (parse "src/aoc2019/day18/a.txt"))]
      (is (= expected actual)))))