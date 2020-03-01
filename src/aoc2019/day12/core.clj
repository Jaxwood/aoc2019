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
  (Math/abs ^Integer value))

(defn mabs
  "get the absolute value"
  [value]
  (map #(abs %1) value))

(defn calculate
  "calculate the energy"
  [moons velocity]
  (apply + (map *
                (map #(apply + %1) (map mabs moons))
                (map #(apply + %1) (map mabs velocity)))))

(defn gcd
  "greatest common divisor"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm
  "least common multiple"
  [a b]
  (/ (* a b) (gcd a b)))

(defn find-repeating-step
  "find the steps required for a repeat"
  [[x1 x2 x3 x4 v1 v2 v3 v4]
   steps
   memory]
  (let [xc1 (+ (modifier x1 x2) (modifier x1 x3) (modifier x1 x4))
        xc2 (+ (modifier x2 x1) (modifier x2 x3) (modifier x2 x4))
        xc3 (+ (modifier x3 x1) (modifier x3 x2) (modifier x3 x4))
        xc4 (+ (modifier x4 x1) (modifier x4 x2) (modifier x4 x3))
        key [(+ x1 xc1 v1) (+ x2 xc2 v2) (+ x3 xc3 v3) (+ x4 xc4 v4) (+ v1 xc1) (+ v2 xc2) (+ v3 xc3) (+ v4 xc4)]]
    (if (contains? memory key)
      (if (= (get memory key) 0)
        steps
        (recur key (inc steps) (assoc memory key (inc (get memory key)))))
      (recur key (inc steps) (assoc memory key 0)))))

(defn day12a
  "find the energy after steps"
  [moons velocity steps]
  (if (= steps 0)
    (calculate moons velocity)
    (let [[a b] (tick moons velocity)]
      (recur a b (dec steps)))))

(defn day12b
  "find the steps for a repeat"
  [[x y z]]
  (let [x-repeat (find-repeating-step (concat x [0 0 0 0]) 0 {})
        y-repeat (find-repeating-step (concat y [0 0 0 0]) 0 {})
        z-repeat (find-repeating-step (concat z [0 0 0 0]) 0 {})]
    (lcm (lcm x-repeat y-repeat) z-repeat)))