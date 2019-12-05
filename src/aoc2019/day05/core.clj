(ns aoc2019.day05.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn opcode
  "1: adds together positions of parameters 1, 2 and stores at parameter 3
   2: multiplies together positions of parameters 1, 2 and stores at parameter 3
   3: takes input and saves to position of parameter 1
   4: output value at parameter 1"
  [oc]
  (case oc 1 ()
        2 ()
        3 ()
        4 ()
        (throw (Exception. "Unsupported opcode"))))

(defn parametermode
  "0: position mode - value stored at position
   1: immediate mode - parameter interpreted as value"
  [pm]
  0)

(defn pointer
  "moves pointer based on instruction"
  [in]
  (case in
    1 4
    2 4
    3 2
    4 2))

(defn instruction
  "parse the instruction
  opcode: two rightmost digits
  parametermode: 3 leftmost digits"
  [inst]
  0)
