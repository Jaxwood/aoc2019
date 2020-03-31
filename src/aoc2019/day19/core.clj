(ns aoc2019.day19.core
  (:require [intcode.core :refer [run]]))

(defn drone-status
  "get the drone status"
  [memory coord]
  (:output (run {:memory memory :address 0 :relative 0 :input coord})))

(def pulled? #(= 1 %))

(def stationary? #(= 0 %))

(defn pulled-objects
  "get the pulled objects on a line"
  [status y]
  (take-while (fn [x] (pulled? (status [x y]))) (drop-while (fn [x] (stationary? (status [x y]))) (drop y (range)))))

(defn fits?
  "check if the beam can hold a 100x100 ship"
  [status y xs]
  (every? (fn [x] (pulled? (status [x (+ 99 y)]))) xs))

(defn result
  "calculate the result"
  [x y]
  (+ y (* x 10000)))

(defn square-finder
  "finds the coord for the square"
  [status skipped-lines]
  (loop [y skipped-lines]
    (let [pulled (take-last 100 (pulled-objects status y))]
      (if (fits? status y pulled)
        (result (first pulled) y)
        (recur (inc y))))))

(defn day19a
  "find the points affected by the tractor beam"
  [memory]
  (let [coordinates (mapcat (fn [x] (map (fn [y] [x y]) (range 0 50))) (range 0 50))
        points (map (partial drone-status memory) coordinates)]
    (apply + points)))

(defn day19b
  "find the points affected by the tractor beam"
  [memory]
  (square-finder (partial drone-status memory) 550))