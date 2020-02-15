(ns intcode.core)

(defn parse
  "parse instructions as a raw comma delimited string
   and puts it into a vector"
  [filename]
  (def raw (slurp filename))
  (def firstLine (first (clojure.string/split raw #"\r\n")))
  (def sequences (map #(Long/valueOf %1) (clojure.string/split firstLine #",")))
  (into [] sequences))

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

(defn parameter-mode
  "Based on the parameter mode for the specific parameter it will
   return the address to read from"
  [memory address mode]
  (case mode
    0 (get memory (get memory address))
    1 (get memory address)))

(defn read-instruction
  "Read the next instruction"
  [memory address]
  (let [[pm3 pm2 pm1 tens ones] (digits (get memory address) [])
        opcode (+ ones (* 10 tens))]
    (case opcode
      1 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1)
                           (parameter-mode memory (+ address 2) pm2)
                           (get memory (+ address 3)))}
      2 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1)
                           (parameter-mode memory (+ address 2) pm2)
                           (get memory (+ address 3)))}
      99 {:opcode opcode
          :parameters []})))

(defn add
  "adds two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (update memory c
            (constantly (+ a b)))))

(defn multiply
  "multiplies two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (update memory c
            (constantly (* a b)))))

(defn run
  "Run the program"
  [memory address]
  (let [instruction (read-instruction memory address)]
    (case (:opcode instruction)
      1 (recur (add memory instruction) (+ address 4))
      2 (recur (multiply memory instruction) (+ address 4))
      99 memory)))