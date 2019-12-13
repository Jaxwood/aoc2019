(ns aoc2019.day10-test
  (:require [clojure.test :refer :all]
            [aoc2019.day10.core :refer :all]))

(deftest day10
  (testing "line of sight"
    (def expected false) 
    (def actual (los? [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]] [3 4] [1 0]))
    (is (= expected actual)))
  (testing "line of sight"
    (def expected true) 
    (def actual (los? [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]] [3 4] [1 2]))
    (is (= expected actual)))
  (testing "parse"
    (def expected [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]]) 
    (def actual (parse "src/aoc2019/day10/a.txt"))
    (is (= expected actual)))
  (testing "count visible planets"
    (def expected 8)
    (def actual (planet-counter [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]] [3 4] 0))
    (is (= expected actual)))
  (testing "find radar station")
    (def expected 8)
    (def actual (find-radar-station [[1 0] [4 0] [0 2] [1 2] [2 2] [3 2] [4 2] [4 3] [3 4] [4 4]]))
    (is (= expected actual)))