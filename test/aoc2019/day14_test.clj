(ns aoc2019.day14-test
  (:require [clojure.test :refer :all]
            [aoc2019.day14.core :refer :all]))

(deftest day14
  (def fixture {:A {:amount 10 :requires [[:ORE 10]]}
                :B {:amount 1 :requires [[:ORE 1]]}
                :C {:amount 1 :requires [[:A 7] [:B 1]]}
                :D {:amount 1 :requires [[:A 7] [:C 1]]}
                :E {:amount 1 :requires [[:A 7] [:D 1]]}
                :FUEL {:amount 1 :requires [[:A 7] [:E 1]]}})
  (testing "parse"
    (let [expected fixture
          actual (parse "src/aoc2019/day14/input.txt")]
      (is (= expected actual))))
  (testing "day14a - part 1"
    (let [expected 31
          actual (day14a (parse "src/aoc2019/day14/input.txt"))]
      (is (= expected actual))))
  (testing "day14a - part 2"
    (let [expected 165
          actual (day14a (parse "src/aoc2019/day14/input2.txt"))]
    (is (= expected actual))))
  (testing "day14a - part 3"
    (let [expected 13312
          actual (day14a (parse "src/aoc2019/day14/input3.txt"))]
      (is (= expected actual))))
  (testing "day14a - part 4"
    (let [expected 180697
          actual (day14a (parse "src/aoc2019/day14/input4.txt"))]
      (is (= expected actual))))
  (testing "day14a - part 5"
    (let [expected 2210736
          actual (day14a (parse "src/aoc2019/day14/input5.txt"))]
      (is (= expected actual))))
  (testing "day14a"
    (let [expected 579797
          actual (day14a (parse "src/aoc2019/day14/input6.txt"))]
      (is (= expected actual))))
  (testing "day14b"
    (let [expected 2521844
          actual (day14b (parse "src/aoc2019/day14/input6.txt") 0 0 {} 2520000)]
      (is (= expected actual)))))
