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

(defn recursive
  "generator a board using a strategy for naming"
  [num strategy]
  (loop [x 0 y 0 c num acc []]
    (if (= y 5)
      (into {} acc)
      (if (= x 5)
        (recur 0 (inc y) c acc)
        (recur (inc x) y (inc c) (conj acc [(strategy c) [x y]]))))))

(def outer (into #{} (mapcat (fn [x] [[x 0] [x 4] [0 x] [4 x]]) (range 5))))
(def center (into #{} [2 2]))
(def inner (into #{} [[1 2] [2 3] [2 1] [3 2]]))

(def innermaze (recursive 65 #(keyword (str (char %)))))
(def outermaze (recursive 1 #(keyword (str %))))
(def lookup
  {(:A innermaze) [(:8 outermaze) (:12 outermaze)]
   (:B innermaze) [(:8 outermaze)]
   (:C innermaze) [(:8 outermaze)]
   (:D innermaze) [(:8 outermaze)]
   (:E innermaze) [(:8 outermaze) (:14 outermaze)]
   (:F innermaze) [(:12 outermaze)]
   (:H innermaze) [(:1 outermaze) (:2 outermaze) (:3 outermaze) (:4 outermaze) (:5 outermaze)]
   (:J innermaze) [(:14 outermaze)]
   (:K innermaze) [(:12 outermaze)]
   (:L innermaze) [(:1 outermaze) (:6 outermaze) (:11 outermaze) (:16 outermaze) (:21 outermaze)]
   (:N innermaze) [(:5 outermaze) (:10 outermaze) (:15 outermaze) (:20 outermaze) (:25 outermaze)]
   (:O innermaze) [(:14 outermaze)]
   (:P innermaze) [(:12 outermaze)]
   (:R innermaze) [(:21 outermaze) (:22 outermaze) (:23 outermaze) (:24 outermaze) (:25 outermaze)]
   (:T innermaze) [(:14 outermaze)]
   (:U innermaze) [(:12 outermaze) (:18 outermaze)]
   (:V innermaze) [(:18 outermaze)]
   (:W innermaze) [(:18 outermaze)]
   (:X innermaze) [(:18 outermaze)]
   (:Y innermaze) [(:14 outermaze) (:18 outermaze)]})

(defn recursive-neighbors
  "find the neighbors taking into account the recursiveness"
  [boards level [x y]]
  (let [board (get boards level)
        up (or (get boards (inc level)) [])
        down (or (get boards (dec level)) [])
        bugs (neighbors board [x y])]
    (cond
      (contains? center [x y]) 0
      (contains? outer [x y])
        (let [below (lookup [x y])]
          (+ bugs (count (filter (fn [[x y s]] (and (= s :bug) (contains? below [x y]))) below))))
      (contains? inner [x y])
        (let [upper (lookup [x y])]
          (+ bugs (count (filter (fn [[x y s]] (and (= s :bug) (contains? upper [x y]))) up))))
      :else bugs)))

(defn day24a
  "find the biodiversity rating for the first duplicate board"
  [board]
  (loop [b board acc #{}]
    (let [rating (biodiversity-rating b)]
      (if (contains? acc rating)
        rating
        (let [next (map (partial tick b) b)]
          (recur next (conj acc rating)))))))

(defn day24b
  "find the number of bugs after x minutes"
  [board minutes]
  (let [boards {0 board}]
    0))