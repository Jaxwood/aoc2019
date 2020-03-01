(ns intcode.core-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer :all]))

(deftest intcode
  (testing "parse"
    (let [expected [1 9 10 3 2 3 11 0 99 30 40 50]
          actual (parse "test/intcode/parse.txt")]
      (is (= expected actual))))
  (testing "digits"
    (let [expected [0 1 2 3 4]
          actual (digits 1234 [])]
      (is (= expected actual))))
  (testing "add"
    (let [memory [1 9 10 3 2 3 11 0 99 30 40 50]
          expected [1 9 10 70 2 3 11 0 99 30 40 50]
          actual (add memory (read-instruction memory 0 0))]
      (is (= expected actual))))
  (testing "multiply"
    (let [memory [1 9 10 70 2 3 11 0 99 30 40 50]
          expected [3500 9 10 70 2 3 11 0 99 30 40 50]
          actual (multiply memory (read-instruction memory 4 0))]
      (is (= expected actual))))
  (testing "input"
    (let [memory [3,0,4,0,99]
          expected [1,0,4,0,99]
          actual (in memory (read-instruction memory 0 0) [1])]
      (is (= expected actual))))
  (testing "output"
    (let [memory [3,0,4,0,99]
          expected 3
          actual (out memory (read-instruction memory 2 0))]
      (is (= expected actual))))
  (testing "run"
    (let [expected 3500
          actual (run {:memory [1 9 10 3 2 3 11 0 99 30 40 50] :address 0 :relative 0 :input [0]})]
      (is (= expected (first (:memory actual))))))
  (testing "day05b"
    (let [expected 12648139
          actual (run {:memory (parse "src/aoc2019/day05/input.txt") :address 0 :relative 0 :input [5]})]
      (is (= expected (:output actual)))))
  (testing "day09a"
    (let [expected 3345854957
          actual (run {:memory (parse "src/aoc2019/day09/input.txt") :address 0 :relative 0 :input [1]})]
      (is (= expected (:output actual)))))
  (testing "day09b"
    (let [expected 68938
          actual (run {:memory (parse "src/aoc2019/day09/input.txt") :address 0 :relative 0 :input [2]})]
      (is (= expected (:output actual))))))