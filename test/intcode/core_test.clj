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
    (def actual (add memory (readInstruction memory 0)))
    (is (= expected actual)))
  (testing "multiply"
    (def memory [1 9 10 70 2 3 11 0 99 30 40 50])
    (def expected [3500 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (multiply memory (readInstruction memory 4)))
    (is (= expected actual)))
  (testing "run"
    (def expected 3500)
    (def actual (run [1 9 10 3 2 3 11 0 99 30 40 50] 0))
    (is (= expected (first actual)))))