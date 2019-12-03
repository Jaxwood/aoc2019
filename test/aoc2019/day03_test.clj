(ns aoc2019.day03-test
  (:require [clojure.test :refer :all]
            [aoc2019.day03.core :refer :all]))

(deftest day03
  (testing "a"
    (def expected 6)
    (def actual (day03a ["R8,U5,L5,D3", "U7,R6,D4,L4"]))
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