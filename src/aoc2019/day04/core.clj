(ns aoc2019.day04.core)

(defn toInt
  "Parse char to integer"
  [c]
  (Integer/parseInt (str c)))

(defn valid?
  "Check a vector of numbers if they are increasing in size
  e.g. [1 2 3] is valid but [0 2 1] is not valid"
  [input]
  (if (= 1 (count input))
    true
    (if (> (first input) (first (rest input)))
      false
      (recur (rest input)))))

(defn toIntegers
  "Convert a string to an integer vector"
  [input]
  (reduce #(conj %1 (toInt %2)) [] input))

(defn adjacent-two?
  "Check for ONLY two adjacent digits"
  [candidate]
  (if (empty? candidate)
    false)
  (let [num (first candidate)
        len (take-while #(= % num) candidate)
        rest (drop-while #(= % num) candidate)]
    (if (= 2 (count (into [] len)))
      true
      (if (empty? (into [] rest))
        false
        (recur (into [] rest))))))

(defn adjacent?
  "Checks a string for two or more equal adjacent digits"
  [candidate]
  (not (nil? (re-find #"(\d)\1" candidate))))

(defn check
  "Iterates from low to high and validates predicates"
  [low high acc]
  (if (= low high)
    acc
    (if (and (valid? (toIntegers (str low))) (adjacent? (str low)))
      (recur (inc low) high (inc acc))
      (recur (inc low) high acc))))

(defn check-two
  "Iterates from low to high and validates predicates"
  [low high acc]
  (if (= low high)
    acc
    (if (and (valid? (toIntegers (str low))) (adjacent-two? (toIntegers (str low))))
      (recur (inc low) high (inc acc))
      (recur (inc low) high acc))))

(defn day04a
  "Find valid passwords"
  [low high]
  (check low high 0))

(defn day04b
  "Find valid passwords"
  [low high]
  (check-two low high 0))