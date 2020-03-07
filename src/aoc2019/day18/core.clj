(ns aoc2019.day18.core
  (:require [clojure.string :refer [split-lines lower-case]]))

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
      lower-case
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
          (if (wall? obstacle)
            (recur (rest lst) acc)
            (recur (rest lst) (conj acc next))))))))

(defn visited?
  "has the tile been visited?"
  [visited location]
  (nil? (get visited location)))

(defn add-door
  "add doors"
  [obstacle acc]
  (if (door? obstacle)
    (conj acc obstacle)
    acc))

(defn explore
  "explore the vault"
  [vault moves visited acc]
  ;;(Thread/sleep 2000)
  (if (empty? moves)
    acc
    (let [[current from doors] (first moves)
          distance (get visited from)
          obstacle (get vault current)
          moredoors (add-door obstacle doors)
          next (map (fn [pos] [pos current moredoors]) (filter (partial visited? visited) (neighbors vault current)))]
      (if (key? obstacle)
        (recur vault (into (rest moves) next) (conj visited [current (inc distance)]) (conj acc [obstacle (inc distance) moredoors]))
        (recur vault (into (rest moves) next) (conj visited [current (inc distance)]) acc)))))

(defn routes
  "map all the routes and the distances of the vault"
  [vault keyring]
  (loop [ks keyring acc {}]
    (if (empty? ks)
      acc
      (let [candidate (first ks)
            [current _] (first (filter (fn [[_ v]] (= v candidate)) vault))
            moves (map (fn [pos] [pos current []]) (neighbors vault current))]
        (recur (rest ks) (into acc {candidate (explore vault moves {current 0} [])}))))))

(defn access?
  ""
  [visited [k _ ds]]
  (if (contains? (into #{} visited) k)
    false
    (every? (fn [d] (contains? (into #{} visited) (unlocks d))) ds)))

(defn score
  "calculate score for a breadcrumb"
  [paths breadcrumbs acc]
  (if (= 1 (count breadcrumbs))
    acc
    (let [a (first breadcrumbs)
          b (second breadcrumbs)
          [_ v _] (first (filter (fn [[k v _]] (= k b)) (a paths)))]
      (recur paths (rest breadcrumbs) (+ acc v)))))


(defn travel
  ""
  [paths acc scores]
  (if (empty? acc)
    (apply min scores)
    (let [breadcrumbs (first acc) ;; [[:current]]
          next (last breadcrumbs) ;; :current
          connected (filter (partial access? breadcrumbs) (next paths))
          sorted (sort-by second connected)
          ways (map (fn [n] (conj breadcrumbs (first n))) sorted)]
      (if (empty? connected)
        (recur paths (rest acc) (conj scores (score paths breadcrumbs 0)))
        (recur paths (into (rest acc) ways) scores)))))

(defn day18a
  "solution for day18a"
  [vault]
  (let [keyring (conj (map second (filter (fn [[_ v]] (key? v)) vault)) :current)
        paths (routes vault keyring)]
    (travel paths [[:current]] [])))

    ;; (next paths) => [[:a 2 []] [:b 4 [:A]]]