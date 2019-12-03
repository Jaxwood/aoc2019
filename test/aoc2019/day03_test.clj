(ns aoc2019.day03-test
  (:require [clojure.test :refer :all]
            [aoc2019.day03.core :refer :all]))

(deftest day03
  (testing "a"
    (def expected 6)
    (def actual (day03a ["R8,U5,L5,D3", "U7,R6,D4,L4"]))
    (is (= expected actual)))
  (testing "b"
    (def expected 159)
    (def actual (day03a ["R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83"]))
    (is (= expected actual)))
  (testing "c"
    (def expected 135)
    (def actual (day03a ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"]))
    (is (= expected actual)))
  (testing "parse"
    (def expected ["R8" "U5" "L5" "D3"]))
    (def actual (parse "R8,U5,L5,D3"))
    (is (= expected actual))
  (testing "toTuples"
    (def expected [["R" 8] ["U" 5] ["L" 5] ["D" 3]]))
    (def actual (map toTuples ["R8" "U5" "L5" "D3"]))
    (is (= expected actual))
  (testing "generateX"
    (def expected [[1 0] [2 0] [3 0]]))
    (def actual (take 3 (generateX [0 0] inc)))
    (is (= expected actual))
  (testing "generateY"
    (def expected [[0 -1] [0 -2] [0 -3]]))
    (def actual (take 3 (generateY [0 0] dec)))
    (is (= expected actual))
  (testing "path right"
    (def expected [[1 0] [2 0] [3 0]])
    (def actual (path [0 0] ["R" 3]))
    (is (= expected actual)))
  (testing "path left"
    (def expected [[-1 0] [-2 0] [-3 0]])
    (def actual (path [0 0] ["L" 3]))
    (is (= expected actual)))
  (testing "path up"
    (def expected [[0 1] [0 2] [0 3]])
    (def actual (path [0 0] ["U" 3]))
    (is (= expected actual)))
  (testing "path down"
    (def expected [[0 -1] [0 -2] [0 -3]])
    (def actual (path [0 0] ["D" 3]))
    (is (= expected actual)))
  (testing "follow"
    (def expected [[0 -1] [0 -2] [0 -3] [0 -2] [0 -1] [0 0]])
    (def actual (follow [] [0 0] [["D" 3] ["U" 3]]))
    (is (= expected actual))))