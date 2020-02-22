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
    (vec (map string->reaction lines))))

(defn has?
  "filter based on a type"
  [type {:keys [in out]}]
  (let [[k v] out]
    (= v type)))

(defn produces?
  "finds the fuel"
  [type reactions]
  (filter (partial has? type) reactions))

(defn ore?
  "does the ingredients contain ore?"
  [[amount type]]
  (= "ORE" type))

(defn ore
  "find the amount of ore for an ingredient"
  [reactions [amount type]]
  (let [ingredients (mapcat :in (produces? type reactions))
        os (first (filter ore? ingredients))]
    (if (nil? os)
      (reduce (fn [acc n] (+ acc (ore reactions n))) 0 ingredients)
      (first os))))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (let [ingredients (:in (first (produces? "FUEL" reactions)))]
    ingredients))