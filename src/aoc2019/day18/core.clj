(ns aoc2019.day18.core
  (:require [clojure.string :refer [lower-case split-lines]]
            [clojure.set :refer [subset? difference]]))

(defn tile
  "parse the tile"
  [candidate]
  (cond
    (= candidate "#") :wall
    (= candidate ".") :open
    (= candidate "@") :current
    :else (keyword candidate)))

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

(def all-keys (into #{} (map (comp keyword str char) (range 97 123))))
(def all-doors (into #{} (map (comp keyword str char) (range 65 91))))
(defn left [[x y]] [(dec x) y])
(defn right [[x y]] [(inc x) y])
(defn up [[x y]] [x (inc y)])
(defn down [[x y]] [x (dec y)])
(defn key? [candidate] (contains? all-keys candidate))
(defn door? [candidate] (contains? all-doors candidate))
(defn wall? [vault pos] (= :wall (get vault pos)))
(def not-wall? (complement wall?))

(defn neighbors
  "find passable neighbor tiles"
  [vault pos]
  (let [movements (juxt left right up down)]
    (filter (partial not-wall? vault) (movements pos))))

(defn with-moves
  "add the movement count to the coordiate"
  [m doors coords]
  (map (fn [xs] (conj (conj xs m) doors)) coords))

(defn with-doors
  "add the doors"
  [type acc]
  (if (door? type)
    (conj acc (keyword (lower-case (name type))))
    acc))

(defn explore
  "find the keys"
  [vault from]
  (let [tiles (with-moves 1 [] (neighbors vault from))]
    (loop [moves tiles visited #{from} acc {}]
      (if (empty? moves)
        acc
        (let [[x y m doors] (first moves)
              type (get vault [x y])
              next (difference (set (neighbors vault [x y])) visited)
              next-moves (with-moves (inc m) (with-doors type doors) next)]
          (if (key? type)
            (recur (into (rest moves) next-moves) (conj visited [x y]) (into acc {type {:cost m :dependsOn doors}}))
            (recur (into (rest moves) next-moves) (conj visited [x y]) acc)))))))

(defn accessible?
  "find keys that is accessible"
  [foundkeys [k v]]
  (let [dependsOn (set (:dependsOn v))]
    (or (empty? dependsOn) (subset? dependsOn foundkeys))))

(defn get-key
  "get the key from the vault"
  [vault k]
  (first (filter (fn [entry] (= k (val entry))) vault)))