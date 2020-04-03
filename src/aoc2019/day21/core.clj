(ns aoc2019.day21.core
  (:require [intcode.core :refer [run]]))

(defn translate
  "translate instruction into machine commands"
  [commands]
  (loop [instructions commands acc []]
    (if (empty? instructions)
      (conj acc 10)
      (recur (rest instructions) (conj acc (int (first instructions)))))))

(defn ascii
  "convert to ascii code"
  [code]
  (cond
    (= code 10) "\r\n"
    :else (str (char code))))

(defn draw
  "draw the sensor output"
  [filename lines]
  (loop [line lines]
    (if (empty? line)
      lines
      (do
        (spit filename (ascii (first line)) :append true)
        (recur (rest line))))))

(def routine [
  "NOT B J"
  "AND D J"
  "NOT A T"
  "AND D T"
  "OR T J"
  "NOT C T"
  "AND D T"
  "OR T J"
  "WALK"
])

(defn jump
  "execute the jumping logic"
  [program acc]
  (let [state (run program)]
    (if (= (:status state) :stopped)
      acc
      (recur state (:output state)))))

(defn day21a
  "find the hull damage"
  [memory]
  (let [program {:memory memory :address 0 :relative 0 :input (mapcat translate routine)}]
    (jump program [])))