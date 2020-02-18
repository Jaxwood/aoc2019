(ns aoc2019.day05.core
  (:require [intcode.core :refer [run]]))

(defn day05a
  "find solution for day05a"
  [program]
  (let [state (run program)]
    (if (= 0 (:output state))
      (recur state)
      (:output state))))

(defn day05b
  "find solution for day05b"
  [program]
  (:output (run program)))
