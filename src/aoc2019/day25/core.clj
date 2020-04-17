(ns aoc2019.day25.core
  (:require [intcode.core :refer [run]]))

(defn printer
  "print the statements"
  [initial acc]
  (let [state (run initial)]
    (cond
      (= (:status state) :uninterruptible)
      (recur state (conj acc (str (char (:output state)))))
      (or (= (:status state) :interruptible)
          (= (:status state) :stopped))
      [state acc])))

(defn print-to-file
  "print output from the droid to a file"
  [filename command]
  (spit filename (apply str command) :append true))

(defn print-to-console
  "print output from the droid to the console"
  [command]
  (println (apply str command)))

(defn translate
  "translate instruction into machine commands"
  [commands]
  (loop [instructions commands acc []]
    (if (empty? instructions)
      (conj acc 10)
      (recur (rest instructions) (conj acc (int (first instructions)))))))

(defn traverse
  "traverse the ship"
  [state instructions]
  (let [[state output] (printer state [])
        instruction (first instructions)]
    (if (= :stopped (:status state))
      (first (map #(Integer/parseInt %) (re-seq #"\d+" (apply str output))))
      (if (empty? instructions)
        (let [user-input (read-line)]
          (recur state [user-input]))
        (recur (assoc state :input (translate instruction)) (rest instructions))))))

(defn day25a
  "find the password"
  [memory]
  (traverse
   {:memory memory :address 0 :relative 0 :input []}
   ["north"
    "west"
    "take planetoid"
    "west"
    "take spool of cat6"
    "east"
    "east"
    "south"
    "west"
    "south"
    "east"
    "west"
    "north"
    "north"
    "take dark matter"
    "south"
    "east"
    "east"
    "north"
    "take sand"
    "west"
    "take coin"
    "west"
    "south"
    "take wreath"
    "west"
    "take fuel cell"
    "east"
    "north"
    "north"
    "west"
    "east"
    "south"
    "north"
    "west"
    "south"
    "east"
    "south"
    "east"
    "north"
    "take jam"
    "south"
    "west"
    "north"
    "west"
    "drop coin"
    "drop dark matter"
    "drop planetoid"
    "drop wreath"
    "south"]))