(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
  (testing "unlocks"
    (let [expected :A
          actual (unlocks :a)]
      (is (= expected actual))))
  (testing "day18a testcase a"
    (let [expected 8
          actual (day18a (parse "src/aoc2019/day18/a.txt"))]
      (is (= expected actual))))
  (testing "day18a testcase b"
    (let [expected 86
          actual (day18a (parse "src/aoc2019/day18/b.txt"))]
      (is (= expected actual))))
  (testing "day18a testcase c"
    (let [expected 132
          actual (day18a (parse "src/aoc2019/day18/c.txt"))]
      (is (= expected actual))))
  ;; (testing "day18a testcase d"
    ;; (let [expected 136
          ;; actual (day18a (parse "src/aoc2019/day18/d.txt"))]
      ;; (is (= expected actual))))
  (testing "day18a testcase e"
    (let [expected 81
          actual (day18a (parse "src/aoc2019/day18/e.txt"))]
      (is (= expected actual)))))