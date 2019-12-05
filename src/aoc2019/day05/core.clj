(ns aoc2019.day05.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn opcode
  "op: 1: adds together positions of parameters 1, 2 and stores at parameter 3
       2: multiplies together positions of parameters 1, 2 and stores at parameter 3
       3: takes input and saves to position of parameter 1
       4: output value at parameter 1
   pm: 0: position mode
       1: immediate mode
   a:     parameter 3
   b:     parameter 2
   c:     parameter 1"
  [[a b c pm op] arr]
  (case op
        1 ()
        2 ()
        3 ()
        4 ()
        (throw (Exception. "Unsupported opcode"))))

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
