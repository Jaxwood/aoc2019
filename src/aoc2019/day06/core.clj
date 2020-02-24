(ns aoc2019.day06.core)
(require '[clojure.set :refer [difference]])

(defn parse
  "parse lines into tuples"
  [raw]
  (map #(clojure.string/split % #"\)") (clojure.string/split-lines raw)))

(defn tuple->map
  "convert tuples into a map"
  [m [k v]]
  (let [old (get m k)]
    (if (nil? old)
      (assoc m k (conj [] v))
      (assoc m k (conj old v)))))

(defn orbit-counter
  "find the number of orbits for a given input"
  [m v sum]
  (if (empty? v)
    sum
    (recur m (into (get m (first v)) (rest v)) (inc sum))))

(defn finder
  "find target in collection"
  [[k v] target]
  (some #(= % target) v))

(defn neighbors
  "find the planets neighbors that has not been visited yet"
  [m visited target]
  (let [orbits (get m target)
        orbiting (keys (filter #(finder % target) m))]
    (vec (difference (set (into orbiting orbits)) (set visited)))))

(defn santa-finder
  "find santa among the planets"
  [m k visited target]
  (let [[p sum] (first k)
        planets (neighbors m visited p)]
    (if (some #(= % target) planets)
      sum
      (recur m (into (drop 1 k) (map #(conj [] % (inc sum)) planets)) (conj visited p) target))))

(defn day06a
  "find the total number of orbits"
  [filename]
  (let [raw (slurp filename)
        objects (reduce #(tuple->map %1 %2) {} (parse raw))]
    (apply + (map #(orbit-counter objects (get objects %) 0) (keys objects)))))

(defn day06b
  "find santa"
  [filename]
  (let [raw (slurp filename)
        objects (reduce #(tuple->map %1 %2) {} (parse raw))
        start (vec (keys (filter #(finder % "YOU") objects)))]
        (santa-finder objects (vector (conj start 1)) ["YOU"] "SAN")))