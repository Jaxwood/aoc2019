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
  (testing "reciepe"
    (def expected {:A 28 :E 1 :D 1 :C 1 :B 1})
    (def actual (reciepe (parse "src/aoc2019/day14/input.txt") {} (:requires (:FUEL fixture))))
    (is (= expected actual)))
  (testing "ore-producing?"
    (def expected true)
    (def actual (ore-producing? (parse "src/aoc2019/day14/input.txt") [:A 28]))
    (is (= expected actual)))
  (testing "ore-producing?"
    (def expected false)
    (def actual (ore-producing? (parse "src/aoc2019/day14/input.txt") [:C 1]))
    (is (= expected actual)))
  (testing "calculate ore needed"
    (def expected 31)
    (def actual (calculate (parse "src/aoc2019/day14/input.txt") [[:A 28] [:B 1]] 0))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 31)
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual))))