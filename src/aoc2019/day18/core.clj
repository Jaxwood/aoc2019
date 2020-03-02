(ns aoc2019.day18.core
  (:require [clojure.string :refer [split-lines]]))

(defn tile
  "find the tile"
  [obstacle]
  (cond
    (= obstacle "#") :wall
    (= obstacle ".") :open
    (= obstacle "@") :entrance
    :else (if (nil? (re-seq #"[A-Z]+" obstacle))
            :key
            :door)))

(defn parse
  "parse the vault"
  [filename]
  (let [lines (split-lines (slurp filename))]
    (loop [line lines acc {} y 0]
      (if (empty? line)
        acc
        (let [coords (map-indexed (fn [idx itm] [[idx y] (tile (str itm))]) (first line))]
          (recur (rest line) (into acc coords) (dec y)))))))

(defn day18a
  "solution for day18a"
  [vault]
  vault)