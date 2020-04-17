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
  [boards level [x y]]
  (let [candidates (into #{} (map (fn [[xx yy]] [(+ x xx) (+ y yy)]) [[-1 0] [0 -1] [1 0] [0 1]]))]
    (count (filter (fn [[xx yy s]] (and (= s :bug) (contains? candidates [xx yy]))) (get boards level)))))

(defn tick
  "update the state of a square"
  [boards level strategy [x y s]]
  (let [bugs (strategy boards level [x y])]
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

(defn empty-board
  "create a new empty board"
  []
  (loop [x 0 y 0 acc []]
    (if (= y 5)
      acc
      (if (= x 5)
        (recur 0 (inc y) acc)
        (recur (inc x) y (conj acc [x y :space]))))))

(def innermaze (recursive 65 #(keyword (str (char %)))))
(def outermaze (recursive 1 #(keyword (str %))))
(def outer  (into #{} (map innermaze [:A :B :C :D :E :F :J :K :O :P :T :U :V :W :X :Y])))
(def center (into #{} (map innermaze [:M])))
(def inner  (into #{} (map innermaze [:L :H :N :R])))

(def lookup
  {(:A innermaze) (map outermaze [:8 :12])
   (:B innermaze) (map outermaze [:8])
   (:C innermaze) (map outermaze [:8])
   (:D innermaze) (map outermaze [:8])
   (:E innermaze) (map outermaze [:8 :14])
   (:F innermaze) (map outermaze [:12])
   (:H innermaze) (map outermaze [:1 :2 :3 :4 :5])
   (:J innermaze) (map outermaze [:14])
   (:K innermaze) (map outermaze [:12])
   (:L innermaze) (map outermaze [:1 :6 :11 :16 :21])
   (:M innermaze) (map innermaze [:M])
   (:N innermaze) (map outermaze [:5 :10 :15 :20 :25])
   (:O innermaze) (map outermaze [:14])
   (:P innermaze) (map outermaze [:12])
   (:R innermaze) (map outermaze [:21 :22 :23 :24 :25])
   (:T innermaze) (map outermaze [:14])
   (:U innermaze) (map outermaze [:12 :18])
   (:V innermaze) (map outermaze [:18])
   (:W innermaze) (map outermaze [:18])
   (:X innermaze) (map outermaze [:18])
   (:Y innermaze) (map outermaze [:14 :18])})

(defn recursive-neighbors
  "find the neighbors taking into account the recursiveness"
  [boards level [x y]]
  (let [up (or (get boards (inc level)) [])
        down (or (get boards (dec level)) [])
        bugs (neighbors boards level [x y])]
    (cond
      (contains? center [x y]) 0
      (contains? outer [x y])
      (let [below (into #{} (lookup [x y]))]
        (+ bugs (count (filter (fn [[x y s]] (and (= s :bug) (contains? below [x y]))) down))))
      (contains? inner [x y])
      (let [above (into #{} (lookup [x y]))]
        (+ bugs (count (filter (fn [[x y s]] (and (= s :bug) (contains? above [x y]))) up))))
      :else bugs)))

(defn evole
  "generate the next generation of the board"
  [boards]
  (loop [levels (keys boards) acc {}]
    (if (empty? levels)
      acc
      (let [level (first levels)
            next (map (partial tick boards level recursive-neighbors) (get boards level))]
        (recur (rest levels) (assoc acc level next))))))

(defn day24a
  "find the biodiversity rating for the first duplicate board"
  [board]
  (let [level 0
        boards {level board}]
    (loop [bs boards acc #{}]
      (let [rating (biodiversity-rating (get bs level))]
        (if (contains? acc rating)
          rating
          (let [next (map (partial tick bs level neighbors) (get bs level))]
            (recur (assoc bs level next) (conj acc rating))))))))

(defn day24b
  "find the number of bugs after x minutes"
  [board minutes]
  (loop [boards {0 board} minute minutes]
    (if (= 0 minute)
      (count (filter (fn [[x y s]] (= s :bug)) (apply concat (vals boards))))
      (let [base (dec (apply min (keys boards)))
            super (inc (apply max (keys boards)))
            bs (assoc (assoc boards base (empty-board)) super (empty-board))]
        (recur (evole bs) (dec minute))))))