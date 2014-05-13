(ns billiards.core
  (require [clojure.math.combinatorics :as combo]))

(def perms (combo/permutations (range 1 11)))

(defn three-row-asc?
  [row]
  "Verifies that the 3-ball row is ascending by 3"
  (= (+ (row 0) 6) (+ (row 1) 3) (row 2)))

(three-row-asc? [1 4 7])

(defn sum-three-eq-sum-two?
  [row3 row2]
  "Verifies that the sum of the 3-ball row equals the sum of the 2-ball row"
  (= (reduce + row3) (reduce + row2)))

(sum-three-eq-sum-two? [1 2 3] [4 2])

(defn filter-by-index [coll indexes]
  (keep-indexed #(when ((set indexes) %1) %2)
                (vec coll)))

(defn get-edges
  [rows]
  "Returns a map of vectors with the edges of the rows"
  (hash-map :left (filter-by-index rows [0 4 7 9])
            :right (filter-by-index rows [3 6 8 9])))

(get-edges [1 2 3 4 5 6 7 8 9 10])


(defn sum-edges-eq?
  [left right]
  "Verifies that the sum of the edges both equal 20"
  (= 20 (reduce + left) (reduce + right)))


(defn sum-one-eq-sum-two?
  [row1 row2]
  "Verifies that the sum of the 1-ball row and the sum of the 2-ball row both equal 20"
  (= 20 (row1) (row2)))

(defn adj-balls-in-four-gt-one-apart?
  [row]
  "Verifies that adjacent balls in the 4-ball row are more than 1 apart"
  (and (< 1 (- (row 1) (row 0))) (< 1 (- (row 2) (row 1))) (< 1 (- (row 3) (row 2)))))

(adj-balls-in-four-gt-one-apart? [1 3 5 7])
