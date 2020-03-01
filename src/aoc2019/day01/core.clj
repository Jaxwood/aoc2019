(ns aoc2019.day01.core
  (:require [clojure.string :refer [split-lines]]))

(defn calculateFuel
  "Calculate fuel required to launch module"
  [mass]
  (int (- (Math/floor (/ mass 3)) 2)))

(defn calculateAdditionalFuel
  "Calculate additional fuel required to launch module"
  [mass sum]
  (let [fuel (calculateFuel mass)]
    (if (<= fuel 0)
      sum
      (calculateAdditionalFuel fuel (+ sum fuel)))))

(defn day01a
  "Day01a"
  [filename]
  (let [raw (slurp filename)
        masses (map #(Integer/parseInt %1) (split-lines raw))]
    (reduce + 0 (map calculateFuel masses))))

(defn day01b
  "Day01b"
  [filename]
  (let [raw (slurp filename)
        masses (map #(Integer/parseInt %1) (split-lines raw))]
    (reduce + 0 (map #(calculateAdditionalFuel %1 0) masses))))