(ns aoc2019.day18.core
  (:require [clojure.string :refer [split-lines]]))

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