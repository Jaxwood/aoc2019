(ns aoc2019.day05.core)

(defn parse
  "Read from input file"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Integer/parseInt %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn lookup
  ""
  [arr mode val]
  (if (= mode 1)
    val
    (get arr val)))

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
  [[a b c _ op] arr idx default]
  (case op
    1 (let [fst (get arr (+ idx 1))
            snd (get arr (+ idx 2))
            thd (get arr (+ idx 3))]
        (update arr thd (constantly (+ (lookup arr b snd) (lookup arr c fst)))))
    2 (let [fst (get arr (+ idx 1))
            snd (get arr (+ idx 2))
            thd (get arr (+ idx 3))]
        (update arr thd (constantly (* (lookup arr b snd) (lookup arr c fst)))))
    3 (update arr (get arr (+ idx 1)) (constantly default))
    4 (lookup arr c (get arr (+ idx 1)))
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
  [[a b c pm op]]
  (case op
    1 4
    2 4
    3 2
    4 2
    (throw (Exception. "Unsupported opcode"))))

(defn day05a
  ""
  [instructions idx]
  (let [inst (digits (get instructions idx) [])
        op (opcode inst instructions idx 1)
        newIdx (+ idx (pointer inst))]
    (if (= 4 (last inst))
      op
      (recur op newIdx))))
