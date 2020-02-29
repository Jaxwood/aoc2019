(ns aoc2019.day16-test
  (:require [clojure.test :refer :all]
            [aoc2019.day16.core :refer :all]))

(deftest day16
  (testing "calculate"
    (def expected 4)
    (def actual (calculate [1 2 3 4 5 6 7 8] 1))
    (is (= expected actual)))
  (testing "fft"
    (def expected '(0 1 0 2 9 4 9 8))
    (def actual (fft [1 2 3 4 5 6 7 8] 4))
    (is (= expected actual)))
  (testing "day16a"
    (def expected '(7 3 1 2 7 5 2 3))
    (def actual (day16a "src/aoc2019/day16/input.txt" 100))
    (is (= expected actual)))
  (testing "day16b"
    (def expected '(8 4 4 6 2 0 2 6))
    (def actual (day16b "03036732577212944063491565474664" 100))
    (is (= expected actual))))
