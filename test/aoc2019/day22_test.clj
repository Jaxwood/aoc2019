(ns aoc2019.day22-test
  (:require [clojure.test :refer :all]
            [aoc2019.day22.core :refer :all]))

(deftest day22
  (testing "day22a"
    (let [expected 4775
          instructions (parse "src/aoc2019/day22/input.txt" [deal cut increment]) 
          actual (first (day22a instructions 10007 [2019 1 0]))]
      (is (= expected actual))))
  (testing "day22b"
    (let [expected 37889219674304
          instructions (parse "src/aoc2019/day22/input.txt" [deal cut increment])
          actual (day22b instructions (biginteger 119315717514047) [2020 1 0] (biginteger 101741582076661))]
      (is (= expected actual)))))
