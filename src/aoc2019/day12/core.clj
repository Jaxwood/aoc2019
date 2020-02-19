(ns aoc2019.day12.core)

(defn tick
  "simulate motion of the moons"
  [moons velocity]
  [moons velocity])

(defn abs
  "get the absolute value"
  [value]
  (map #(Math/abs %1) value))

(defn calculate
  "calculate the energy"
  [moons velocity]
  (apply + (map *
                (map #(apply + %1) (map abs moons))
                (map #(apply + %1) (map abs velocity)))))

(defn day12a
  "find the energy after steps"
  [moons velocity steps]
  (if (= steps 0)
    (calculate moons velocity)
    (let [[a b] (tick moons velocity)]
      (recur a b (dec steps)))))