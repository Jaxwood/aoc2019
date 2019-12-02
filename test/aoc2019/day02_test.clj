(ns aoc2019.day02-test
  (:require [clojure.test :refer :all]
            [aoc2019.day02.core :refer :all]))

(deftest day02
  (testing "a"
    (def expected [3500 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (intCode [1 9 10 3 2 3 11 0 99 30 40 50] 0))
    (is (= expected actual)))
  (testing "b"
    (def expected [2 0 0 0 99])
    (def actual (intCode [1 0 0 0 99] 0))
    (is (= expected actual)))
  (testing "c"
    (def expected [2 3 0 6 99])
    (def actual (intCode [2 3 0 3 99] 0))
    (is (= expected actual)))
  (testing "d"
    (def expected [2 4 4 5 99 9801])
    (def actual (intCode [2 4 4 5 99 0] 0))
    (is (= expected actual)))
  (testing "e"
    (def expected [30 1 1 4 2 5 6 0 99])
    (def actual (intCode [1 1 1 4 99 5 6 0 99] 0))
    (is (= expected actual)))
  (testing "getOperation"
    (def expected 6)
    (def actual (getOperation 2 2 3))
    (is (= expected actual)))
  (testing "day02a"
    (def expected [])
    (def actual (day02a "src/aoc2019/day02/input.txt"))
    (is (= expected actual)))
)