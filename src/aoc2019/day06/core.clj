(ns aoc2019.day06.core)

(defn parse
  "parse lines into objects"
  [raw]
  (map #(clojure.string/split % #"\)") (clojure.string/split raw #"\r\n")))

(defn tuple->map
  ""
  [m [k v]]
  (let [old (get m k)]
    (if (nil? old)
      (assoc m k (conj [] v))
      (assoc m k (conj old v)))))

(defn orbit-counter
  ""
  [m k sum]
  (let [v (get m k)]
    (if (nil? v)
      sum
      (apply + (count v) (map #(orbit-counter m % sum) v))))
)

(defn day06a
  ""
  [filename]
  (let [raw (slurp filename)
        objects (reduce #(tuple->map %1 %2) {} (parse raw))]
        (apply + (map #(orbit-counter objects % 0) (keys objects)))))