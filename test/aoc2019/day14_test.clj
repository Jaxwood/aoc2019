(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
    (def fixture {
      :A {:amount 10 :requires [[10 :ORE]]}
      :B {:amount 1 :requires [[1 :ORE]]}
      :C {:amount 1 :requires [[7 :A] [1 :B]]}
      :D {:amount 1 :requires [[7 :A] [1 :C]]}
      :E {:amount 1 :requires [[7 :A] [1 :D]]}
      :FUEL {:amount 1 :requires [[7 :A] [1 :E]]}
    })
  (testing "parse"
    (def expected fixture)
    (def actual (parse "src/aoc2019/day14/input.txt"))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 31)
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 165)
    (def actual (day14a (parse "src/aoc2019/day14/input2.txt")))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 13312)
    (def actual (day14a (parse "src/aoc2019/day14/input3.txt")))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 180697)
    (def actual (day14a (parse "src/aoc2019/day14/input4.txt")))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 2210736)
    (def actual (day14a (parse "src/aoc2019/day14/input5.txt")))
    (is (= expected actual))))
