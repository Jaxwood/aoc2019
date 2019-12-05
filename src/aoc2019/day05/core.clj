(ns aoc2019.day05.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn lookup
  "loop based on parameter mode"
  [arr mode val]
  (if (= mode 1)
    val
    (get arr val)))

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
    (throw (Exception. "Unsupported opcode"))))

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
  [arr idx default acc]
  (let [[a b c _ op] (digits (get arr idx) [])
        newIdx (+ idx (pointer op))
        fst (get arr (+ idx 1))
        snd (get arr (+ idx 2))
        thd (get arr (+ idx 3))]
    (case op
      1 (opcode (update arr thd (constantly (+ (lookup arr b snd) (lookup arr c fst)))) newIdx default acc)
      2 (opcode (update arr thd (constantly (* (lookup arr b snd) (lookup arr c fst)))) newIdx default acc)
      3 (opcode (update arr fst (constantly default)) newIdx default acc)
      4 (let [val (lookup arr c fst)]
          (if (= 0 val)
            (opcode arr newIdx default (cons val acc))
            (cons val acc)))
      5 (if (= 0 (lookup arr c fst))
          (opcode arr newIdx default acc)
          (opcode arr (lookup arr b snd) default acc))
      6 (if (= 0 (lookup arr c fst))
          (opcode arr (lookup arr b snd) default acc)
          (opcode arr newIdx default acc))
      7 (if (< (lookup arr c fst) (lookup arr b snd))
          (opcode (update arr thd (constantly 1)) newIdx default acc)
          (opcode (update arr thd (constantly 0)) newIdx default acc))
      8 (if (= (lookup arr c fst) (lookup arr b snd))
          (opcode (update arr thd (constantly 1)) newIdx default acc)
          (opcode (update arr thd (constantly 0)) newIdx default acc))
      (throw (Exception. "Unsupported opcode")))))

(defn day05a
  "find solution for day05a"
  [instructions idx]
  (first (opcode instructions idx 1 [])))

(defn day05b
  "find solution for day05b"
  [instructions idx]
  (first (opcode instructions idx 5 [])))
