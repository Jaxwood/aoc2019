(ns aoc2019.day02.core)

(defn getOperation
  "Get the operation based on val"
  [val op1 op2]
  (if (= val 1)
    (+ op1 op2)
    (* op1 op2)))

(defn intCode
  "Calculates int codes for a sequence"
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

(defn incVerb
  "Increment verb"
  [verb]
  (if (= verb 99)
    0
    (inc verb)))

(defn incNoun
  "Increment noun"
  [noun verb]
  (if (= verb 99)
    (inc noun)
    noun))

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn day02a
  "Calculate int codes for puzzle input"
  [sequences noun verb]
  (first (intCode (modifyParameters sequences noun verb) 0)))

(defn day02b
  "Calculate int codes for puzzle input"
  [sequences noun verb target]
  (let [result (day02a sequences noun verb)]
    (if (= result target)
      (+ (* 100 noun) verb)
      (recur sequences (incNoun noun verb) (incVerb verb) target))))