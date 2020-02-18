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
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} turn new-color]
  (match [direction turn]
    [:north left]  {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:north right] {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:south left]  {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:south right] {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west  :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:west left]   {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:west right]  {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:east left]   {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (assoc breadcrumbs [x y] new-color)}
    [:east right]  {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (assoc breadcrumbs [x y] new-color)}))

(defn day11a
  "panels painted atleast once"
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} program]
  (let [hull-color (run (assoc program :input [color]))
        robot-direction (run (assoc hull-color :input []))]
      (if (= :stopped (:status robot-direction))
        (count (into #{} (:breadcrumbs state)))
        (recur (move state (:output robot-direction) (:output hull-color)) robot-direction))))