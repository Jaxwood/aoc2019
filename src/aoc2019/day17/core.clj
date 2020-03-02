(ns aoc2019.day17.core
  (:require [intcode.core :refer [run]]
            [clojure.string :as s]))

(defn camera
  "retrieve the camera output"
  [program inputs acc]
  (let [state (run program)
        status (:status state)]
    (cond
      (= status :stopped) acc
      (= status :interruptible) (recur (assoc state :input (first inputs)) (rest inputs) acc)
      :else (recur state inputs (conj acc (:output state))))))

(defn new-line?
  "predicate to check for new line character"
  [c]
  (= c 10))

(defn camera-lines
  "group the camera output into lines"
  [camera]
  (partition-by new-line? camera))

(defn ascii
  "get the ascii character"
  [output]
  (cond
    (= output 46) "."
    (= output 35) "#"
    (= output 10) "\r\n"
    :else (str (char output))))

(defn draw
  "draw the camera output"
  [filename lines]
  (loop [line lines]
    (if (empty? line)
      lines
      (do
        (spit filename (reduce (fn [acc n] (str acc (ascii n))) "" (first line)) :append true)
        (recur (rest line))))))

(defn coords
  "represent the scaffold as coordinates and store them in a map"
  [lines y acc]
  (if (empty? lines)
    acc
    (let [line (first lines)]
      (if (some #(= % 10) line)
        (recur (rest lines) y acc)
        (recur (rest lines) (dec y) (into acc (map-indexed (fn [idx num] [[idx y] num]) (first lines))))))))

(defn intersection?
  "is this coordinate at an intersection point"
  [scaffold [[x y] val]]
  (and
   (= 35 val)
   (= 35 (get scaffold [(inc x) y]))
   (= 35 (get scaffold [(dec x) y]))
   (= 35 (get scaffold [x (inc y)]))
   (= 35 (get scaffold [x (dec y)]))))

(defn sum
  "find the sum of the alignment parameters"
  [acc [[x y] _]]
  (+ acc (* x (Math/abs ^Integer y))))

(defn day17a
  "calculate solution for day17a"
  [memory]
  (let [camera-output (camera {:memory memory :address 0 :relative 0 :input []} [] [])
        scaffold (coords (camera-lines camera-output) 0 {})]
    (reduce sum 0 (filter (partial intersection? scaffold) scaffold))))

(defn translate
  "translate instruction into machine commands"
  [commands]
  (loop [instructions commands acc []]
    (if (empty? instructions)
      (conj acc 10)
      (recur (rest instructions) (conj acc (int (first instructions)))))))

(defn day17b
  "calculate solution for day17b"
  [memory]
  (let [main (translate "B,C,B,A,C,A,C,A,B,C")
        a-routine (translate "L,4,R,4,L,4,R,8")
        b-routine (translate "L,6,L,4,R,8")
        c-routine (translate "R,8,L,6,L,4,L,10,R,8")
        yes-no (translate "n")
        inputs [main a-routine b-routine c-routine yes-no]
        camera-output (camera {:memory (assoc memory 0 2) :address 0 :relative 0 :input []} inputs [])]
    (last camera-output)))