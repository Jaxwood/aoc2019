(ns aoc2019.day06.core)

(defn parse
  "parse lines into objects"
  [raw]
  (map #(clojure.string/split % #"\)") (clojure.string/split raw #"\r\n")))

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
    (recur m (into (get m (first v) ) (rest v)) (inc sum))))

(defn day06a
  "find the total number of orbits"
  [filename]
  (let [raw (slurp filename)
        objects (reduce #(tuple->map %1 %2) {} (parse raw))]
        (apply + (map #(orbit-counter objects (get objects %) 0) (keys objects)))))