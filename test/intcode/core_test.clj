(ns intcode.core-test
  (:require [clojure.test :refer :all]
            [intcode.core :refer :all]))

(deftest intcode
  (testing ""
    (def expected 0)
    (def actual (run []))
    (is (= expected actual))))