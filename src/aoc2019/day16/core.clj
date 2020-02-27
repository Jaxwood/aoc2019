(ns aoc2019.day16.core)

(def base '(0 1 0 -1))

(defn repeating
  "repeat the base pattern"
  [times size]
  (take size
        (drop 1
              (apply concat
                     (repeat
                      (+ 1 (int (Math/ceil (/ size (* times 4)))))
                      (mapcat (partial repeat times) base))))))

(defn string->numbers
  "parse input string to numbers"
  [numberstring]
  (map (fn [x] (Integer/parseInt (str x))) numberstring))

(defn fft
  "flawed frequency transmission algorithm"
  [times input acc]
  (if (> times (count input))
    acc
    (let [pattern (repeating times (count input))
          val (mod (Math/abs (reduce + 0 (map (fn [a b] (* a b)) input pattern))) 10)]
      (recur (inc times) input (str acc val)))))

(defn day16a
  "clean up the signals"
  [input phases]
  (if (= 0 phases)
    input
    (recur (fft 1 (string->numbers input) "") (dec phases))))