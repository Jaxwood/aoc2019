(ns aoc2019.day08.core)

(def black 0)
(def white 1)
(def transparent 2)

(defn metadata
  "Extract image metadata"
  [image]
  (let [zeros (count (filter #(= black %) image))
        ones (count (filter #(= white %) image))
        twos (count (filter #(= transparent %) image))]
    {:zero zeros
     :result (* ones twos)}))

(defn transpose
  ""
  [m]
  (apply mapv vector m))

(defn transparent?
  [color]
  (= transparent color))

(defn day08a
  "Find the best quality layer"
  [filename]
  (let [raw (slurp filename)
        parsed (flatten (vector (map #(Integer/parseInt (str %)) raw)))]
    (:result (apply min-key :zero (map metadata (partition (* 25 6) parsed))))))

(defn day08b
  "Find the hidden message"
  [filename]
  [filename]
  (let [raw (slurp filename)
        parsed (flatten (vector (map #(Integer/parseInt (str %)) raw)))
        layers (transpose (partition (* 25 6) parsed))]
    (partition 25 (map #(first (drop-while transparent? %)) layers))))