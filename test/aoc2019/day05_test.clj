(ns aoc2019.day05-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer [parse]]
            [aoc2019.day05.core :refer :all]))

(deftest day05
  (testing "day05a"
    (let [expected 4511442
          actual (day05a {:memory (parse "src/aoc2019/day05/input.txt") :address 0 :relative 0 :input [1]})]
      (is (= expected actual))))
  (testing "day05b"
    (let [expected 12648139
          actual (day05b {:memory (parse "src/aoc2019/day05/input.txt") :address 0 :relative 0 :input [5]})]
      (is (= expected actual)))))