(ns aoc2019.day02.core)

(defn getOperation
  "get the operation based on val"
  [val op1 op2]
  (if (= val 1)
    (+ op1 op2)
    (* op1 op2)))

(defn intCode
  "calculates int codes for a sequence"
  [sequence idx]
  (let [val (get sequence idx)]
    (if (= val 99)
      sequence
      (let [opcode1 (get sequence (get sequence (+ idx 1)))
            opcode2 (get sequence (get sequence (+ idx 2)))
            newIdx (get sequence (+ idx 3))]
        (intCode (update sequence newIdx (constantly (getOperation val opcode1 opcode2))) (+ idx 4))))))

(defn modifyParameters
  "Updates noun and verb"
  [seq noun verb]
  (def updatedSeq (update (update seq 1 (constantly noun)) 2 (constantly verb)))
  updatedSeq)

(defn day02a
  "Calcaulate int codes for puzzle input"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (first (intCode (modifyParameters (into [] sequences) 12 2) 0)))