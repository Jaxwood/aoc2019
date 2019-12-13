(ns aoc2019.day10.core)

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
      (if (and (= 0 (+ a b c)) (not (= x z)) (not (= y z)))
        false
        (recur (rest galaxy) x y)))))