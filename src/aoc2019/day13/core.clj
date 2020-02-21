(ns aoc2019.day13.core
  (:require [intcode.core :refer [run]]))

(defn arcade
  "run all instructions and saves the output"
  [program instructions]
  (if (= :stopped (:status program))
    instructions
    (recur (run program) (conj instructions (:output program)))))

(defn day13a
  "find blocks"
  [memory]
  (count
   (filter #(= 2 %1)
           (map last
                (partition 3 (arcade (run {:memory memory :address 0 :input [] :relative 0}) []))))))