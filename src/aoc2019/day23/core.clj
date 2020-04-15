(ns aoc2019.day23.core
  (:require [intcode.core :refer [run]]))

(defn inc-index
  "increment the index"
  [old]
  (if (= old 49)
    0
    (inc old)))

(defn transmit
  "the next message to send"
  [queue idx]
  (if (empty? queue)
    [idx -1]
    (first queue)))

(defn day23a
  "find the Y-value send to address 255"
  [memory]
  (let [nics (into {} (map (fn [idx] [idx (run {:memory memory :relative 0 :address 0 :input [idx]})]) (range 50)))]
    (loop [idx 0 network nics queue []]
      (let [msg (transmit queue idx)
            program (run (assoc (get network (first msg)) :input (rest msg)))]
        (cond
          (= (:status program) :interruptible)
          (recur (inc-index idx) (assoc network (first msg) program) (rest queue))
          (= (:status program) :uninterruptible)
          (let [x (run program)
                y (run x)]
            (if (= (:output program) 255)
              (:output y)
              (recur idx (assoc network (first msg) y) (conj (vec (rest queue)) [(:output program) (:output x) (:output y)])))))))))

(defn day23b
  "find the Y-value send twice to the NAT"
  [memory]
  (let [nics (into {} (map (fn [idx] [idx (run {:memory memory :relative 0 :address 0 :input [idx]})]) (range 50)))]
    (loop [idx 0 network nics queue [] nat #{} idle 0]
      (if (> idle 50)
        (let [[a x y, :as zero] (get network 255)]
          (if (contains? nat y)
            y
            (recur idx network (conj queue zero) (conj nat y) 0)))
        (let [msg (transmit queue idx)
              program (run (assoc (get network (first msg)) :input (rest msg)))]
          (cond
            (= (:status program) :interruptible)
            (recur (inc-index idx) (assoc network (first msg) program) (rest queue) nat (inc idle))
            (= (:status program) :uninterruptible)
            (let [x (run program)
                  y (run x)]
              (if (= (:output program) 255)
                (recur idx (assoc (assoc network (first msg) y) 255 [0 (:output x) (:output y)]) (rest queue) nat 0)
                (recur idx (assoc network (first msg) y) (conj (vec (rest queue)) [(:output program) (:output x) (:output y)]) nat 0)))))))))