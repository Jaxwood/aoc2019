(ns aoc2019.day08.core)

(defn metadata
  "Extract imag metadata"
  [image]
  (let [zeros (count (filter #(= 0 %) image))
        ones (count (filter #(= 1 %) image))
        twos (count (filter #(= 2 %) image))]
    {:zero zeros
     :result (* ones twos)}))

(defn day08a
  "Find the best quality layer"
  [filename]
  (let [raw (slurp filename)
        parsed (flatten (vector (map #(Integer/parseInt (str %)) raw)))]
    (:result (apply min-key :zero (map metadata (partition (* 25 6) parsed))))))