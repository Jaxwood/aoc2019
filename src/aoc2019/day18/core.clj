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
  (let [type (get vault coord)
        type-to-lower (keyword (lower-case (name type)))]
    (if (or (door? type) (key? type))
      (set (conj doors type-to-lower))
      (set doors))))

(defn update-state
  "update the state of the vault"
  [vault acc move-count doors coord]
  (let [type (get vault coord)]
    (if (key? type)
      (update acc type (fn [old] [move-count (vec (difference (set (into doors (or (second old) []))) #{type}))]))
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
  [vault state]
  (let [kks (map second (filter #(key? (second %)) vault))]
    (loop [ks kks acc state]
      (if (empty? ks)
        acc
        (recur (rest ks) (update acc (first ks) (fn [old] (explore vault (first ks)))))))))

(defn visited?
  "has this been visited previously?"
  [previous candidate]
  (or (some #(= candidate %) previous) false))

(defn transform
  "give unqiue identifier for each start location"
  [vault name]
  (update vault (get-by-type vault :current) (constantly name)))

(defn check-other-quadrants
  "expand possible moves to other quadrants"
  [all seen ks]
  (let [result (reduce #(conj %1 (%2 all)) {} ks)]
    result))

(defn find-previous
  "update the map for the new key"
  [all k ks]
  (vec (filter some? (map-indexed (fn [i c] (if (nil? (k (c all))) (get ks i) k)) [:q1 :q2 :q3 :q4]))))

(defn travel
  "calculate the next paths to travel"
  [all steps candidates ks breadcrumbs]
  (loop [targets candidates result []]
    (if (empty? targets)
      result
      (let [[k [num doors]] (first targets)]
        (if (contains? breadcrumbs k)
          (recur (rest targets) result)
          (if (subset? (set doors) breadcrumbs)
            (recur (rest targets) (conj result [(+ steps num) (find-previous all k ks) (conj breadcrumbs k)]))
            (recur (rest targets) result)))))))

(defn shortest-path
  "find shortest path by visiting the lowest distance found so far"
  [all until? queue history]
  (loop [qs queue seen history]
    (let [[steps ks breadcrumbs] (first qs)]
      (if (visited? (get seen (set ks)) breadcrumbs)
        (recur (rest qs) seen)
        (if (until? breadcrumbs)
          steps
          (let [next (travel all steps (check-other-quadrants all breadcrumbs ks) ks breadcrumbs)
                new-seen (update seen (set ks) (fn [old] (conj (or old []) breadcrumbs)))]
            (recur (sort-by first (concat (rest qs) next)) new-seen)))))))

(defn day18a
  "find the shortest path while visiting all keys"
  [vault]
  (let [transformed-vault (reduce #(transform %1 %2) vault [:q1])
        state (reduce #(conj %1 {%2 (explore transformed-vault %2)}) {} [:q1])
        all (explore-all transformed-vault state)
        until (count (filter #(key? (second %)) vault))
        queue [[0 [:q1] #{}]]]
    (shortest-path all #(= (count %) until) queue {})))

(defn day18b
  "find the shortest path"
  [vault]
  (let [transformed-vault (reduce #(transform %1 %2) vault [:q1 :q2 :q3 :q4])
        state (reduce #(conj %1 {%2 (explore transformed-vault %2)}) {} [:q1 :q2 :q3 :q4])
        all (explore-all transformed-vault state)
        until (count (filter #(key? (second %)) vault))
        queue [[0, [:q1 :q2 :q3 :q4], #{}]]]
    (shortest-path all #(= (count %) until) queue {})))