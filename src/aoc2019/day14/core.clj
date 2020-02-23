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

(defn process
  "process the required ingredients"
  [reactions requirements acc]
  (if (empty? requirements)
    acc
    (let [[v k] (first requirements)
          resolution (k reactions)]
      (if (or (some (fn [[_ kk]] (= kk :ORE)) (:requires resolution)) false)
        (recur reactions (rest requirements) (concat acc [[v k]]))
        (let [factor (int (Math/ceil (/ v (:amount resolution))))
              adjusted (map (fn [[v k]] [(* factor v) k]) (:requires resolution))]
          (recur reactions (conj (rest requirements)) (concat acc adjusted)))))))

(defn sum
  "sum up the total for the ingredients"
  [acc [k v]]
  (let [total (reduce (fn [a [vals _]] (+ a vals)) 0 v)]
    (concat acc [[total k]])))

(defn ore?
  "does the ingredient produce ore?"
  [reactions [v k]]
  (let [rs (:requires (k reactions))]
    (or (some (fn [[v k]] (= :ORE k)) rs) false)))

(defn calculate
  "based on the needed ingredients find the amount of ore needed"
  [reactions ingredients amount]
  (if (empty? ingredients)
    amount
    (let [[v k] (first ingredients)
          ka (:amount (k reactions))
          [a kk] (first (:requires (k reactions)))]
      (recur reactions (rest ingredients) (+ amount (* a (int (Math/ceil (/ v ka)))))))))

(defn resolver
  "keep looping until all ingredients can produce ore"
  [reactions requirements]
  (println requirements)
  (if (= true (every? (partial ore? reactions) requirements))
    requirements
    (let [reciepe (process reactions requirements [])
          accumulated (reduce sum [] (group-by second reciepe))]
      (recur reactions accumulated))))

(defn day14a
  "Find the amount of ore for 1 fuel"
  [reactions]
  (calculate reactions (resolver reactions (:requires (:FUEL reactions))) 0))