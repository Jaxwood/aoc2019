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

(def repeatmem (memoize repeating))

(defn number->digits
  "convert number into digits"
  [num]
  (loop [n num res []]
    (if (zero? n)
      res
      (recur (quot n 10) (cons (mod n 10) res)))))

(defn abs
  "get the absolute value"
  [val]
  (if (pos? val)
    val
    (* -1 val)))

(defn fft
  "flawed frequency transmission algorithm"
  [times input acc]
  (let [size (count input)]
    (if (> times size)
      acc
      (let [pattern (repeatmem times size)
            val (mod (abs (reduce + 0 (map (fn [a b] (* a b)) input pattern))) 10)]
        (recur (inc times) input (conj acc val))))))

(defn day16a
  "clean up the signals"
  [input phases]
  (if (= 0 phases)
    input
    (recur (fft 1 input []) (dec phases))))