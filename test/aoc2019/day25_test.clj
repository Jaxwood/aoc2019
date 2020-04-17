(ns aoc2019.day25-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day25.core :refer :all]))

(deftest day25
  (testing "day25a"
    (let [expected ""
          actual (day25a (parse "src/aoc2019/day25/input.txt"))]
      (is (= expected actual)))))