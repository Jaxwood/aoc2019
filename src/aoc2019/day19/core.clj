(ns aoc2019.day19.core
  (:require [intcode.core :refer [run]]))

(defn day19a
  "find the points affected by the tractor beam"
  [memory]
  (let [state {:memory memory :address 0 :relative 0 :input []}
        coordinates (mapcat (fn [x] (map (fn [y] [x y]) (range 0 50))) (range 0 50))
        points (map (fn [coord] (:output (run (assoc state :input coord)))) coordinates)]
    (apply + points)))