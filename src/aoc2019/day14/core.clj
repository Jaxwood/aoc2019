(ns aoc2019.day14.core)

(defn string->reaction
  "parse a string into a series of reactions"
  [acc raw]
  (let [matches (re-seq #"(\d+) (\w+)" raw)
        [_ amount type] (last matches)
        requires (map (fn [[_ a t]] [(Integer/parseInt a) (keyword t)]) (drop-last matches))]
    (assoc acc (keyword type) {:amount (Integer/parseInt amount) :requires requires})))

(defn parse
  "parse file input into reactions"
  [filename]
  (let [raw (slurp filename)
        lines (clojure.string/split raw #"\r\n")]
    (reduce (fn [acc n] (string->reaction acc n)) {} lines)))

(defn reciepe
  "finds the ingredients for 1 fuel"
  [reactions acc requirements]
  (if (empty? requirements)
    acc
    (let [so-far (reduce (fn [acc [v k]] (update acc k (fn [old] (+ (or old 0) v)))) acc requirements)
          next (filter (fn [[v k]] (not (= :ORE k))) (mapcat (fn [[v k]] (:requires (k reactions))) requirements))]
      (recur reactions so-far next))))

(defn ore-producing?
  "check if ingredient can be exchanged for ore"
  [reactions [k v]]
  (or (some (fn [[_ ky]] (= :ORE ky)) (:requires (k reactions))) false))

(defn calculate
  "based on the needed ingredients find the amount of ore needed"
  [reactions ingredients amount]
  (if (empty? ingredients)
    amount
    (let [[k v] (first ingredients)
          ka (:amount (k reactions))
          [a kk] (first (:requires (k reactions)))]
      (recur reactions (rest ingredients) (+ amount (* a (int (Math/ceil (/ v ka)))))))))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (let [reci (reciepe reactions {} (:requires (:FUEL reactions)))
        requirements (filter (partial ore-producing? reactions) reci)]
    (calculate reactions requirements 0)))