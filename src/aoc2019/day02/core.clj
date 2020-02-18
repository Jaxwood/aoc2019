(ns aoc2019.day02.core
  (:require [intcode.core :refer [run]]))
(defn inc-verb
  "Increment verb"
  [verb]
  (if (= verb 99)
    0
    (inc verb)))

(defn inc-noun
  "Increment noun"
  [noun verb]
  (if (= verb 99)
    (inc noun)
    noun))

(defn day02a
  "Calculate int codes for puzzle input"
  [program]
  (first (:memory (run program))))

(defn day02b
  "Calculate int codes for puzzle input"
  [program noun verb target]
  (let [memory (assoc (assoc (:memory program) 1 noun) 2 verb)
        result (day02a (run (assoc program :memory memory)))]
    (if (= result target)
      (+ (* 100 noun) verb)
      (recur program (inc-noun noun verb) (inc-verb verb) target))))