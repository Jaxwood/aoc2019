(ns intcode.core-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer :all]))

(deftest intcode
  (testing "parse"
    (def expected [1 9 10 3 2 3 11 0 99 30 40 50])
    (def actual (parse "test/intcode/parse.txt"))
    (is (= expected actual))))