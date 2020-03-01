(ns aoc2019.day16.core)

(def base '(0 1 0 -1))

(defn offset
  "get the offset to read from"
  [input]
  (Integer/parseInt (apply str (take 7 input))))

(defn pattern
  "find the pattern value based on index"
  [recurring idx]
  (let [c (* recurring 4)
        i (mod idx c)]
    (cond
      (< i recurring) 0
      (< i (* 2 recurring)) 1
      (< i (* 3 recurring)) 0
      (< i (* 4 recurring)) -1)))

(defn calculate
  "calculate output digit"
  [input base recurring]
  (mod (Math/abs (apply + (map-indexed (fn [idx num] (* num (pattern recurring (+ base (inc idx))))) input))) 10))

(defn fft
  "flawed frequency transmission"
  [input base phases]
  (if (= phases 0)
    input
    (let [next (map-indexed (fn [idx num] (calculate (drop idx input) (+ idx base) (inc idx))) input)]
      (recur next base (dec phases)))))

(defn day16a
  "find the signal output"
  [filename phases]
  (let [raw (slurp filename)
        ints (map (fn [x] (Integer/parseInt (str x))) raw)]
    (take 8 (fft ints 0 phases))))

(defn day16b
  "find the signal output after repeating input 10000 times"
  [raw phases]
  (let [extended (apply str (repeat 10000 raw))
        base (offset raw)
        in (map (fn [x] (Integer/parseInt (str x))) (last (split-at base extended)))]
    (loop [input in acc [] phase phases]
      (if (= 0 phase)
        (take 8 input)
        (if (empty? input)
          (recur acc [] (dec phase))
          (recur (rest input) (conj acc (mod (reduce + 0 input) 10)) phase))))))