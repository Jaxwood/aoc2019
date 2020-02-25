(ns aoc2019.day14.core)

(defn string->reaction
  "parse a string into a series of reactions"
  [acc raw]
  (let [matches (re-seq #"(\d+) (\w+)" raw)
        [_ amount type] (last matches)
        requires (map (fn [[_ a t]] [(keyword t) (Integer/parseInt a)]) (drop-last matches))]
    (assoc acc (keyword type) {:amount (Integer/parseInt amount) :requires requires})))

(defn parse
  "parse file input into reactions"
  [filename]
  (let [raw (slurp filename)
        lines (clojure.string/split-lines raw)]
    (reduce (fn [acc n] (string->reaction acc n)) {} lines)))

(defn calculate
  "calculate amount of ore needed for 1 fuel"
  [reactions ingredients acc]
  (if (empty? ingredients)
    acc
    (let [[k needed] (first ingredients)
          produced (:amount (k reactions))
          [_ ore] (first (:requires (k reactions)))
          factor (int (Math/ceil (/ needed produced)))]
      (recur reactions (rest ingredients) (+ acc (* ore factor))))))

(defn group-by-type
  "groups ingredients by type"
  [reactions ingredients acc]
  (if (empty? ingredients)
    (vec acc)
    (let [[k v] (first ingredients)]
      (recur reactions (rest ingredients) (update acc k (fn [old] (+ v (or old 0))))))))

(defn ore?
  "does it produce ore?"
  [chemical]
  (let [[k _] (first (:requires chemical))]
    (= :ORE k)))

(defn chain-reaction
  "start the chain reaction"
  [reactions requires acc surplus]
  (if (empty? requires)
    acc
    (let [[key amount, :as ingredient] (first requires)
          chemical (key reactions)
          leftover (or (key surplus) 0)
          factor (int (Math/ceil (/ (- amount leftover) (:amount chemical))))
          extra (- (* (:amount chemical) factor) amount)
          adjusted (map (fn [[k v]] [k (* v factor)]) (:requires chemical))]
      (if (ore? chemical)
        (recur reactions (rest requires) (concat acc [ingredient]) surplus)
        (recur reactions (concat (rest requires) adjusted) acc (update surplus key (fn [old] (+ extra (or old 0)))))))))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (let [fuel (:requires (:FUEL reactions))
        ore-ingredients (chain-reaction reactions fuel [] {})
        grouped (group-by-type reactions ore-ingredients {})]
    (calculate reactions grouped 0)))