(ns aoc2019.day22.core
  (:require [clojure.string :refer [starts-with? split-lines]]))

(defn deal
  "deal new stack"
  [size n [idx a b]]
  [(- size idx 1) (mod (* -1 a) size) (mod (- size 1 b) size)])

(defn cut
  "cut the stack"
  [size n [idx a b]]
  [(mod (- idx n) size) a (mod (- b n) size)])

(defn increment
  "increment the stack"
  [size n [idx a b]]
  (if (pos? n)
    [(mod (* n idx) size) (mod (* a n) size) (mod (* b n) size)]))

(defn parseCount
  "find the n-amount"
  [line]
  (Integer/parseInt (re-find #"-?\d+" line)))

(defn parse
  "parse the raw lines into instructions"
  [filename fns]
  (let [raw (split-lines (slurp filename))]
    (loop [lines raw acc []]
      (if (empty? lines)
        acc
        (let [line (first lines)]
          (cond
            (starts-with? line "deal into") (recur (rest lines) (conj acc {:name :deal :n 0 :runner (first fns)}))
            (starts-with? line "cut") (recur (rest lines) (conj acc {:name :cut :n (parseCount line) :runner (second fns)}))
            (starts-with? line "deal with") (recur (rest lines) (conj acc {:name :increment :n (parseCount line) :runner (last fns)}))))))))

(defn day22a
  "shuffle the cards according to the different techniques"
  [instructions size idx]
  (if (empty? instructions)
    idx
    (let [instruction (first instructions)
          name (:name instruction)
          runner (:runner instruction)
          n (or (:n instruction) 0)]
      (recur (rest instructions) size (runner size n idx)))))

(defn modpow
  "wrap modpow from Math/BigInteger"
  [b e m]
  (.modPow (biginteger b) (biginteger e) (biginteger m)))

(defn day22b
  "shuffle the cards according to the different techniques"
  [instructions size idx times]
  (if (empty? instructions)
    (let [[i a b] idx
          r (mod (* b (modpow (- 1 a) (- size 2) size)) size)]
      (mod (+ (* (- 2020 r) (modpow a  (* times (- size 2))  size)) r) size))
    (let [instruction (first instructions)
          name (:name instruction)
          runner (:runner instruction)
          n (or (:n instruction) 0)]
      (recur (rest instructions) size (runner size n idx) times))))