(ns aoc2019.day23-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day23.core :refer :all]))

(deftest day23
  (testing "day23a"
    (let [expected 18513
          actual (day23a (parse "src/aoc2019/day23/input.txt"))]
      (is (= expected actual)))))