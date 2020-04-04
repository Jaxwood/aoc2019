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

(def walk-routine
  "(¬A v ¬C) ^ D"
  ["NOT A J"
   "NOT C T"
   "OR T J"
   "AND D J"
   "WALK"])

(def run-routine
  "(¬A v ¬B v (¬C ^ H)) ^ D"
  ["NOT A J"
   "NOT B T"
   "OR T J"
   "NOT C T"
   "AND H T"
   "OR T J"
   "AND D J"
   "RUN"])

(defn jump
  "execute the jumping logic"
  [program acc]
  (let [state (run program)]
    (if (= (:status state) :stopped)
      acc
      (recur state (conj acc (:output state))))))

(defn day21a
  "find the hull damage"
  [memory]
  (let [program {:memory memory :address 0 :relative 0 :input (mapcat translate walk-routine)}]
    (last (jump program []))))

(defn day21b
  "find the hull damage"
  [memory]
  (let [program {:memory memory :address 0 :relative 0 :input (mapcat translate run-routine)}]
    (last (jump program []))))