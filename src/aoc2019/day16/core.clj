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