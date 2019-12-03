(ns aoc2019.day03.core)
(require '[clojure.core.match :refer [match]])

(defn parse
  "Split input on comma"
  [input]
  (clojure.string/split input #","))

(defn toTuples
  "Convert raw coordinate string into tuples"
  [input]
  ((juxt #(subs %1 0 1) #(Integer/parseInt (subs %1 1))) input))

(defn day03a
  ""
  [input]
  0)