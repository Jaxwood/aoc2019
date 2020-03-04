(ns aoc2019.day18-test
  (:require [clojure.test :refer :all]
            [aoc2019.day18.core :refer :all]))

(deftest day18
  (testing "unlocks"
    (let [expected :A
          actual (unlocks :a)]
      (is (= expected actual))))
  (testing "open with key and door"
    (let [expected {[0 0] :open [1 1] :open}
          actual (open {:vault {[0 0] :a [1 1] :A} :current [0 0] :from [1 1] :visited {}})]
      (is (= expected actual))))
  (testing "open with key only"
    (let [expected {[0 0] :open [1 1] :wall}
          actual (open {:vault {[0 0] :a [1 1] :wall} :current [0 0] :from [1 1] :visited {}})]
      (is (= expected actual))))
  (testing "unvisited?"
    (let [expected [[1 0]]
          actual (unvisited? {:vault {[0 0] :open [0 1] :wall [0 -1] :wall [1 0] :open [-1 0] :wall} :current [0 0] :from [1 1] :visited {}})]
      (is (= expected actual))))
   (testing "day18a testcase a"
     (let [expected 8
           actual (day18a (parse "src/aoc2019/day18/a.txt"))]
       (is (= expected actual))))
  (testing "day18a testcase b"
    (let [expected 86
          actual (day18a (parse "src/aoc2019/day18/b.txt"))]
      (is (= expected actual))))
  (testing "day18a testcase c"
    (let [expected 132
          actual (day18a (parse "src/aoc2019/day18/c.txt"))]
      (is (= expected actual))))
  ;;(testing "day18a testcase d"
    ;;(let [expected 136
          ;;actual (day18a (parse "src/aoc2019/day18/d.txt"))]
      ;;(is (= expected actual))))
  (testing "day18a testcase e"
    (let [expected 81
          actual (day18a (parse "src/aoc2019/day18/e.txt"))]
      (is (= expected actual)))))
  ;;(testing "day18a"
    ;;(let [expected 81
          ;;actual (day18a (parse "src/aoc2019/day18/input.txt"))]
      ;;(is (= expected actual)))))