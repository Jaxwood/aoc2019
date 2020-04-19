(ns aoc2019.day22.core
  (:require [clojure.string :refer [starts-with? split-lines]]))

(defn xgcd
  "Extended Euclidean Algorithm"
  [a b]
  (if (= a 0)
    [b 0 1]
    (let [[g x y] (xgcd (mod b a) a)]
      [g (- y (* (Math/floorDiv b a) x)) x])))

(defn mod_inv
  "Get inverse using extended gcd"
  [a b]
  (let [b (if (neg? b) (- b) b)
        a (if (neg? a) (- b (mod (- a) b)) a)
        egcd (xgcd a b)]
    (if (= (first egcd) 1)
      (mod (second egcd) b)
      (throw (str "No inverse since gcd is: " (first egcd))))))

(defn deal
  "deal new stack"
  [size n idx]
  (- size idx 1))

(defn cut
  "cut the stack"
  [size n idx]
  (if (pos? n)
    (if (< idx n)
      (- size (- n idx))
      (- idx n))
    (if (> idx (+ size n))
      (- (- idx n) size)
      (- idx n))))

(defn increment
  "increment the stack"
  [size n idx]
  (mod (* n idx) size))

(defn increment-reversed
  "reverse the increment"
  [size n idx]
  (mod (* idx (mod_inv n size)) size))

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
  [instructions cards idx]
  (if (empty? instructions)
    idx
    (let [instruction (first instructions)
          name (:name instruction)
          runner (:runner instruction)
          n (or (:n instruction) 0)]
      (recur (rest instructions) cards (runner cards n idx)))))