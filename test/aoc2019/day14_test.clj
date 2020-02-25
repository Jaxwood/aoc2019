(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
    (def fixture {
      :A {:amount 10 :requires [[:ORE 10]]}
      :B {:amount 1 :requires [[:ORE 1]]}
      :C {:amount 1 :requires [[:A 7] [:B 1]]}
      :D {:amount 1 :requires [[:A 7] [:C 1]]}
      :E {:amount 1 :requires [[:A 7] [:D 1]]}
      :FUEL {:amount 1 :requires [[:A 7] [:E 1]]}
    })
  (testing "parse"
    (def expected fixture)
    (def actual (parse "src/aoc2019/day14/input.txt"))
    (is (= expected actual)))
  (testing "day14a - part 1"
    (def expected 31)
    (def actual (day14a (parse "src/aoc2019/day14/input.txt")))
    (is (= expected actual)))
  (testing "day14a - part 2"
    (def expected 165)
    (def actual (day14a (parse "src/aoc2019/day14/input2.txt")))
    (is (= expected actual)))
  (testing "day14a - part 3"
    (def expected 13312)
    (def actual (day14a (parse "src/aoc2019/day14/input3.txt")))
    (is (= expected actual)))
  (testing "day14a - part 4"
    (def expected 180697)
    (def actual (day14a (parse "src/aoc2019/day14/input4.txt")))
    (is (= expected actual)))
  (testing "day14a - part 5"
    (def expected 2210736)
    (def actual (day14a (parse "src/aoc2019/day14/input5.txt")))
    (is (= expected actual)))
  (testing "day14a"
    (def expected 579797)
    (def actual (day14a (parse "src/aoc2019/day14/input6.txt")))
    (is (= expected actual)))
  (testing "day14b"
    (def expected 2521844)
    (def actual (day14b (parse "src/aoc2019/day14/input6.txt") 0 0 {} 2520000))
    (is (= expected actual))))
