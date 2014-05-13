(ns puzzle-ages
  (:require [clojure.string :as string]))

(defn reverse-age [age]
  (Integer/parseInt (string/reverse (str age))))

(defn double? [dad-age]
  (let [son-age (reverse-age dad-age)]
    (=
     (* 2 (- son-age 9))
     (- dad-age 9))))

(filter (fn [x]
          (if (double? x)
            (println (str "Father: " x ", son: " (reverse-age x)))))
          (range 10 99))

