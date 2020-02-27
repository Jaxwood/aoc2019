(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "repeating"
    (def expected '(0 0 1 1 1 0 0 0 -1 -1 -1 0))
    (def actual (repeating 3 12))
    (is (= expected actual)))
  (testing "string->numbers"
    (def expected '(0 1 4 2 5 3))
    (def actual (string->numbers "014253"))
    (is (= expected actual)))
  (testing "fft"
    (def expected "48226158")
    (def actual (fft 1 '(1 2 3 4 5 6 7 8) ""))
    (is (= expected actual)))
  (testing "day16a testcase"
    (def expected "01029498")
    (def actual (day16a "12345678" 4))
    (is (= expected actual)))
  (testing "day16a"
    (def expected "73127523")
    (def actual (reduce str (take 8 (day16a (slurp "src/aoc2019/day16/input.txt") 100))))
    (is (= expected actual))))
