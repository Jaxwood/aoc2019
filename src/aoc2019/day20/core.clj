(ns aoc2019.day20.core
  (:require [clojure.string :refer [split-lines]]
            [clojure.set :refer [intersection]]))

(defn tile
  "parse the tile"
  [character]
  (cond
    (= character "#") :wall
    (= character ".") :open
    (= character " ") :space
    :else (keyword character)))

(defn parse
  "parse the maze"
  [filename]
  (let [raw (split-lines (slurp filename))]
    (loop [lines raw y 0 acc {}]
      (if (empty? lines)
        acc
        (let [line (first lines)
              result (map-indexed (fn [x character] [[x y] (tile (str character))]) line)]
          (recur (rest lines) (dec y) (into acc result)))))))

(def portals (into #{} (map (comp keyword str char) (range 65 91))))
(def open? #(= % :open))
(def portal? #(contains? portals %))

(defn moves
  "generate adjecent moves for a coordinate"
  [[x y]]
  (map (fn [[xx yy]] [(+ x xx) (+ y yy)]) [[1 0] [0 1] [-1 0] [0 -1]]))

(defn neighbors
  "get the neighbor squares that is open or a portal"
  [maze coord]
  (map (fn [c] [c (get maze c)])
       (filter #(or (portal? (get maze %)) (open? (get maze %)))
               (moves coord))))

(defn portal
  "find the full portal"
  [maze [[x y] _] [[xx yy] _]]
  (cond
    (> y yy) [[xx (dec yy)] (get maze [xx (dec yy)])]
    (< y yy) [[xx (inc yy)] (get maze [xx (inc yy)])]
    (> x xx) [[(dec xx) yy] (get maze [(dec xx) yy])]
    (< x xx) [[(inc xx) yy] (get maze [(inc xx) yy])]))

(defn teleport
  "find the location to teleport to"
  [maze [coord1 name1] [coord2 name2]]
  (let [pairs (filter #(or (= (second %) name1) (= (second %) name2)) maze)
        new-location (map first (filter #(not (or (= (first %) coord1) (= (first %) coord2))) pairs))
        candidates (intersection (set new-location) (set (mapcat moves new-location)))]
    (map (fn [coord] [coord (get maze coord)]) candidates)))

(defn end?
  "is the end reached"
  [maze candidates]
  (let [zs (filter #(= :Z (second %)) candidates)]
    (if (empty? zs)
      false
      (or (some #(= :Z (get maze %)) (moves (first (first zs)))) false))))

(defn walkthrough-portal
  "handle portal fields"
  [maze previous fields]
  (loop [fs fields acc []]
    (if (empty? fs)
      acc
      (let [[coord type :as fst] (first fs)]
        (if (portal? type)
          (let [port (teleport maze fst (portal maze previous fst))
                next (filter #(open? (second %)) (mapcat #(neighbors maze (first %)) port))]
            (recur (rest fs) (into acc next)))
          (recur (rest fs) (conj acc fst)))))))

(defn shortest-path
  "traverse the maze"
  [maze start entrance]
  (loop [queue [start] visited entrance]
    (let [[coord type move :as fst] (first queue)
          candidates (neighbors maze coord)
          next (filter #(not (contains? visited (first %))) (walkthrough-portal maze [coord type] candidates))]
      (if (end? maze candidates)
        move
        (recur (concat (rest queue) (map #(conj % (inc move)) next)) (conj visited coord))))))

(defn day20a
  "find the shortest path through the maze"
  [maze]
  (let [as (map first (filter #(= :A (second %)) maze))
        pair (intersection (set as) (set (mapcat moves as)))
        start (filter #(open? (second %)) (mapcat #(neighbors maze %) pair))]
    (shortest-path maze (conj (first start) 0) pair)))