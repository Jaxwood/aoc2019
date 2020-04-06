(ns aoc2019.day22.core
  (:require [clojure.string :refer [starts-with? split-lines]]))

(defn deal
  "deal new stack"
  [cards]
  (vec (reverse cards)))

(defn deal-indexed
  "deal new stack"
  [size n idx]
  (- size idx 1))

(defn cut
  "cut the stack"
  [cards n]
  (if (pos? n)
    (into (subvec cards n) (subvec cards 0 n))
    (let [idx (+ (count cards) n)]
      (into (subvec cards idx) (subvec cards 0 idx)))))

(defn cut-indexed
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
  [cards n]
  (let [size (count cards)]
    (loop [idx 1 acc []]
      (if (= idx size)
        (vec (cons (first cards) (mapv second (sort-by first acc))))
        (let [new-idx (* idx n)
              shifted (mod new-idx size)]
          (if (> new-idx size)
            (recur (inc idx) (conj acc [shifted (nth cards idx)]))
            (recur (inc idx) (conj acc [new-idx (nth cards idx)]))))))))

(defn increment-indexed
  "increment the stack"
  [size n idx]
  (let [new-idx (* idx n)
        shifted (mod new-idx size)]
    (if (> new-idx size)
      shifted
      new-idx)))

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