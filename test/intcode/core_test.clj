(ns intcode.core-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer :all]))

(deftest intcode
  (testing "parse"
    (def expected [1 9 10 3 2 3 11 0 99 30 40 50])
    (def actual (parse "test/intcode/parse.txt"))
    (is (= expected actual)))
  (testing "add"
    (def expected [1 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (add [1 9 10 3 2 3 11 0 99 30 40 50] 0))
    (is (= expected actual)))
  (testing "multiply"
    (def expected [3500 9 10 70 2 3 11 0 99 30 40 50])
    (def actual (multiply [1 9 10 70 2 3 11 0 99 30 40 50] 4))
    (is (= expected actual)))
  (testing "run"
    (def expected 3500)
    (def actual (run [1 9 10 3 2 3 11 0 99 30 40 50] 0))
    (is (= expected (first actual)))))