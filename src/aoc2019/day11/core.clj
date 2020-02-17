(ns aoc2019.day11.core
  (:require [intcode.core :refer [run]]
            [clojure.core.match :refer [match]]))
;; state
;; :position [x y (:black|:white)]
;; :direction (:up|:down|:left|:right)
;; :breadcrumbs [[x y (:black|:white)]]

(defn int->color
  "convert an integer to a color"
  [raw]
  (case raw
    0 :black
    1 :white))

(defn color->int
  "convert an color to an integer"
  [raw]
  (case raw
    :black 0
    :white 1))

(defn int->direction
  "convert an integer to a direction"
  [raw]
  (case raw
    0 :left
    1 :right))

(defn set-input
  "sets the input of the program"
  [color {:keys [memory address relative input output]}]
  {:memory memory :address address :relative relative :input (color->int color) :output output})

(defn find-color
  "finds the color"
  [x y breadcrumbs]
  (let [existing (filter #(and (= (first %1) x) (= (second %1) y)) breadcrumbs)]
    (if (empty? existing)
      :black
      ((comp last last) existing))))

(defn move
  "moves the hull painting robot"
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} turn new-color]
  (match [direction turn]
    [:north :left]  {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west :breadcrumbs  (conj breadcrumbs [x y new-color])}
    [:north :right] {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east :breadcrumbs  (conj breadcrumbs [x y new-color])}
    [:south :left]  {:position [(inc x) y (find-color (inc x) y breadcrumbs)] :direction :east :breadcrumbs  (conj breadcrumbs [x y new-color])}
    [:south :right] {:position [(dec x) y (find-color (dec x) y breadcrumbs)] :direction :west :breadcrumbs  (conj breadcrumbs [x y new-color])}
    [:west :left]   {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (conj breadcrumbs [x y new-color])}
    [:west :right]  {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (conj breadcrumbs [x y new-color])}
    [:east :left]   {:position [x (inc y) (find-color x (inc y) breadcrumbs)] :direction :north :breadcrumbs (conj breadcrumbs [x y new-color])}
    [:east :right]  {:position [x (dec y) (find-color x (dec y) breadcrumbs)] :direction :south :breadcrumbs (conj breadcrumbs [x y new-color])}))

(defn day11a
  "panels painted atleast once"
  [{[x y color] :position direction :direction breadcrumbs :breadcrumbs, :as state} program]
  (let [hull-color (run (set-input color program))
        robot-direction (run (set-input (int->color (:output hull-color)) hull-color))]
    (recur (move state (int->direction (:output robot-direction)) (int->color (:output hull-color))) robot-direction)))