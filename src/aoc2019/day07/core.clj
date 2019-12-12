(ns aoc2019.day07.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn lookup
  "loop based on parameter mode"
  [instruction mode val]
  (if (= mode 1)
    val
    (get instruction val)))

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
    9 op))

(defn print-instruction
  "prints the instruction"
  [op]
  (case op
    1 (println "add")
    2 (println "multiply")
    3 (println "input")
    4 (println "output")
    5 (println "jump-if-true")
    6 (println "jump-if-false")
    7 (println "less than")
    8 (println "equals")
    (println "unknown" op)))

(def memoryStructure {:program []
                      :pointer 0
                      :signals []
                      :output 0
                      :done false})

(defn opcode
  ""
  [memory]
  (if (= true (:done memory))
    memory
    (let [instruction (get memory :program)
          signals (get memory :signals)
          instructionPointer (get memory :pointer)
          [a b c _ op] (digits (get instruction instructionPointer) [])
          updatedPointer (+ instructionPointer (pointer op))
          fst (get instruction (+ instructionPointer 1))
          snd (get instruction (+ instructionPointer 2))
          thd (get instruction (+ instructionPointer 3))]
    ;; (print-instruction op)
      (case op
        1 (opcode {:program (update instruction thd (constantly (+ (lookup instruction b snd) (lookup instruction c fst)))) :pointer updatedPointer :signals signals})
        2 (opcode {:program (update instruction thd (constantly (* (lookup instruction b snd) (lookup instruction c fst)))) :pointer updatedPointer :signals signals})
        3 (if (empty? signals)
            {:done false :pointer instructionPointer :output 0 :program instruction :signals signals}
            (opcode {:program (update instruction fst (constantly (first signals))) :pointer updatedPointer :signals (rest signals)}))
        4 (let [val (lookup instruction c fst)]
            (if (= 0 val)
              (opcode {:program instruction :pointer updatedPointer :signals signals})
              {:done false :pointer updatedPointer :output val :program instruction :signals signals}))
        5 (if (= 0 (lookup instruction c fst))
            (opcode {:program instruction :pointer updatedPointer :signals signals})
            (opcode {:program instruction :pointer (lookup instruction b snd) :signals signals}))
        6 (if (= 0 (lookup instruction c fst))
            (opcode {:program instruction :pointer (lookup instruction b snd) :signals signals})
            (opcode {:program instruction :pointer updatedPointer :signals signals}))
        7 (if (< (lookup instruction c fst) (lookup instruction b snd))
            (opcode {:program (update instruction thd (constantly 1)) :pointer updatedPointer :signals signals})
            (opcode {:program (update instruction thd (constantly 0)) :pointer updatedPointer :signals signals}))
        8 (if (= (lookup instruction c fst) (lookup instruction b snd))
            (opcode {:program (update instruction thd (constantly 1)) :pointer updatedPointer :signals signals})
            (opcode {:program (update instruction thd (constantly 0)) :pointer updatedPointer :signals signals}))
        9 {:done true :program [] :pointer 0 :signals []  :output 0}))))

(defn thrusters
  "find highest signal that can be sent to the thrusters"
  [program settings pointer]
  (let [a (opcode {:program program :pointer pointer :signals [(get settings 0) 0]})
        b (opcode {:program program :pointer pointer :signals [(get settings 1) (get a :output)]})
        c (opcode {:program program :pointer pointer :signals [(get settings 2) (get b :output)]})
        d (opcode {:program program :pointer pointer :signals [(get settings 3) (get c :output)]})
        e (opcode {:program program :pointer pointer :signals [(get settings 4) (get d :output)]})]
    (if (= true (:done e))
      0
      [a b c d e])))

(defn feedback
  ""
  [[a b c d e]]
  (let [a' (opcode {:program (:program a) :pointer (:pointer a) :signals (vec (conj (:signals a) (:output e))) :output (:output a)})
        b' (opcode {:program (:program b) :pointer (:pointer b) :signals (vec (conj (:signals b) (:output a'))) :output (:output b)})
        c' (opcode {:program (:program c) :pointer (:pointer c) :signals (vec (conj (:signals c) (:output b'))) :output (:output c)})
        d' (opcode {:program (:program d) :pointer (:pointer d) :signals (vec (conj (:signals d) (:output c'))) :output (:output d)})
        e' (opcode {:program (:program e) :pointer (:pointer e) :signals (vec (conj (:signals e) (:output d'))) :output (:output e)})]
    (if (= true (:done e'))
      (:output e)
      (recur [a' b' c' d' e']))))

(defn day07a
  "find the highest signal"
  [program perms acc]
  (if (empty? perms)
    (apply max acc)
    (recur program (rest perms) (conj acc (get (last (thrusters program (first perms) 0)) :output)))))