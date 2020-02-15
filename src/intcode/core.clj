(ns intcode.core)

(defn parse
  "parse instructions as a raw comma delimited string
   and puts it into a vector"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Long/valueOf %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn add
  "adds two positions and stores in the third position
   returns the updated instruction vector"
  [instructions pointer]
  (let [position1 (get instructions (+ pointer 1))
        position2 (get instructions (+ pointer 2))
        positionToUpdate (get instructions (+ pointer 3))]
    (update instructions positionToUpdate (constantly (+ (get instructions position1) (get instructions position2))))))

(defn multiply
  "multiplies two positions and stores in the third position
   returns the updated instruction vector"
  [instructions pointer]
  (let [position1 (get instructions (+ pointer 1))
        position2 (get instructions (+ pointer 2))
        positionToUpdate (get instructions (+ pointer 3))]
    (update instructions positionToUpdate (constantly (* (get instructions position1) (get instructions position2))))))

(defn run
  ""
  [instructions pointer]
  (let [opcode (get instructions pointer)]
    (case opcode
      1 (recur (add instructions pointer) (+ pointer 4))
      2 (recur (multiply instructions pointer) (+ pointer 4))
      99 instructions)))