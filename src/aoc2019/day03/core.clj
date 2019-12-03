(ns aoc2019.day03.core)
(require '[clojure.core.match :refer [match]])

(defn parse
  "Split input on comma"
  [input]
  (clojure.string/split input #","))

(defn toTuples
  "Convert raw coordinate string into tuples"
  [input]
  ((juxt #(subs %1 0 1) #(Integer/parseInt (subs %1 1))) input))

(defn generateX
  "Create infinite sequence of coordinates using supplied strategy on the X coordinate"
  [[x y] strategy]
  (map #(into [] [% y]) (iterate strategy (strategy x))))

(defn generateY
  "Create infinite sequence of coordinates using supplied strategy on the Y coordinate"
  [[x y] strategy]
  (map #(into [] [x %]) (iterate strategy (strategy y))))

(defn path
  "Generate path for coordinate"
  [coord [direction count]]
  (match [direction count]
      ["R" cnt] (take cnt (generateX coord inc))
      ["L" cnt] (take cnt (generateX coord dec))
      ["U" cnt] (take cnt (generateY coord inc))
      ["D" cnt] (take cnt (generateY coord dec))
      :else (throw (Exception. "Unsupported direction"))))

(defn follow
  "Takes an accumulator a starting coord and a list of coords tuples and generates coords
   e.g. [[R 3]] generates [[0 1] [0 2] [0 3]]"
  [acc coord lst]
    (if (empty? lst)
      acc
      (let [p (path coord (first lst))]
        (follow (into acc p) (last p) (rest lst)))))

(defn manhattan
  "Calculate manhattan distance for coord"
  [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(defn intersections
  "Finds the intersections between two lines"
  [line1 line2]
  (clojure.set/intersection (into #{} (follow [] [0 0] line1)) (into #{} (follow [] [0 0] line2))))

(defn steps
  "Find the steps to a given intersection"
  [target path]
  (inc (count (take-while #(not (= target %)) path))))

(defn day03a
  "Finds the manhanttan distance for the closest circuit"
  [input]
  (let [[f l] (map #(map toTuples (parse %)) input)]
  (apply min (map manhattan (intersections f l)))))

(defn day03b
  "Calculates the minimum steps for an intersection"
  [input]
  (let [[f l] (map #(map toTuples (parse %)) input)
         line1 (follow [] [0 0] f)
         line2 (follow [] [0 0] l)
         intersect (intersections f l)]
  (apply min (reduce #(cons (+ (steps %2 line1) (steps %2 line2)) %1) [] intersect))))