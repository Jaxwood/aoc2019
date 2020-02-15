(ns intcode.core)

(defn parse
  "parse instructions as a raw comma delimited string
   and puts it into a vector"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Long/valueOf %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

(defn readInstruction
  "Read the next instruction"
  [memory address]
  (let [opcode (get memory address)]
    (case opcode
      1 {:opcode opcode :parameters (conj [] (get memory (+ address 1)) (get memory (+ address 2)) (get memory (+ address 3)))}
      2 {:opcode opcode :parameters (conj [] (get memory (+ address 1)) (get memory (+ address 2)) (get memory (+ address 3)))}
      99 {:opcode opcode :parameters []})))

(defn add
  "adds two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (update memory c (constantly (+ (get memory a) (get memory b))))))

(defn multiply
  "multiplies two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (update memory c (constantly (* (get memory a) (get memory b))))))

(defn run
  "Run the program"
  [memory address]
  (let [instruction (readInstruction memory address)]
    (case (:opcode instruction)
      1 (recur (add memory instruction) (+ address 4))
      2 (recur (multiply memory instruction) (+ address 4))
      99 memory)))