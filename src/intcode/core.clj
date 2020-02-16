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
      3 {:opcode opcode
         :parameters (conj []
                           (get memory (+ address 1)))}
      4 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1))}
      5 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1)
                           (parameter-mode memory (+ address 2) pm2))}
      6 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1)
                           (parameter-mode memory (+ address 2) pm2))}
      7 {:opcode opcode
         :parameters (conj []
                           (parameter-mode memory (+ address 1) pm1)
                           (parameter-mode memory (+ address 2) pm2)
                           (get memory (+ address 3)))}
      8 {:opcode opcode
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

(defn in
  "takes a single integer as input and saves it to the position
  given by its only parameter. Returns the updated memory"
  [memory instruction input]
  (let [[a] (:parameters instruction)]
    (update memory a
            (constantly input))))

(defn out
  "outputs the value of its only parameter."
  [memory instruction]
  (let [[a] (:parameters instruction)]
    a))

(defn jump-if-true
  "if the first parameter is non-zero, it sets the instruction pointer
   to the value from the second parameter. Otherwise, it does nothing"
  [instruction address]
  (let [[a b] (:parameters instruction)]
    (if (= 0 a)
      (+ address 3)
      b)))

(defn jump-if-false
  "if the first parameter is zero, it sets the instruction pointer to the
   value from the second parameter. Otherwise, it does nothing"
  [instruction address]
  (let [[a b] (:parameters instruction)]
    (if (= 0 a)
      b
      (+ address 3))))

(defn less-than
  "if the first parameter is less than the second parameter, it stores 1 in
   the position given by the third parameter. Otherwise, it stores 0"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (if (< a b)
      (update memory c (constantly 1))
      (update memory c (constantly 0)))))

(defn equals
  "if the first parameter is equal to the second parameter, it stores 1 in
   the position given by the third parameter. Otherwise, it stores 0"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (if (= a b)
      (update memory c (constantly 1))
      (update memory c (constantly 0)))))

(defn run
  "Run the program"
  [memory address input]
  (let [instruction (read-instruction memory address)]
    (case (:opcode instruction)
      1 (recur (add memory instruction) (+ address 4) input)
      2 (recur (multiply memory instruction) (+ address 4) input)
      3 (recur (in memory instruction input) (+ address 2) input)
      4 (let [output (out memory instruction)]
          (if (= 0 output)
            (recur memory (+ address 2) input)
            output))
      5 (recur memory (jump-if-true instruction address) input)
      6 (recur memory (jump-if-false instruction address)  input)
      7 (recur (less-than memory instruction) (+ address 4) input)
      8 (recur (equals memory instruction) (+ address 4) input)
      99 memory)))