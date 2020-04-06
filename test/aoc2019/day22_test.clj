(ns aoc2019.day22-test
  (:require [clojure.test :refer :all]
            [aoc2019.day22.core :refer :all]))

(deftest day22
  (testing "deal to new stack"
    (let [expected [9 8 7 6 5 4 3 2 1 0]
          actual (deal [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual))))
  (testing "deal-indexed to new stack"
    (let [expected 1
          actual (deal-indexed (count (range 10)) 0 8)]
      (is (= expected actual))))
  (testing "deal-indexed-reverse to new stack"
    (let [expected 8
          actual (deal-indexed (count (range 10)) 0 1)]
      (is (= expected actual))))
  (testing "cut positive number of cards"
    (let [expected [3 4 5 6 7 8 9 0 1 2]
          actual (cut [0 1 2 3 4 5 6 7 8 9] 3)]
      (is (= expected actual))))
  (testing "cut negative number of cards"
    (let [expected [6 7 8 9 0 1 2 3 4 5]
          actual (cut [0 1 2 3 4 5 6 7 8 9] -4)]
      (is (= expected actual))))
  (testing "cut-indexed positive"
    (let [expected 2016
          actual (cut-indexed 5000 3 2019)]
      (is (= expected actual))))
  (testing "cut-indexed positive overflow"
    (let [expected 4999
          actual (cut-indexed 5000 3 2)]
      (is (= expected actual))))
  (testing "cut-indexed negative"
    (let [expected 2022
          actual (cut-indexed 5000 -3 2019)]
      (is (= expected actual))))
  (testing "cut-indexed negative overflow"
    (let [expected 2
          actual (cut-indexed 5000 -3 4999)]
      (is (= expected actual))))
  (testing "increment the cards"
    (let [expected [0 7 4 1 8 5 2 9 6 3]
          actual (increment [0 1 2 3 4 5 6 7 8 9] 3)]
      (is (= expected actual))))
  (testing "increment-indexed the cards"
    (let [expected 1
          actual (increment-indexed (count [0 1 2 3 4 5 6 7 8 9]) 7 3)]
      (is (= expected actual))))
  (testing "day22a"
    (let [expected 4775
          instructions (parse "src/aoc2019/day22/input.txt" [deal-indexed cut-indexed increment-indexed]) 
          actual (day22a instructions 10007 2019)]
      (is (= expected actual)))))
