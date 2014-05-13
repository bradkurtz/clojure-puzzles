;; Find a 6 digit number containing no zeros and no repeated digits
;; in which:
;; the 2nd digit is 3 less than the 4th digit
;; the sum of the 1st and 3rd digits is the 5th digit
;; the 5th digit is 5 more than the 4th digit
;; the sum of the 2nd, 3rd and 4th digit is the 6th digit


(ns digit-puzzle.core
  (:gen-class))

(defn extract-digits [number]
  (vec (map #(Character/getNumericValue %)(str number))))

(defn six-digits? [digits]
  (= 6 (count digits)))

(defn no-dups? [digits]
  (= (count (set digits)) (count digits)))

(defn no-zeroes? [digits]
  (nil? (some #{0} digits)))

(defn second-digit [digits]
  (= (digits 1) (- (digits 3) 3)))

(defn sum-first-third [digits]
  (= (+ (digits 0) (digits 2)) (digits 4)))

(defn fifth-digit [digits]
  (= (+ (digits 3) 5) (digits 4)))

(defn sum-second-thru-fourth [digits]
  (= (+ (digits 1) (digits 2) (digits 3))
     (digits 5)))


(defn meets-requirements [number]
  (let [digits (extract-digits number)]
    (cond (not (six-digits? digits)) false
          (not (no-zeroes? digits)) false
          (not (no-dups? digits)) false
          (not (second-digit digits)) false
          (not (sum-first-third digits)) false
          (not (fifth-digit digits)) false
          (not (sum-second-thru-fourth digits)) false
          :else true)))

(defn run-test []
  (filter (fn [x]
            (meets-requirements x))
            (range 111111 999999)))

(run-test)

