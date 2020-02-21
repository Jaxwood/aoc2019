(ns aoc2019.day13.core
  (:require [intcode.core :refer [run]]))

(defn paddle-ball-location
  "get the location of paddle and ball"
  [instructions]
  (let [tiles (partition 3 instructions)
        ball (last (filter (fn [[x y z]] (= z 4)) tiles))
        paddle (last (filter (fn [[x y z]] (= z 3)) tiles))]
    [(first ball) (first paddle)]))

(defn joystick
  "adjust the joystick"
  [instructions]
  (let [[ball paddle] (paddle-ball-location instructions)]
    (cond
      (= ball paddle) 0
      (< ball paddle) -1
      (> ball paddle) 1)))

(defn arcade
  "run all instructions and saves the output"
  [program instructions]
  (case (:status program)
    :interruptible (recur (run (assoc program :input [(joystick instructions)])) instructions)
    :uninterruptible (recur (run program) (conj instructions (:output program)))
    :stopped instructions))

(defn score
  "get the final score"
  [instructions]
  (let [tiles (partition 3 instructions)
        score-tile (last (filter (fn [[x y z]] (and (= x -1) (= y 0))) tiles))]
    (last score-tile)))

(defn day13a
  "find blocks"
  [memory]
  (count
   (filter #(= 2 %1)
           (map last
                (partition 3 (arcade (run {:memory memory :address 0 :input [] :relative 0}) []))))))
(defn day13b
  "play the game"
  [memory]
  (score
   (arcade (run {:memory memory :address 0 :input [] :relative 0}) [])))
