(ns aoc2019.day20-test
  (:require [clojure.test :refer :all]
            [aoc2019.day20.core :refer :all]))

(deftest day20
  (testing "day20a - testcase a"
    (let [expected 23
          actual (day20a (parse "src/aoc2019/day20/a.txt"))]
      (is (= expected actual))))
  (testing "day20a - testcase b"
    (let [expected 58
          actual (day20a (parse "src/aoc2019/day20/b.txt"))]
      (is (= expected actual))))
  (testing "day20a - solution"
    (let [expected 528
          actual (day20a (parse "src/aoc2019/day20/input.txt"))]
      (is (= expected actual))))
  (testing "day20b - testcase a"
    (let [expected 396
          actual (day20b (parse "src/aoc2019/day20/c.txt"))]
      (is (= expected actual)))))