(ns aoc2019.day04.core)

(defn toInt
  [c]
  (Integer/parseInt (str c)))

(defn increments?
  "Checks a string for increasing numbers"
  [[f n rest]]
  (if (>= (toInt f) (toInt n))
    false
    (if (char? rest)
      (>= (toInt rest) (toInt n))
      (recur (str n rest)))))

(defn adjacent?
  "Checks a string for two equal adjacent digits"
  [candidate]
  (not (nil? (re-find #"(\d)\1" candidate))))

(defn day04a
  ""
  [low high]
  0)