(ns aoc2019.day01.core)

(defn day01a
  "Calculate fuel required to lanch module"
  [x]
  (int (- (Math/floor (/ x 3)) 2)))
