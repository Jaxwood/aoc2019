(ns aoc2019.day12.core)

(defn modifier
  "calculates the velocity modifier"
  [a b]
  (cond
    (> a b) -1
    (< a b) 1
    (= a b) 0))

(defn gravity
  "calculate gravity"
  [[io europe ganymede calisto]]
  [(apply + (map (partial modifier io) [europe ganymede calisto]))
   (apply + (map (partial modifier europe) [io ganymede calisto]))
   (apply + (map (partial modifier ganymede) [europe io calisto]))
   (apply + (map (partial modifier calisto) [europe ganymede io]))])

(defn adjust-velocity
  "apply the velocity"
  [[x y z] [vx vy vz]]
  [(+ x vx) (+ y vy) (+ z vz)])

(defn tick
  "simulate motion of the moons"
  [[a b c d] velocity]
  (let [[x y z] (map (comp gravity vector) a b c d)
        new-velocity (map adjust-velocity (map vector x y z) velocity)
        new-moons (map adjust-velocity [a b c d] new-velocity)]
    [new-moons new-velocity]))

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