(ns aoc2019.day14.core)

(defn string->reaction
  "parse a string into a series of reactions"
  [raw]
  (let [[left right] (clojure.string/split raw #" => ")
        commas (clojure.string/split left #", ")
        keypairs (map (fn [x] (clojure.string/split x #"\s")) commas)
        in-result (map (fn [[k v]] [(Integer/parseInt k) v]) keypairs)
        [i o] (clojure.string/split right #"\s")]
    {:in (vec in-result)
     :out [(Integer/parseInt i) o]}))

(defn parse
  "parse file input into reactions"
  [filename]
  (let [raw (slurp filename)
        lines (clojure.string/split raw #"\r\n")]
    (map string->reaction lines)))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (println reactions)
  0)