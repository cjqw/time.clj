(ns timer.parser
  (:require [instaparse.core :as insta]
            [clojure.core]))

(def time-magnification
  {:millisecond 1
   :second 1000
   :minute 60000
   :hour 3600000
   :date 86400000})

(defn- space-concat
  [& s]
  (reduce #(str %1 " " %2) s))

(def time-parser
  (insta/parser
   (space-concat
    "time-gap = date hour minute second millisecond"
    "date = number <'d'> | eps"
    "hour = number <'h'> | eps"
    "minute = number <'m'> | eps"
    "second = number <'s'> | eps"
    "millisecond = number <'ms'> | eps"
    "number = #\"\\d+(.\\d+)?\"")
   :auto-whitespace :standard))

(defn- contain-value?
  [v]
  (if (vector? v)
    (contains? v 1)
    nil))

(defn- calc-ms
  [k v]
  (java.lang.Math/ceil (* (Double. v) (k time-magnification)))
)

(defn- calc-ms-v
  [v]
  (if (contain-value? v)
    (calc-ms (v 0) ((v 1) 1))
    0))

(defn parse
  [s]
  (let [ast (time-parser s)]
    (reduce + (map calc-ms-v ast))))
