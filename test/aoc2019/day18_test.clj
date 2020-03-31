(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
  (testing "parse"
    (let [expected  {[0 0] :wall [1 0] :wall [2 0] :wall [3 0] :wall [4 0] :wall [5 0] :wall [6 0] :wall [7 0] :wall [8 0] :wall
                     [0 -1] :wall [1 -1] :b [2 -1] :open [3 -1] :A [4 -1] :open [5 -1] :current [6 -1] :open [7 -1] :a [8 -1] :wall
                     [0 -2] :wall [1 -2] :wall [2 -2] :wall [3 -2] :wall [4 -2] :wall [5 -2] :wall [6 -2] :wall [7 -2] :wall [8 -2] :wall}
          actual (parse "src/aoc2019/day18/a.txt")]
      (is (= expected actual))))
  (testing "testcase a"
    (let [expected 8
          actual (day18a (parse "src/aoc2019/day18/a.txt"))]
      (is (= expected actual))))
  (testing "testcase b"
    (let [expected 86
          actual (day18a (parse "src/aoc2019/day18/b.txt"))]
      (is (= expected actual))))
  (testing "testcase c"
    (let [expected 132
          actual (day18a (parse "src/aoc2019/day18/c.txt"))]
      (is (= expected actual))))
  (testing "testcase d"
    (let [expected 136
          actual (day18a (parse "src/aoc2019/day18/d.txt"))]
      (is (= expected actual))))
  (testing "testcase e"
    (let [expected 81
          actual (day18a (parse "src/aoc2019/day18/e.txt"))]
      (is (= expected actual))))
  (testing "day18a"
    (let [expected 5450
          actual (day18a (parse "src/aoc2019/day18/input.txt"))]
      (is (= expected actual))))
  (testing "testcase a2"
    (let [expected 8
          actual (day18b (parse "src/aoc2019/day18/a2.txt"))]
      (is (= expected actual))))
  (testing "testcase b2"
    (let [expected 24
          actual (day18b (parse "src/aoc2019/day18/b2.txt"))]
      (is (= expected actual))))
  (testing "testcase c2"
    (let [expected 32
          actual (day18b (parse "src/aoc2019/day18/c2.txt"))]
      (is (= expected actual))))
  (testing "testcase d2"
    (let [expected 72
          actual (day18b (parse "src/aoc2019/day18/d2.txt"))]
      (is (= expected actual))))
  (testing "day18b"
    (let [expected 2020
          actual (day18b (parse "src/aoc2019/day18/input2.txt"))]
      (is (= expected actual)))))