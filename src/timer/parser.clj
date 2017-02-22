(ns timer.parser
  (:require [instaparse.core :as insta]
            [clojure.core]))

(def time-magnification
  {:millisecond 1
   :second 1000
   :minute 60000
   :hour 3600000
   :date 86400000})

(defn- combine
  [{t :left st :now} kw]
  (let [quotient (quot t (time-magnification kw))
        remain (rem t (time-magnification kw))]
    (if (zero? quotient)
      {:left t :now st}
      {:left remain :now (str st quotient (first (name kw)))})
    ))

(defn st [t]
  "Convert millisecond t into time."
  (:now  (reduce
          combine
          {:left t :now ""}
          [:date :hour :minute :second]
          )))

(defn- space-concat
  "Join the strings with separate whitespaces."
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
  "Returns true iff the input is a vector and have at least 2 elements."
  [v]
  (if (vector? v)
    (contains? v 1)
    false))

(defn- calc-ms
  [k v]
  (.intValue (* (Double. v) (k time-magnification)))
)

(defn- calc-ms-v
  "Deal with time gap"
  [v]
  (if (contain-value? v)
    (calc-ms (v 0) ((v 1) 1))
    0))

(defn parse
  "Parse the input args into millisecond, the return value is
  [result, errmsg]."
  [s]
  (let [ast (time-parser s)]
    (if (= (first ast) :time-gap)
      [(reduce + (map calc-ms-v ast)) nil]
      [nil (str "Wrong arguments: " s "\n" (apply str ast))])
    ))
