(ns aoc2019.day24.core
  (:require [clojure.string :refer [starts-with? split-lines]]))

(defn parse-line
  "parse the line into keywords (:bug|:space)"
  [line]
  (loop [l line acc []]
    (if (empty? l)
      acc
      (let [s (str (first l))]
        (cond
          (= s "#") (recur (rest l) (conj acc :bug))
          (= s ".") (recur (rest l) (conj acc :space)))))))

(defn parse
  "parse the input"
  [filename]
  (let [lines (split-lines (slurp filename))]
    (loop [line lines acc []]
      (if (empty? line)
        acc
        (recur (rest line) (conj acc (parse-line (first line))))))))

(defn pow
  "calculate the diversity rating for a square"
  [[i n]]
  (cond
    (= n :space) 0
    (= n :bug) (int (Math/pow 2 i))))

(defn biodiversity-rating
  "calculate the biodiveristy rating for a board"
  [board]
  (let [board-indexed (map-indexed (fn [idx n] [idx n]) (flatten board))]
    (reduce (fn [acc n] (+ acc (pow n))) 0 board-indexed)))

(defn day24a
  "find the biodiversity rating for the first duplicate board"
  [raw]
  raw)