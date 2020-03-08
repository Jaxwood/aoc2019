(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
   (testing "parse"
     (let [expected  {[0 0] :wall [1 0] :wall [2 0] :wall [3 0] :wall [4 0] :wall [5 0] :wall [6 0] :wall [7 0] :wall [8 0] :wall
                      [0 -1] :wall [1 -1] :b [2 -1] :open [3 -1] :A [4 -1] :open [5 -1] :current [6 -1] :open [7 -1] :a [8 -1] :wall
                      [0 -2] :wall [1 -2] :wall [2 -2] :wall [3 -2] :wall [4 -2] :wall [5 -2] :wall [6 -2] :wall [7 -2] :wall [8 -2] :wall}
           actual (parse "src/aoc2019/day18/a.txt")]
       (is (= expected actual))))
   (testing "left"
     (let [expected [-1 0]
           actual (left [0 0])]
       (is (= expected actual))))
   (testing "right"
     (let [expected [1 0]
           actual (right [0 0])]
       (is (= expected actual))))
   (testing "up"
     (let [expected [0 1]
           actual (up [0 0])]
       (is (= expected actual))))
   (testing "down"
     (let [expected [0 -1]
           actual (down [0 0])]
       (is (= expected actual))))
   (testing "neighbors"
     (let [expected [[4 -1] [6 -1]]
           actual (neighbors (parse "src/aoc2019/day18/a.txt") [5 -1])]
       (is (= expected actual)))))