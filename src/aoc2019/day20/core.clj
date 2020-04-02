(ns aoc2019.day20.core
  (:require [clojure.string :refer [split-lines]]
            [clojure.set :refer [intersection difference]]))

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

(defn left-right-portal?
  "check for a left to right portal"
  [maze x y]
  (and (portal? (get maze [(+ x 1) y])) (open? (get maze [(+ x 2) y]))))

(defn right-left-portal?
  "check for a right to left portal"
  [maze x y]
  (and (portal? (get maze [(+ x 1) y])) (open? (get maze [(- x 1) y]))))

(defn top-down-portal?
  "check for a top down portal"
  [maze x y]
  (and (portal? (get maze [x (- y 1)])) (open? (get maze [x (- y 2)]))))

(defn down-top-portal?
  "check for a down top portal"
  [maze x y]
  (and (portal? (get maze [x (- y 1)])) (open? (get maze [x (+ y 1)]))))

(defn teleport-locations
  "create a map of all teleport locations"
  [maze]
  (let [x-max (apply max (map (comp first first) maze))
        y-min (apply min (map (comp second first) maze))
        inc-x #(if (= % x-max) 0 (inc %))
        inc-y #(if (= %1 0) (dec %2) %2)]
    (loop [x 0 y 0 acc {}]
      (if (< y y-min)
        acc
        (let [new-x (inc-x x)
              new-y (inc-y new-x y)]
          (if (portal? (get maze [x y]))
            (cond
              (left-right-portal? maze x y)
              (recur new-x new-y
                     (update acc [(get maze [x y]) (get maze [(+ x 1) y])] (fn [old] (conj (or old []) [(+ x 2) y]))))
              (right-left-portal? maze x y)
              (recur new-x new-y
                     (update acc [(get maze [x y]) (get maze [(+ x 1) y])] (fn [old] (conj (or old []) [(- x 1) y]))))
              (top-down-portal? maze x y)
              (recur new-x new-y
                     (update acc [(get maze [x y]) (get maze [x (- y 1)])] (fn [old] (conj (or old []) [x (- y 2)]))))
              (down-top-portal? maze x y)
              (recur new-x new-y
                     (update acc [(get maze [x y]) (get maze [x (- y 1)])] (fn [old] (conj (or old []) [x (+ y 1)]))))
              :else
              (recur new-x new-y acc))
            (recur new-x new-y acc)))))))

(defn destination
  "find the destination after using a portal"
  [teleports coord]
  (let [result (filter (fn [[port destination]] (contains? (set destination) coord)) teleports)]
    (vec (difference (set ((comp second first) result)) #{coord}))))

(defn open-coords
  "get the neighbor squares that is open or a portal"
  [maze teleports coord]
  (let [ms (moves coord)
        open (filter #(open? (get maze %)) ms)]
    (map (fn [c] [c (get maze c)]) open)))

(def outer? (fn [[x y] [[xmin xmax] [ymin ymax]]] (or (= x xmin) (= y ymax) (= x xmax) (= y ymin))))

(defn find-bounds
  "finds the outer bounds"
  [teleports]
  (let [coords (mapcat second teleports)
        outer [[(apply min (map first coords))
                (apply max (map first coords))]
               [(apply min (map second coords))
                (apply max (map second coords))]]]
    outer))

(defn portal-coords
  "get the neighbor squares that is open or a portal"
  [maze teleports coord level bounds]
  (let [ms (moves coord)
        portals (destination teleports coord)]
    (if (and (= 0 level) (outer? coord bounds))
      []
      (map (fn [c] [c (get maze c)]) portals))))

(defn update-level
  "increment or decrement the level"
  [teleports current bounds coord]
  (if (outer? coord bounds)
    (dec current)
    (inc current)))

(defn shortest-path
  "traverse the maze"
  [maze teleports start end? bounds entrance]
  (loop [queue [start] visited entrance]
    (let [[coord type move level :as fst] (first queue)
          visitor (or (get visited level) #{})
          available (map #(into % [(inc move) level]) (open-coords maze teleports coord))
          ports (map #(into % [(inc move) (update-level teleports level bounds coord)]) (portal-coords maze teleports coord level bounds))
          non-visited (filter #(not (contains? (or (get visited (last %)) #{}) [(first %) (last %)])) (into available ports))]
      (if (end? coord level)
        move
        (recur (into (vec (rest queue)) (vec non-visited)) (assoc visited level (conj visitor [coord level])))))))

(defn day20a
  "find the shortest path through the maze"
  [maze]
  (let [teleports (teleport-locations maze)
        start (into (get teleports [:A :A]) [:open 0 0])
        finish (flatten (get teleports [:Z :Z]))
        bounds (find-bounds teleports)
        end? (fn [coord level] (= coord finish))]
    (shortest-path maze teleports start end? bounds {})))

(defn day20b
  "find the shortest path through the recursive maze"
  [maze]
  (let [teleports (teleport-locations maze)
        start (into (get teleports [:A :A]) [:open 0 0])
        finish (flatten (get teleports [:Z :Z]))
        bounds (find-bounds teleports)
        end? (fn [coord level] (and (= coord finish) (= 0 level)))]
    (shortest-path maze teleports start end? bounds {})))