(ns aoc2019.day22-test
  (:require [clojure.test :refer :all]
            [aoc2019.day22.core :refer :all]))

(deftest day22
  (testing "deal to new stack"
    (let [expected [9 8 7 6 5 4 3 2 1 0]
          actual (deal [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual))))
  (testing "cut positive number of cards"
    (let [expected [3 4 5 6 7 8 9 0 1 2]
          actual (cut [0 1 2 3 4 5 6 7 8 9] 3)]
      (is (= expected actual))))
  (testing "cut negative number of cards"
    (let [expected [6 7 8 9 0 1 2 3 4 5]
          actual (cut [0 1 2 3 4 5 6 7 8 9] -4)]
      (is (= expected actual))))
  (testing "increment the cards"
    (let [expected [0 7 4 1 8 5 2 9 6 3]
          actual (increment [0 1 2 3 4 5 6 7 8 9] 3)]
      (is (= expected actual))))
  (testing "testcase a"
    (let [expected [0 3 6 9 2 5 8 1 4 7]
          actual (day22a [{:name :increment :n 7}
                          {:name :deal}
                          {:name :deal}]
                         [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual))))
  (testing "testcase b"
    (let [expected [3 0 7 4 1 8 5 2 9 6]
          actual (day22a [{:name :cut :n 6}
                          {:name :increment :n 7}
                          {:name :deal}]
                         [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual))))
  (testing "testcase c"
    (let [expected [6 3 0 7 4 1 8 5 2 9]
          actual (day22a [{:name :increment :n 7}
                          {:name :increment :n 9}
                          {:name :cut :n -2}]
                         [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual))))
  (testing "testcase d"
    (let [expected [9 2 5 8 1 4 7 0 3 6]
          actual (day22a [{:name :deal}
                          {:name :cut :n -2}
                          {:name :increment :n 7}
                          {:name :cut :n 8}
                          {:name :cut :n -4}
                          {:name :increment :n 7}
                          {:name :cut :n 3}
                          {:name :increment :n 9}
                          {:name :increment :n 3}
                          {:name :cut :n -1}]
                         [0 1 2 3 4 5 6 7 8 9])]
      (is (= expected actual)))))