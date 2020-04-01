(ns aoc2019.day20-test
  (:require [clojure.test :refer :all]
            [aoc2019.day20.core :refer :all]))

(deftest day20
  (testing "day20a"
    (let [expected 23
          actual (day20a (parse "src/aoc2019/day20/a.txt"))]
      (is (= expected actual)))))