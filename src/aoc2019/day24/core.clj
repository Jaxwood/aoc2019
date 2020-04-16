(ns aoc2019.day24.core
  (:require [clojure.string :refer [starts-with? split-lines]]))

(defn parse-line
  "parse the line into keywords (:bug|:space)"
  [y line]
  (loop [l line x 0 acc []]
    (if (empty? l)
      acc
      (let [s (str (first l))]
        (cond
          (= s "#") (recur (rest l) (inc x) (conj acc [x y :bug]))
          (= s ".") (recur (rest l) (inc x) (conj acc [x y :space])))))))

(defn parse
  "parse the input"
  [filename]
  (let [lines (split-lines (slurp filename))]
    (loop [line lines y 0 acc []]
      (if (empty? line)
        acc
        (recur (rest line) (inc y) (into acc (parse-line y (first line))))))))

(defn pow
  "calculate the diversity rating for a square"
  [[i [_ _ n]]]
  (cond
    (= n :space) 0
    (= n :bug) (int (Math/pow 2 i))))

(defn biodiversity-rating
  "calculate the biodiveristy rating for a board"
  [board]
  (let [board-indexed (map-indexed (fn [idx n] [idx n]) board)]
    (reduce (fn [acc n] (+ acc (pow n))) 0 board-indexed)))

(defn neighbors
  "find the squares with bugs"
  [board [x y]]
  (let [candidates (into #{} (map (fn [[xx yy]] [(+ x xx) (+ y yy)]) [[-1 0] [0 -1] [1 0] [0 1]]))]
    (count (filter (fn [[xx yy s]] (and (= s :bug) (contains? candidates [xx yy]))) board))))

(defn tick
  "update the state of a square"
  [board [x y s]]
  (let [bugs (neighbors board [x y])]
    (cond
      (= s :space)
      (if (or (= bugs 1) (= bugs 2))
        [x y :bug]
        [x y s])
      (= s :bug)
      (if (= bugs 1)
        [x y s]
        [x y :space]))))

(defn day24a
  "find the biodiversity rating for the first duplicate board"
  [board]
  (loop [b board acc #{}]
    (let [rating (biodiversity-rating b)]
      (if (contains? acc rating)
        rating
        (let [next (map (partial tick b) b)]
          (recur next (conj acc rating)))))))