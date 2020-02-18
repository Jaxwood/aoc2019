(ns aoc2019.day07.core
  (:require [intcode.core :refer [run]]))

(defn thrusters
  "find highest signal that can be sent to the thrusters"
  [memory settings address]
  (let [a (run {:memory memory :address address :input [(get settings 0) 0] :relative 0})
        b (run {:memory memory :address address :input [(get settings 1) (get a :output)] :relative 0})
        c (run {:memory memory :address address :input [(get settings 2) (get b :output)] :relative 0})
        d (run {:memory memory :address address :input [(get settings 3) (get c :output)] :relative 0})
        e (run {:memory memory :address address :input [(get settings 4) (get d :output)] :relative 0})]
    (if (= :stopped (:status e))
      0
      [a b c d e])))

(defn feedback
  "Exectute the feedback loop"
  [[a b c d e]]
  (let [a' (run {:memory (:memory a) :address (:address a) :input (vec (conj (:input a) (:output e))) :output (:output a) :relative 0})
        b' (run {:memory (:memory b) :address (:address b) :input (vec (conj (:input b) (:output a'))) :output (:output b) :relative 0})
        c' (run {:memory (:memory c) :address (:address c) :input (vec (conj (:input c) (:output b'))) :output (:output c) :relative 0})
        d' (run {:memory (:memory d) :address (:address d) :input (vec (conj (:input d) (:output c'))) :output (:output d) :relative 0})
        e' (run {:memory (:memory e) :address (:address e) :input (vec (conj (:input e) (:output d'))) :output (:output e) :relative 0})]
    (if (= :stopped (:status e'))
      (:output e)
      (recur [a' b' c' d' e']))))

(defn day07a
  "find the highest signal"
  [program perms acc]
  (if (empty? perms)
    (apply max acc)
    (recur program (rest perms) (conj acc (get (last (thrusters program (first perms) 0)) :output)))))

(defn day07b
  "find the best phase settings"
  [program perms acc]
  (if (empty? perms)
    (apply max acc)
    (recur program (rest perms) (conj acc (feedback (thrusters program (first perms) 0))))))