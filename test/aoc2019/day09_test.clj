(ns aoc2019.day09-test
  (:require [clojure.test :refer :all]
            [aoc2019.day09.core :refer :all]))

(deftest day09
  (testing "opcode"
    (def expected [109 1 204 -1 1001 100 1 100 1008 100 16 101 1006 101 0 99])
    (def actual (runner {:done false :output 0 :program expected :base 0 :signals [] :pointer 0} []))
    (is (= expected actual)))
  (testing "opcode"
    (def expected 1219070632396864) 
    (def actual (:output (opcode {:done false :output 0 :program [1102 34915192 34915192 7 4 7 99 0] :base 0 :signals [] :pointer 0})))
    (is (= expected actual)))
  (testing "opcode"
    (def expected 1125899906842624) 
    (def actual (:output (opcode {:done false :output 0 :program [104 1125899906842624 99] :base 0 :signals [] :pointer 0})))
    (is (= expected actual))))