(ns aoc2019.day11.core
  (:require [aoc2019.day09.core :refer [opcode]]
            [clojure.core.match :refer [match]]))

;;  :program []
;;  :pointer 0
;;  :signals []
;;  :base 0

(def initial {:position [0 0]
              :direction :north
              :breadcrumbs []})

(defn move
  "Takes a state and a direction (:left or :right) and calculates next state"
  [state next]
  (let [direction (:direction state)
        [x y :as position] (:position state)
        breadcrumbs (:breadcrumbs state)]
    (match [direction next]
      [:north :left] {:breadcrumbs (conj breadcrumbs position) :direction :west :position [(dec x) y]}
      [:north :right] {:breadcrumbs (conj breadcrumbs position) :direction :east :position [(inc x) y]}
      [:south :left] {:breadcrumbs (conj breadcrumbs position) :direction :east :position [(inc x) y]}
      [:south :right] {:breadcrumbs (conj breadcrumbs position) :direction :west :position [(dec x) y]}
      [:west :left] {:breadcrumbs (conj breadcrumbs position) :direction :south :position [x (dec y)]}
      [:west :right] {:breadcrumbs (conj breadcrumbs position) :direction :north :position [x (inc y)]}
      [:east :left] {:breadcrumbs (conj breadcrumbs position) :direction :north :position [x (inc y)]}
      [:east :right] {:breadcrumbs (conj breadcrumbs position) :direction :south :position [x (dec y)]})))

(defn paint
  "toogle paint between black and white"
  [color]
  (match [color]
    [:white] :black
    [:black] :white))

(defn blah
  ""
  [instructions]
  (let [a (opcode {:program instructions :pointer 0 :signals [0] :base 0})
        b (opcode {:program (:program a) :pointer (:pointer a) :signals [(:output a)] :base (:base a)})]
    [(:output a) (:output b)]))

(defn day11a
  ""
  []
  0)