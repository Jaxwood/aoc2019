(ns aoc2019.day02.core)

;; update a position to a value
;; (update [1 2 3] 2 (constantly 10))
;; [1 2 10]

;; get from a position
;; (get [1 2 3] 1)
;; 2

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