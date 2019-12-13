(ns aoc2019.day09-test
  (:require [clojure.test :refer :all]
            [aoc2019.day09.core :refer :all]))

(deftest day09
  (testing "opcode"
    (def expected 1219070632396864) 
    (def actual (:output (opcode {:done false :output 0 :program [1102 34915192 34915192 7 4 7 99 0] :base 0 :signals [] :pointer 0})))
    (is (= expected actual)))
  (testing "opcode"
    (def expected 1125899906842624) 
    (def actual (:output (opcode {:done false :output 0 :program [104 1125899906842624 99] :base 0 :signals [] :pointer 0})))
    (is (= expected actual)))
  (testing "day09a"
    (def expected 3345854957) 
    (def actual (day09a (parse "src/aoc2019/day09/input.txt")))
    (is (= expected actual)))
  (testing "day09b"
    (def expected 68938) 
    (def actual (day09b (parse "src/aoc2019/day09/input.txt")))
    (is (= expected actual))))