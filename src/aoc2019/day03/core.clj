(ns aoc2019.day03.core)
(require '[clojure.core.match :refer [match]])

(defn parse
  "Split input on comma"
  [input]
  (clojure.string/split input #","))

(defn toTuples
  "Convert raw coordinate string into tuples"
  [input]
  (let [f (subs input 0 1)
        r (Integer/parseInt (subs input 1))]
    [f r]))

(defn day03a
  ""
  [input]
  0)