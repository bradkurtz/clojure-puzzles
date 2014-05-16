;; April 8, 2014 puzzle

;; Assign the numbers 1 through 10 to the billiard balls below (arranged in 4 rows, 4 balls, then 3, then 2, then 1)
;; according to these rules:
;; A: In the 3-ball row, numbers are in ascending order from left to right with each successive ball 3 higher than the one before it
;; B: The sum of the 3-ball row = the sum of the 2-ball row
;; C: The sum of the 4 balls on the left edge = the sum of the balls on the right edge = 20
;; D: The total sum of the balls in the 1-ball and 2-ball rows = 20
;; E: Adjacent numbers in the 4-ball row are more than 1 apart


(ns billiards.core
  (require [clojure.math.combinatorics :as combo]))

(def perms (combo/permutations (range 1 11)))

(defn filter-by-index [coll indexes]
  (keep-indexed #(when ((set indexes) %1) %2)
                (vec coll)))

(defn get-edges
  [rows]
  "Returns a map of vectors with the edges of the rows"
  (hash-map :left (filter-by-index rows [0 4 7 9])
            :right (filter-by-index rows [3 6 8 9])))

(defn three-row-asc?
  [row]
  "A: Verifies that the 3-ball row is ascending by 3"
  (= (+ (row 0) 6) (+ (row 1) 3) (row 2)))

(defn sum-three-eq-sum-two?
  [row3 row2]
  "B: Verifies that the sum of the 3-ball row equals the sum of the 2-ball row"
  (= (reduce + row3) (reduce + row2)))

(defn sum-edges-eq?
  [left right]
  "C: Verifies that the sum of the edges both equal 20"
  (= 20 (reduce + left) (reduce + right)))


(defn sum-one-eq-sum-two?
  [row1 row2]
  "D: Verifies that the sum of the 1-ball row and the sum of the 2-ball row both equal 20"
  (= 20 (reduce + row1) (reduce + row2)))

(defn adj-balls-in-four-gt-one-apart?
  [row]
  "E: Verifies that adjacent balls in the 4-ball row are more than 1 apart"
  (and (< 1 (- (row 1) (row 0))) (< 1 (- (row 2) (row 1))) (< 1 (- (row 3) (row 2)))))

(defn correct-arrangement?
  [balls]
  "Solves the puzzle given a permutation of the ball arrangement (balls)"
  (let [ball4-row (vec (take 4 balls))
        ball3-row (vec (take 3 (drop 4 balls)))
        ball2-row (vec (take 2 (drop 7 balls)))
        ball1-row (vec (take-last 1 balls))
        edges (get-edges balls)]
      (and (three-row-asc? ball3-row)
           (sum-three-eq-sum-two? ball3-row ball2-row)
           (sum-edges-eq? (edges :left) (edges :right))
           (sum-one-eq-sum-two? ball1-row ball2-row)
           (adj-balls-in-four-gt-one-apart? ball4-row))))

(defn solve
  [balls]
  "Recursively checks each permutation and prints out the solution if it exists"
  (cond
   (empty? balls) (println "No solution found")
   (correct-arrangement? (first balls)) (println balls)
   :else (recur (rest balls))))

(defn -main
  []
  "Solves the puzzle"
  (solve perms))

(-main)
