(ns aoc2019.day09.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn lookup
  "loop based on parameter mode"
  [instruction mode val base]
  (case mode
    0 (get instruction val 0)
    1 val
    2 (get instruction (+ base val) 0)))

(defn digits
  "extract digits and append leading zeros
   1234 becomes [0 1 2 3 4]"
  [candidate acc]
  (if (= candidate 0)
    (if (= (count acc) 5)
      acc
      (into (vec (take (- 5 (count acc)) (repeat 0))) acc))
    (let [digit (mod candidate 10)]
      (recur (int (/ candidate 10)) (cons digit acc)))))

(defn pointer
  "moves pointer based on instruction"
  [op]
  (case op
    1 4
    2 4
    3 2
    4 2
    5 3
    6 3
    7 4
    8 4
    9 2
    0 op))

(defn print-mode
  ""
  [mode]
  (case mode
    0 "position mode for"
    1 "immediate mode for"
    2 "relative mode for"))

(defn print-instruction
  "prints the instruction"
  [op c b a fst snd thd]
  (case op
    1 (println "adding" (print-mode c) fst "and" (print-mode b) snd "into" (print-mode a) thd)
    2 (println "multiplying" c "and" c "into" a)
    3 (println "input" c)
    4 (println "output" c)
    5 (println "jump-if-true" c b)
    6 (println "jump-if-false" c b)
    7 (println "less than" c b a)
    8 (println "equals" c b a)
    9 (println "relative base" c)
    (println "unknown" op)))

;; The memory structure used
(def memoryStructure {:program []
                      :pointer 0
                      :signals []
                      :output 0
                      :base 0
                      :done false})

(defn safe-update
  ""
  [instruction idx func]
  (if (>= idx (count instruction))
    (update (vec (concat instruction (take (- idx (count instruction)) (repeat 0)))) idx func)
    (update instruction idx func)))

(defn opcode
  "Exectute instruction"
  [memory]
  (let [instruction (:program memory)
        signals (:signals memory)
        instructionPointer (:pointer memory)
        base (:base memory)
        [a b c _ op] (digits (get instruction instructionPointer) [])
        updatedPointer (+ instructionPointer (pointer op))
        fst (get instruction (+ instructionPointer 1))
        snd (get instruction (+ instructionPointer 2))
        thd (get instruction (+ instructionPointer 3))]
    ;; (print-instruction op c b a fst snd thd)
    (case op
      1 (opcode {:program (safe-update instruction thd (constantly (+ (lookup instruction b snd base) (lookup instruction c fst base))))
                 :pointer updatedPointer
                 :signals signals
                 :base base})
      2 (opcode {:program (safe-update instruction thd (constantly (* (lookup instruction b snd base) (lookup instruction c fst base))))
                 :pointer updatedPointer
                 :signals signals
                 :base base})
      3 (if (empty? signals)
          {:done false
           :pointer instructionPointer
           :output 0
           :program instruction
           :signals signals
           :base base}
          (opcode {:program (safe-update instruction fst (constantly (first signals)))
                   :pointer updatedPointer
                   :signals (rest signals)
                   :base base}))
      4 (let [val (lookup instruction c fst base)]
          {:done false
           :pointer updatedPointer
           :output val
           :program instruction
           :signals signals
           :base base})
      5 (if (= 0 (lookup instruction c fst base))
          (opcode {:program instruction
                   :pointer updatedPointer
                   :signals signals
                   :base base})
          (opcode {:program instruction
                   :pointer (lookup instruction b snd base)
                   :signals signals
                   :base base}))
      6 (if (= 0 (lookup instruction c fst base))
          (opcode {:program instruction
                   :pointer (lookup instruction b snd base)
                   :signals signals
                   :base base})
          (opcode {:program instruction
                   :pointer updatedPointer
                   :signals signals
                   :base base}))
      7 (if (< (lookup instruction c fst base) (lookup instruction b snd base))
          (opcode {:program (safe-update instruction thd (constantly 1))
                   :pointer updatedPointer
                   :signals signals
                   :base base})
          (opcode {:program (safe-update instruction thd (constantly 0))
                   :pointer updatedPointer
                   :signals signals
                   :base base}))
      8 (if (= (lookup instruction c fst base) (lookup instruction b snd base))
          (opcode {:program (safe-update instruction thd (constantly 1))
                   :pointer updatedPointer
                   :signals signals
                   :base base})
          (opcode {:program (safe-update instruction thd (constantly 0))
                   :pointer updatedPointer
                   :signals signals
                   :base base}))
      9 (let [updatedBase (+ (lookup instruction c fst base) base)]
          (opcode {:program instruction
                   :pointer updatedPointer
                   :signals signals
                   :base updatedBase}))
      0 {:done true})))

(defn runner
  ""
  [program acc]
  (let [state (opcode program)]
    (if (= true (:done state))
      acc
      (recur state (conj acc (:output state))))))

(defn day09a
  ""
  [filename]
  0)