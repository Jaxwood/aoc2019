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
        lines (clojure.string/split-lines raw)]
    (reduce (fn [acc n] (string->reaction acc n)) {} lines)))

(defn diff
  "sum up the total for the reactants"
  [[v1 k1] [v2 k2]]
  [(- v1 v2) k1])

(defn sum
  "sum up the total for the reactants"
  [acc [k vs]]
  (let [total (reduce (fn [ac [vals _]] (+ ac vals)) 0 vs)]
    (concat acc [[total k]])))

(defn ore?
  "does the ingredient produce ore?"
  [reactions [_ k]]
  (or (some (fn [[_ k]] (= :ORE k)) (:requires (k reactions))) false))

(defn process
  "process the required reactants"
  [reactions reactants acc surplus]
  (if (empty? reactants)
    [acc surplus]
    (let [[v k] (first reactants)
          chemical (k reactions)]
      (if (every? (partial ore? reactions) [[v k]])
        (recur reactions (rest reactants) (concat acc [[v k]]) surplus)
        (let [extra (or (k surplus) 0)
              factor (int (Math/ceil (/ (- v extra) (:amount chemical))))
              leftover (- (* factor (:amount chemical)) (- v extra))
              adjusted (map (fn [[vv kk]] [(* factor vv) kk]) (:requires chemical))]
          (recur reactions (rest reactants) (concat acc adjusted) (update surplus k (fn [old] (+ leftover (- (or old 0) extra))))))))))

(defn react
  "keep looping until all reactants can produce ore"
  [reactions reactants surplus]
  (if (every? (partial ore? reactions) reactants)
    reactants
    (let [[reciepe leftover] (process reactions reactants [] surplus)
          accumulated (reduce sum [] (group-by second reciepe))]
      (recur reactions accumulated leftover))))

(defn calculate
  "based on the needed reactants find the amount of ore needed"
  [reactions reactants amount]
  (if (empty? reactants)
    amount
    (let [[total k] (first reactants)
          chemical (:amount (k reactions))
          [ore _] (first (:requires (k reactions)))]
      (recur reactions (rest reactants) (+ amount (* ore (int (Math/ceil (/ total chemical)))))))))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (calculate reactions (react reactions (:requires (:FUEL reactions)) {}) 0))
