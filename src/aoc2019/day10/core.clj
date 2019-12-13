(ns aoc2019.day10.core)

(defn lines->galaxy
  "Parse raw lines into tuples"
  [lines idx acc]
  (if (empty? lines)
    acc
    (let [line (first lines)]
      (recur (rest lines) (inc idx)
             (into acc
                   (map (fn [[x y]] [x idx])
                        (filter (fn [[x y]] (= y \#))
                                (map-indexed (fn [idx item] [idx item]) line))))))))

(defn parse
  "parse the raw input into a tuple based coordinate system"
  [filename]
  (let [raw (slurp filename)
        lines (clojure.string/split raw #"\r\n")]
    (lines->galaxy lines 0 [])))

(defn intercepting?
  "check if the planet is intercepting"
  [[x1 y1 :as x] [x2 y2 :as y] [x3 y3 :as z]]
    (let [xMax (max x1 x2)
          xMin (min x1 x2)
          yMin (min y1 y2)
          yMax (max y1 y2)]
    (and (<= x3 xMax) (>= x3 xMin) (<= y3 yMax) (>= y3 yMin))))

(defn los?
  "Given two coordinates and a coordinate system
   examine if there is line of sight between the coordinates
   https://www.geeksforgeeks.org/program-check-three-points-collinear/"
  [galaxy [x1 y1 :as x] [x2 y2 :as y]]
  (if (empty? galaxy)
    true
    (let [[x3 y3 :as z] (first galaxy)
          a (* x1 (- y2 y3))
          b (* x2 (- y3 y1))
          c (* x3 (- y1 y2))]
      (if (and (= 0 (+ a b c)) (not (= x z)) (not (= y z)) (intercepting? x y z))
        false
        (recur (rest galaxy) x y)))))

(defn planet-counter
  "count number of visible neighbors"
  [galaxy candidate]
  (let [neighbors (filter #(not (= candidate %)) galaxy)]
  (count (filter #(los? neighbors candidate %1) neighbors))))

(defn day10a
  "find the best planet for a radar station"
  [galaxy]
  (apply max (map #(planet-counter galaxy %1) galaxy)))