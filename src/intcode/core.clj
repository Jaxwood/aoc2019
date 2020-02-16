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

(defn safe-update
  "Updates the instruction set and will increment memory if not enough space"
  [memory address func]
  (if (>= address (count memory))
    (update (vec (concat memory (take (- address (count memory)) (repeat 0)))) address func)
    (update memory address func)))

(defn read-value
  "read the value from the address based on the mode"
  [memory address relative mode]
  (case mode
    0 (get memory (get memory address))
    1 (get memory address)
    2 (get memory (+ relative (get memory address)))))

(defn write-index
  "finds the index to write to"
  [memory address relative mode]
  (case mode
    0 (get memory address)
    1 (get memory address)
    2 (+ relative (get memory address))))

(defn read-instruction
  "Read the next instruction"
  [memory address relative]
  (let [[pm3 pm2 pm1 tens ones] (digits (get memory address) [])
        opcode (+ ones (* 10 tens))]
    (case opcode
      1 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2)
                           (write-index memory (+ address 3) relative pm3))}
      2 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2)
                           (write-index memory (+ address 3) relative pm3))}
      3 {:opcode opcode
         :parameters (conj []
                           (write-index memory (+ address 1) relative pm1))}
      4 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1))}
      5 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2))}
      6 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2))}
      7 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2)
                           (write-index memory (+ address 3) relative pm3))}
      8 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1)
                           (read-value memory (+ address 2) relative pm2)
                           (write-index memory (+ address 3) relative pm3))}
      9 {:opcode opcode
         :parameters (conj []
                           (read-value memory (+ address 1) relative pm1))}
      99 {:opcode opcode
          :parameters []})))

(defn add
  "adds two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (safe-update memory c
                 (constantly (+ a b)))))

(defn multiply
  "multiplies two positions and stores in the third position
   returns the updated memory"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (safe-update memory c
                 (constantly (* a b)))))

(defn in
  "takes a single integer as input and saves it to the position
  given by its only parameter. Returns the updated memory"
  [memory instruction input]
  (let [[a] (:parameters instruction)]
    (safe-update memory a
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
      (safe-update memory c (constantly 1))
      (safe-update memory c (constantly 0)))))

(defn equals
  "if the first parameter is equal to the second parameter, it stores 1 in
   the position given by the third parameter. Otherwise, it stores 0"
  [memory instruction]
  (let [[a b c] (:parameters instruction)]
    (if (= a b)
      (safe-update memory c (constantly 1))
      (safe-update memory c (constantly 0)))))

(defn adjust-relative
  "adjust the relative parameter"
  [instruction relative]
  (let [[a] (:parameters instruction)]
    (+ relative a)))

(defn run
  "Run the program"
  [program]
  (let [memory (:memory program)
        address (:address program)
        relative (:relative program)
        input (:input program)
        instruction (read-instruction memory address relative)]
    (case (:opcode instruction)
      1 (recur {:memory (add memory instruction) :address (+ address 4) :relative relative :input input})
      2 (recur {:memory (multiply memory instruction) :address (+ address 4) :relative relative :input input})
      3 (recur {:memory (in memory instruction input) :address (+ address 2) :relative relative :input input})
      4 (let [output (out memory instruction)]
          (if (= 0 output)
            (recur {:memory memory :address (+ address 2) :relative relative :input input})
            {:memory memory :address address :relative relative :input input :output output}))
      5 (recur {:memory memory :address (jump-if-true instruction address) :relative relative :input input})
      6 (recur {:memory memory :address (jump-if-false instruction address) :relative relative :input input})
      7 (recur {:memory (less-than memory instruction) :address (+ address 4) :relative relative :input input})
      8 (recur {:memory (equals memory instruction) :address (+ address 4) :relative relative :input input})
      9 (recur {:memory memory :address (+ address 2) :relative (adjust-relative instruction relative) :input input})
      99 {:memory memory :address address :relative relative :input input})))