(ns aoc2019.day18.core
  (:require [clojure.string :refer [lower-case split-lines]]
            [clojure.set :refer [subset?]]))

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
(def key? #(contains? all-keys %))
(def door? #(contains? all-doors %))
(def current? #(= :current %))
(def wall? #(= :wall %))
(def not-wall? (complement wall?))

(defn get-by-type
  "retrieve the coordinate for a keyword"
  [vault type]
  (first (first (filter (fn [[_ t]] (= type t)) vault))))

(defn neighbors
  "find all passable neighbor coordinates"
  [vault [x y] visited]
  (let [candidates (map (fn [[xx yy]] [(+ x xx) (+ y yy)]) [[0 1] [1 0] [-1 0] [0 -1]])]
    (filter (fn [coord] (and (not (contains? visited coord)) (not-wall? (get vault coord)))) candidates)))

(def move (fn [coord move-count doors] [coord (inc move-count) doors]))

(defn update-doors
  "update the doors found"
  [vault doors coord]
  (let [type (get vault coord)]
    (if (door? type)
      (set (conj doors (keyword (lower-case (name type)))))
      (set doors))))

(defn update-state
  "update the state of the vault"
  [vault acc move-count doors coord]
  (let [type (get vault coord)]
    (if (key? type)
      (update acc type (fn [old] [move-count (vec (into doors (or (second old) [])))]))
      acc)))

(defn explore
  "explore the vault for a certain key"
  [vault type]
  (let [start (get-by-type vault type)
        state (map #(move % 0 (update-doors vault [] %)) (neighbors vault start #{}))]
    (loop [queue state acc {} visited #{start}]
      (if (empty? queue)
        acc
        (let [[coord move-count doors] (first queue)
              candidates (neighbors vault coord visited)
              new-state (update-state vault acc move-count doors coord)
              moves (map #(move % move-count (update-doors vault doors %)) candidates)]
          (recur (concat (rest queue) moves) new-state (conj visited coord)))))))

(defn explore-all
  "explore the vault for all possible keys"
  [vault]
  (let [state {:current (explore vault :current)}
        kks (map second (filter #(key? (second %)) vault))]
    (loop [ks kks acc state]
      (if (empty? ks)
        acc
        (recur (rest ks) (update acc (first ks) (fn [old] (explore vault (first ks)))))))))

(defn visited?
  "has this been visited previously?"
  [previous candidate]
  (or (some #(= candidate %) previous) false))

(defn travel
  "calculate the next paths to travel"
  [steps candidates breadcrumbs]
  (loop [targets candidates result []]
    (if (empty? targets)
      result
      (let [[k [num doors]] (first targets)]
        (if (contains? breadcrumbs k)
          (recur (rest targets) result)
          (if (subset? (set doors) breadcrumbs)
            (recur (rest targets) (conj result [(+ steps num) k (conj breadcrumbs k)]))
            (recur (rest targets) result)))))))

(defn shortest-path
  "find shortest path by visiting the lowest distance found so far"
  [vault]
  (let [all (explore-all vault)
        max-keys (count (filter #(key? (second %)) vault))
        queue [[0, :current, #{}]]]
    (loop [qs queue seen {}]
      (let [[steps k breadcrumb] (first qs)]
        (if (visited? (k seen) breadcrumb)
          (recur (rest qs) seen)
          (if (= (count breadcrumb) max-keys)
            steps
            (let [next (travel steps (k all) breadcrumb)
                  new-seen (update seen k (fn [old] (conj (or old []) breadcrumb)))]
              (recur (sort-by first (concat (rest qs) next)) new-seen))))))))

(defn day18a
  "find the shortest path while visiting all keys"
  [vault]
  (shortest-path vault))