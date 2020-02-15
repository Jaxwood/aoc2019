(ns intcode.core)

(defn parse
  "parse instructions as a raw comma delimited string
   and puts it into a vector"
  [filename]
    (def raw (slurp filename))
    (def firstLine (first (clojure.string/split raw #"\r\n")))
    (def sequences (map #(Long/valueOf %1) (clojure.string/split firstLine #",")))
    (into [] sequences))

(defn run
  ""
  [instructions]
  0)