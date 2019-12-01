(ns aoc2019.day01-test
  (:require [clojure.test :refer :all]
            [aoc2019.day01.core :refer :all]))

(deftest day01
  (testing "a"
    (def expected 2)
    (def actual (calculateFuel 12))
    (is (= expected actual)))
  (testing "b"
    (def expected 2)
    (def actual (calculateFuel 14))
    (is (= expected actual)))
  (testing "c"
    (def expected 654)
    (def actual (calculateFuel 1969))
    (is (= expected actual)))
  (testing "d"
    (def expected 33583)
    (def actual (calculateFuel 100756))
    (is (= expected actual)))
  (testing "e"
    (def expected 3386686)
    (def actual (day01a "src/aoc2019/day01/input.txt"))
    (is (= expected actual))))

(deftest day02
  (testing "a"
    (def expected 2)
    (def actual (calculateAdditionalFuel 14 0))
    (is (= expected actual)))
  (testing "b"
    (def expected 966)
    (def actual (calculateAdditionalFuel 1969 0))
    (is (= expected actual)))
  (testing "c"
    (def expected 50346)
    (def actual (calculateAdditionalFuel 100756 0))
    (is (= expected actual)))
  (testing "d"
    (def expected 0)
    (def actual (day01b "src/aoc2019/day01/input.txt"))
    (is (= expected actual))))