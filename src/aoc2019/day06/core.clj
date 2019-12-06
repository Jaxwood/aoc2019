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
  [m total [k v]]
  (for [n v]
    (orbit-counter m (inc total) (get m v))))

(defn day06a
  ""
  [filename]
  (let [raw (slurp filename)
        objects (reduce #(tuple->map %1 %2) {} (parse raw))]
        (reduce (partial orbit-counter objects) 0 objects)))