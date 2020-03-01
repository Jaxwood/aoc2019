(ns aoc2019.day01-test
  (:require [clojure.test :refer :all]
            [aoc2019.day01.core :refer :all]))

(deftest day01
  (testing "a"
    (let [expected 2
          actual (calculateFuel 12)]
      (is (= expected actual))))
  (testing "b"
    (let [expected 2
          actual (calculateFuel 14)]
      (is (= expected actual))))
  (testing "c"
    (let [expected 654
          actual (calculateFuel 1969)]
      (is (= expected actual))))
  (testing "d"
    (let [expected 33583
          actual (calculateFuel 100756)]
      (is (= expected actual))))
  (testing "e"
    (let [expected 3386686
          actual (day01a "src/aoc2019/day01/input.txt")]
      (is (= expected actual)))))

(deftest day02
  (testing "a"
    (let [expected 2
          actual (calculateAdditionalFuel 14 0)]
      (is (= expected actual))))
  (testing "b"
    (let [expected 966
          actual (calculateAdditionalFuel 1969 0)]
      (is (= expected actual))))
  (testing "c"
    (let [expected 50346
          actual (calculateAdditionalFuel 100756 0)]
      (is (= expected actual))))
  (testing "d"
    (let [expected 5077155
          actual (day01b "src/aoc2019/day01/input.txt")]
      (is (= expected actual)))))