(ns aoc2019.day11.core
  (:require [intcode.core :refer [run]]
            [clojure.core.match :refer [match]]))

(def black 0)
(def white 1)

(def left 0)
(def right 1)

(defn find-color
  "finds the color"
  [x y breadcrumbs]
  (get breadcrumbs [x y] black))

(defn move
  "moves the hull painting robot"
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} new-color new-direction]
  (match [direction new-direction]
    [:north 0] {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:north 1] {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:south 0] {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:south 1] {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:west 0] {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:west 1] {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:east 0] {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:east 1] {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (assoc breadcrumbs [x y] new-color)}))

(defn paint
  "paint the hull"
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} program]
  (let [hull-color (run (assoc program :input [color]))
        robot-direction (run (assoc hull-color :input []))]
    (if (= :stopped (:status robot-direction))
      (:breadcrumbs state)
      (recur (move state (:output hull-color) (:output robot-direction)) robot-direction))))

(defn day11a
  "panels painted atleast once"
  [state program]
  (count (paint state program)))

(defn draw-line
  "draw black and white squares"
  [m x y]
  (if (= 1 (get m [x y]))
    "#"
    "-"))

(defn build-string
  "build a string that can be printed"
  [line m x y acc]
  (if (= x 100)
    (str acc "\n")
    (if (contains? m [x y])
      (recur line m (inc x) y (str acc (draw-line m x y)))
      (recur line m (inc x) y (str acc "-")))))

(defn day11b
  "find the registration identifier"
  [state program]
  (let [hull (paint state program)
        lines (vec (concat (vals (group-by last (sort-by (juxt last first) (keys hull))))))]
    (count hull)))
