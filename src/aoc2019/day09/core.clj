(ns aoc2019.day09.core
  (:require [intcode.core :refer [run]]))

(defn day09a
  "Find answer for day09a"
  [memory]
  (:output (run {:memory memory :address 0 :relative 0 :input [1]})))

(defn day09b
  "Find answer for day09b"
  [memory]
  (:output (run {:memory memory :address 0 :relative 0 :input [2]})))