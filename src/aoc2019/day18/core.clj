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

(defn unvisited?
  "filter nodes that has yet to be visited"
  [candidate]
  (let [adjecent (neighbors (:vault candidate) (:current candidate))
        visited (:visited candidate)]
    (filter #(nil? (get visited %)) adjecent)))

(defn explored?
  "is the vault fully explored?"
  [vault]
  (let [remaining (filter (fn [[_ type]] (key? type)) vault)]
    (= (count remaining) 0)))

(defn traverse
  "traverse the vault"
  [tovisit acc]
  (if (empty? tovisit)
    (apply min (map second acc))
    (let [{:keys [vault current from visited], :as candidate} (first tovisit)
          move (get visited from)
          tile (get vault current)]
      (if (key? tile)
        (let [[door _] (first (filter (fn [[_ type]] (= type (unlocks tile))) vault))
              updated-vault (assoc (assoc vault current :open) door :open)
              adjecent (unvisited? (assoc (assoc candidate :visited {current (inc move)}) :vault updated-vault))
              next-moves (map (fn [pos] {:vault updated-vault :from current :current pos :visited {current (inc move)}}) adjecent)]
          (if (explored? updated-vault)
            (recur (into (rest tovisit) next-moves) (conj acc [current (inc move)]))
            (recur (into (rest tovisit) next-moves) acc)))
        (let [adjecent (unvisited? candidate)
              next-moves (map (fn [pos] {:vault (:vault candidate) :from (:current candidate) :current pos :visited {(:current candidate) (inc move)}}) adjecent)]
          (recur (into (rest tovisit) next-moves) acc))))))

(defn day18a
  "solution for day18a"
  [vault]
  (let [[current _] (first (filter (comp current? second) vault))
        tovisit (map (fn [pos] {:vault vault :current pos :from current :visited {current 0}}) (neighbors vault current))]
    (traverse tovisit [])))
