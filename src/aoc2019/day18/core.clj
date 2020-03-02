(ns aoc2019.day18.core
  (:require [clojure.string :refer [split-lines]]))

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

(defn day18a
  "solution for day18a"
  [vault]
  (let [all-keys (filter (fn [[_ type]] (key? type)) vault)
        [pos _] (first (filter (fn [[_ type]] (current? type)) vault))]
    (neighbors vault pos)))