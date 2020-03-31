(ns aoc2019.day19-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day19.core :refer :all]))

(deftest day19
  (testing "day19a"
    (let [expected 0
          actual (day19a (parse "src/aoc2019/day19/input.txt"))]
      (is (= expected actual)))))