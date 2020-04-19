(ns aoc2019.day22-test
  (:require [clojure.test :refer :all]
            [aoc2019.day22.core :refer :all]))

(deftest day22
  (testing "deal to new stack"
    (let [expected 1
          actual (deal (count (range 10)) 0 8)]
      (is (= expected actual))))
  (testing "deal-reverse to new stack"
    (let [expected 8
          actual (deal (count (range 10)) 0 1)]
      (is (= expected actual))))
  (testing "cut positive"
    (let [expected 2016
          actual (cut 5000 3 2019)]
      (is (= expected actual))))
  (testing "cut positive overflow"
    (let [expected 4999
          actual (cut 5000 3 2)]
      (is (= expected actual))))
  (testing "cut negative"
    (let [expected 2022
          actual (cut 5000 -3 2019)]
      (is (= expected actual))))
  (testing "cut negative overflow"
    (let [expected 2
          actual (cut 5000 -3 4999)]
      (is (= expected actual))))
  (testing "increment the cards"
    (let [expected 1
          actual (increment (count [0 1 2 3 4 5 6 7 8 9]) 7 3)]
      (is (= expected actual))))
  (testing "day22a"
    (let [expected 4775
          instructions (parse "src/aoc2019/day22/input.txt" [deal cut increment]) 
          actual (day22a instructions 10007 2019)]
      (is (= expected actual)))))
