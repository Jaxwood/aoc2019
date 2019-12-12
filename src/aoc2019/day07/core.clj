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

(defn opcode
  ""
  [arr idx signals]
  (let [[a b c _ op] (digits (get arr idx) [])
        newIdx (+ idx (pointer op))
        fst (get arr (+ idx 1))
        snd (get arr (+ idx 2))
        thd (get arr (+ idx 3))]
    ;;(print-instruction op)
    (case op
      1 (opcode (update arr thd (constantly (+ (lookup arr b snd) (lookup arr c fst)))) newIdx signals)
      2 (opcode (update arr thd (constantly (* (lookup arr b snd) (lookup arr c fst)))) newIdx signals)
      3 (opcode (update arr fst (constantly (first signals))) newIdx (drop 1 signals))
      4 (let [val (lookup arr c fst)]
          (if (= 0 val)
            (opcode arr newIdx signals)
            val))
      5 (if (= 0 (lookup arr c fst))
          (opcode arr newIdx signals)
          (opcode arr (lookup arr b snd) signals))
      6 (if (= 0 (lookup arr c fst))
          (opcode arr (lookup arr b snd) signals)
          (opcode arr newIdx signals))
      7 (if (< (lookup arr c fst) (lookup arr b snd))
          (opcode (update arr thd (constantly 1)) newIdx signals)
          (opcode (update arr thd (constantly 0)) newIdx signals))
      8 (if (= (lookup arr c fst) (lookup arr b snd))
          (opcode (update arr thd (constantly 1)) newIdx signals)
          (opcode (update arr thd (constantly 0)) newIdx signals))
      9 0)))

(defn thrusters
  "find highest signal that can be sent to the thrusters"
  [program settings idx]
  (let [a (opcode program idx [(get settings 0) 0])
        b (opcode program idx [(get settings 1) a])
        c (opcode program idx [(get settings 2) b])
        d (opcode program idx [(get settings 3) c])
        e (opcode program idx [(get settings 4) d])]
    e))

(defn day07a
  "find the highest signal"
  [program perms acc]
  (if (empty? perms)
    (apply max acc)
    (recur program (rest perms) (conj acc (thrusters program (first perms) 0)))))