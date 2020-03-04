(ns aoc2019.day18.core
  (:require [clojure.string :refer [split-lines upper-case]]))

(defn tile
  "find the tile"
  [obstacle]
  (cond
    (= obstacle "#") :wall
    (= obstacle ".") :open
    (= obstacle "@") :current
    :else (keyword obstacle)))

(defn parse
  "parse the vault"
  [filename]
  (let [lines (split-lines (slurp filename))]
    (loop [line lines acc {} y 0]
      (if (empty? line)
        acc
        (let [coords (map-indexed (fn [idx itm] [[idx y] (tile (str itm))]) (first line))]
          (recur (rest line) (into acc coords) (dec y)))))))

(def a 97)
(def z 123)
(def A 65)
(def Z 91)

(def all-keys (into #{} (map (comp keyword str char) (range a z))))
(def all-doors (into #{} (map (comp keyword str char) (range A Z))))

(def wall? #(= :wall %))
(def current? #(= :current %))
(def door? #(contains? all-doors %))
(def key? #(contains? all-keys %))

(defn unlocks
  "find the door the key unlocks"
  [k]
  (-> k
      name
      upper-case
      keyword))

(defn neighbors
  "find the neighbors squares that is passable"
  [vault [x y]]
  (let [candidates [[x (dec y)] [x (inc y)] [(inc x) y] [(dec x) y]]]
    (loop [lst candidates acc []]
      (if (empty? lst)
        acc
        (let [next (first lst)
              obstacle (get vault next)]
          (if (or (wall? obstacle) (door? obstacle))
            (recur (rest lst) acc)
            (recur (rest lst) (conj acc next))))))))

(def unvisited? (fn [visited pos] (nil? (get visited pos))))

(defn traverse
  "traverse the vault"
  [vault cur visit visited]
  (if (empty? (filter (comp key? second) vault))
    (apply max (vals visited))
    (let [moves (get visited cur)
          pos (first visit)
          type (get vault pos)
          adjecent (filter (partial unvisited? visited) (neighbors vault pos))]
      (if (key? type)
        (let [door (first (filter (fn [[_ t]] (= t (unlocks type))) vault))
              open-vault (assoc (assoc vault (first door) :open) pos :open)]
          (recur open-vault pos (neighbors open-vault pos) {pos (inc moves)}))
        (recur vault pos (into (rest visit) adjecent) (conj visited [pos (inc moves)]))))))

(defn day18a
  "solution for day18a"
  [vault]
  (let [[pos _] (first (filter (comp current? second) vault))]
    (traverse vault pos (neighbors vault pos) {pos 0})))
