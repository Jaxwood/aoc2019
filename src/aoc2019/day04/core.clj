(ns aoc2019.day04.core)

(defn toInt
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

(defn increments?
  "Convert a string to an integer vector and check if the vector has
  increasing number using the valid? function"
  [in]
  (let [a (reduce #(conj %1 (toInt %2)) [] in)]
  (valid? a)))

(defn adjacent?
  "Checks a string for two equal adjacent digits"
  [candidate]
  (not (nil? (re-find #"(\d)\1" candidate))))

(defn check
  "Iterates from low to high and validates predicates"
  [low high acc]
  (if (= low high)
    acc
    (if (and (increments? (str low)) (adjacent? (str low)))
      (recur (inc low) high (inc acc))
      (recur (inc low) high acc))))

(defn day04a
  "Find valid passwords"
  [low high]
  (check low high 0))