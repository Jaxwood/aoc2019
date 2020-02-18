(ns aoc2019.day02-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day02.core :refer :all]))

(deftest day02
  (testing "day02a"
    (def expected 3101878)
    (def memory (assoc (assoc (parse "src/aoc2019/day02/input.txt") 1 12) 2 2))
    (def actual (day02a {:memory memory :address 0 :relative 0 :input []}))
    (is (= expected actual)))
  (testing "day02b"
    (def expected 8444)
    (def actual (day02b {:memory (parse "src/aoc2019/day02/input.txt") :address 0 :relative 0 :input []} 0 0 19690720))
    (is (= expected actual))))