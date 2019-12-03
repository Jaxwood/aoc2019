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

(defn generateX
  "Create infinite sequence of coordinates using supplied strategy on the X coordinate"
  [[x y] strategy]
  (map #(into [] [% y]) (iterate strategy (strategy x))))

(defn generateY
  "Create infinite sequence of coordinates using supplied strategy on the Y coordinate"
  [[x y] strategy]
  (map #(into [] [x %]) (iterate strategy (strategy y))))

(defn path
  "Generate path for coordinate"
  [coord [direction count]]
  (match [direction count]
      ["R" cnt] (take cnt (generateX coord inc))
      ["L" cnt] (take cnt (generateX coord dec))
      ["U" cnt] (take cnt (generateY coord inc))
      ["D" cnt] (take cnt (generateY coord dec))
      :else (throw (Exception. "Unsupported direction"))))

(defn day03a
  ""
  [input]
  (map parse input))