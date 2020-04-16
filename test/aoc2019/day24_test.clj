(ns aoc2019.day24-test
  (:require [clojure.test :refer :all]
            [aoc2019.day24.core :refer :all]))

(deftest day24
  (testing "find the diversity rating for a board"
    (let [expected 2129920
          actual (biodiversity-rating [
            [:space :space :space :space :space]
            [:space :space :space :space :space]
            [:space :space :space :space :space]
            [:bug :space :space :space :space]
            [:space :bug :space :space :space]])]
      (is (= expected actual)))))