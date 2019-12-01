(ns aoc2019.day01.core)

(defn calculateFuel
  "Calculate fuel required to lanch module"
  [mass]
  (int (- (Math/floor (/ mass 3)) 2)))

(defn day01a
  "Day01a"
  [filename]
  (def raw (slurp filename))
  (def masses (map #(Integer/parseInt %1) (clojure.string/split raw #"\r\n")))
  (reduce + 0 (map calculateFuel masses)))