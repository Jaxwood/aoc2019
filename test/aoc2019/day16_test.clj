(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "repeating"
    (def expected '(0 0 1 1 1 0 0 0 -1 -1 -1 0))
    (def actual (repeating 3 12))
    (is (= expected actual)))
  (testing "number->digits"
    (def expected '(2 3 4 5))
    (def actual (number->digits 2345N))
    (is (= expected actual)))
  (testing "fft"
    (def expected '(4 8 2 2 6 1 5 8))
    (def actual (fft 1 '(1 2 3 4 5 6 7 8) []))
    (is (= expected actual)))
  (testing "day16a testcase"
    (def expected '(0 1 0 2 9 4 9 8))
    (def actual (day16a (number->digits (bigint "12345678")) 4))
    (is (= expected actual)))
  (testing "day16a"
    (def expected '(7 3 1 2 7 5 2 3))
    (def actual (time (take 8 (day16a (number->digits (bigint (slurp "src/aoc2019/day16/input.txt"))) 100))))
    (is (= expected actual))))
