(ns aoc2019.day15.core
  (:require [intcode.core :refer [run]]))

(def north 1)
(def south 2)
(def west 3)
(def east 4)

(def wall 0)
(def move 1)
(def oxygen 2)

(defn travel
  "calculate the position for each direction"
  [direction [x y]]
  (cond
    (= direction north) [north [x (inc y)]]
    (= direction south) [south [x (dec y)]]
    (= direction west) [west [(dec x) y]]
    (= direction east) [east [(inc x) y]]))

(defn open?
  "is it a floor tile?"
  [maze [_  pos]]
  (let [candidate (or (get maze pos) :floor)]
    (cond
      (= candidate :floor) true
      (= candidate :wall) false)))

(defn visited?
  "get the amount of times this tile has been visited"
  [visited [_ pos]]
  (or (get visited pos) 0))

(defn navigate
  "find the best move"
  [pos maze visited]
  (let [n (travel north pos)
        s (travel south pos)
        w (travel west pos)
        e (travel east pos)
        candidates (sort-by (partial visited? visited) (filter (partial open? maze) [s w e n]))]
    (first candidates)))

(defn shortest-path
  "finds the shortest path"
  [until maze visit visited]
  (let [[x y weight] (first visit)
        pos [x y]]
    (if (= pos until)
      weight
      (let [positions (map second [(travel north pos) (travel south pos) (travel west pos) (travel east pos)])
            candidates (filter (fn [pos] (and (not (contains? visited pos)) (= :floor (get maze pos)))) positions)
            moves (into (rest visit) (map (fn [[x y]] [x y (inc weight)]) candidates))]
        (recur until maze moves (into visited [pos]))))))

(defn traverse
  "traverse the ship"
  [program pos maze visited]
  (let [[direction [x y]] (navigate pos maze visited)
        state (run (assoc program :input [direction]))
        status (:output state)]
    (cond
      (= status wall) (recur state pos (assoc maze [x y] :wall) (update visited [x y] (fn [old] (inc (or old 0)))))
      (= status move) (recur state [x y] (assoc maze [x y] :floor) (update visited [x y] (fn [old] (inc (or old 0)))))
      (= status oxygen) (shortest-path [0 0] maze [[x y 0]] #{}))))

(defn open-vent
  "spread the oxygen into the maze"
  [maze visited visit acc]
  (let [neighbors (mapcat (fn [pos] [(travel north pos) (travel south pos) (travel east pos) (travel west pos)]) visit)
        open (filter (fn [pos] (and (open? maze pos) (not (contains? visited (second pos))))) neighbors)]
    (if (empty? open)
      acc
      (recur maze (into visited visit) (map second open) (inc acc)))))

(defn explore
  "explore the whole maze"
  [iteration program pos maze visited]
  (if (= iteration 2500)
    (let [[target _] (first (filter (fn [[k v]] (= v :target)) maze))]
      (open-vent maze #{} [target] 0))
    (let [[direction [x y]] (navigate pos maze visited)
          state (run (assoc program :input [direction]))
          status (:output state)]
      (cond
        (= status wall) (recur (inc iteration) state pos (assoc maze [x y] :wall) (update visited [x y] (fn [old] (inc (or old 0)))))
        (= status move) (recur (inc iteration) state [x y] (assoc maze [x y] :floor) (update visited [x y] (fn [old] (inc (or old 0)))))
        (= status oxygen) (recur (inc iteration) state [x y] (assoc maze [x y] :target) (update visited [x y] (fn [old] (inc (or old 0)))))))))

(defn day15a
  "find the moves required to reach the oxygen module"
  [memory]
  (traverse {:memory memory :address 0 :input [] :relative 0} [0 0] {[0 0] :floor} {[0 0] 1}))

(defn day15b
  "find the number of turns before the maze has oxygen"
  [memory]
  (explore 1 {:memory memory :address 0 :input [] :relative 0} [0 0] {[0 0] :floor} {[0 0] 1}))