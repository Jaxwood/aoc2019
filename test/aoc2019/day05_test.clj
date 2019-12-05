(ns aoc2019.day05-test
  (:require [clojure.test :refer :all]
            [aoc2019.day05.core :refer :all]))

(deftest day05
  (testing "pointer a"
    (def expected 4)
    (def actual (pointer 1))
    (is (= expected actual)))
  (testing "pointer b"
    (def expected 4)
    (def actual (pointer 2))
    (is (= expected actual)))
  (testing "pointer c"
    (def expected 2)
    (def actual (pointer 3))
    (is (= expected actual)))
  (testing "pointer d"
    (def expected 2)
    (def actual (pointer 4))
    (is (= expected actual)))
  (testing "digits"
    (def expected [0 1 0 0 2])
    (def actual (digits 1002 []))
    (is (= expected actual)))
  (testing "digits"
    (def expected [1 2 3 4 5])
    (def actual (digits 12345 []))
    (is (= expected actual)))
  (testing "digits"
    (def expected [0 0 0 0 1])
    (def actual (digits 1 []))
    (is (= expected actual))))