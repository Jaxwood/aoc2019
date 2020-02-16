(ns intcode.core-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer :all]))

(deftest intcode
  (testing "parse"
    (def expected [1 9 10 3 2 3 11 0 99 30 40 50])
    (def actual (parse "test/intcode/parse.txt"))
    (is (= expected actual)))
  (testing "digits"
    (def expected [0 1 2 3 4])
    (def actual (digits 1234 []))
    (is (= expected actual)))
  (testing "add"
    (def memory [1 9 10 3 2 3 11 0 99 30 40 50])
    (def expected [1 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (add memory (read-instruction memory 0 0)))
    (is (= expected actual)))
  (testing "multiply"
    (def memory [1 9 10 70 2 3 11 0 99 30 40 50])
    (def expected [3500 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (multiply memory (read-instruction memory 4 0)))
    (is (= expected actual)))
  (testing "input"
    (def memory [3,0,4,0,99])
    (def expected [1,0,4,0,99])
    (def actual (in memory (read-instruction memory 0 0) 1))
    (is (= expected actual)))
  (testing "output"
    (def memory [3,0,4,0,99])
    (def expected 3)
    (def actual (out memory (read-instruction memory 2 0)))
    (is (= expected actual)))
  (testing "run"
    (def expected 3500)
    (def actual (run {:memory [1 9 10 3 2 3 11 0 99 30 40 50] :address 0 :relative 0 :input 0}))
    (is (= expected (first (:memory actual)))))
  (testing "day05b"
    (def expected 12648139)
    (def actual (run {:memory (parse "src/aoc2019/day05/input.txt") :address 0 :relative 0 :input 5}))
    (is (= expected (:output actual))))
  (testing "day09a"
    (def expected 3345854957)
    (def actual (run {:memory (parse "src/aoc2019/day09/input.txt") :address 0 :relative 0 :input 1}))
    (is (= expected (:output actual))))
  (testing "day09b"
    (def expected 68938)
    (def actual (run {:memory (parse "src/aoc2019/day09/input.txt") :address 0 :relative 0 :input 2}))
    (is (= expected (:output actual)))))