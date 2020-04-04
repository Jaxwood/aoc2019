(ns aoc2019.day22.core)

(defn deal
  "deal new stack"
  [cards]
  (vec (reverse cards)))

(defn cut
  "cut the stack"
  [cards n]
  (if (pos? n)
    (into (subvec cards n) (subvec cards 0 n))
    (let [idx (+ (count cards) n)]
      (into (subvec cards idx) (subvec cards 0 idx)))))

(defn increment
  "increment the stack"
  [cards n]
  (let [size (count cards)]
    (loop [idx 1 acc []]
      (if (= idx size)
        (vec (cons (first cards) (mapv second (sort-by first acc))))
        (let [new-idx (* idx n)
              shifted (mod new-idx size)]
          (if (> new-idx size)
            (recur (inc idx) (conj acc [shifted (nth cards idx)]))
            (recur (inc idx) (conj acc [new-idx (nth cards idx)]))))))))


(defn day22a
  "shuffle the cards according to the different techniques"
  [instructions cards]
  (if (empty? instructions)
    cards
    (let [instruction (first instructions)
          name (:name instruction)
          n (:n instruction)]
      (cond
        (= name :deal) (recur (rest instructions) (deal cards))
        (= name :cut) (recur (rest instructions) (cut cards n))
        (= name :increment) (recur (rest instructions) (increment cards n))))))