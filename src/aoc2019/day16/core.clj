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
  [input recurring]
  (mod (Math/abs (apply + (map-indexed (fn [idx num] (* num (pattern recurring (inc idx)))) input))) 10))

(defn fft
  ""
  [input phases]
  (if (= phases 0)
    input
    (recur (map-indexed (fn [idx num] (calculate input (inc idx))) input) (dec phases))))

(defn fft-optimized
  ""
  [input base phases]
  (if (= phases 0)
    input
    (recur (map-indexed (fn [idx num] (calculate input (+ base (inc idx)))) input) base (dec phases))))

(defn day16a
  ""
  [filename phases]
  (let [raw (slurp filename)
        ints (map (fn [x] (Integer/parseInt (str x))) raw)]
    (take 8 (fft ints phases))))

(defn day16b
  ""
  [input phases]
  (let [extended (apply str (repeat 10000 input))
        idx (offset input)
        in (map (fn [x] (Integer/parseInt (str x))) (last (split-at idx extended)))]
    0));;(fft-optimized in idx phases)))