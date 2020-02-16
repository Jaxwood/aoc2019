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
    (def actual (run [1 9 10 3 2 3 11 0 99 30 40 50] 0 0 0))
    (is (= expected (first actual))))
  (testing "day05a"
    (def expected 4511442)
    (def actual (run (parse "src/aoc2019/day05/input.txt") 0 0 1))
    (is (= expected actual)))
  (testing "day05b"
    (def expected 12648139)
    (def actual (run (parse "src/aoc2019/day05/input.txt") 0 0 5))
    (is (= expected actual))))