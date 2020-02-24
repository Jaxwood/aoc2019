(ns aoc2019.day01.core)

(defn calculateFuel
  "Calculate fuel required to launch module"
  [mass]
  (int (- (Math/floor (/ mass 3)) 2)))

(defn calculateAdditionalFuel
  "Calculate additional fuel required to launch module"
  [mass sum]
  (def fuel (calculateFuel mass))
  (if (<= fuel 0)
    sum
    (calculateAdditionalFuel fuel (+ sum fuel))))

(defn day01a
  "Day01a"
  [filename]
  (def raw (slurp filename))
  (def masses (map #(Integer/parseInt %1) (clojure.string/split-lines raw)))
  (reduce + 0 (map calculateFuel masses)))

(defn day01b
  "Day01b"
  [filename]
  (def raw (slurp filename))
  (def masses (map #(Integer/parseInt %1) (clojure.string/split-lines raw)))
  (reduce + 0 (map #(calculateAdditionalFuel %1 0) masses)))