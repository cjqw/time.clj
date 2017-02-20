(ns timer.io
  (:require [timer.parser :as parser])
  (:import [java.io Writer]))

(defn print-start-message []
  "Print start massage"
  (println "The timer start to work."))

(defn print-exit-message [gap-time]
  "Print exit massage"
  (println "Time Over"))

(defn print-hint-message [t]
  "Print hint message of count up."
  (print (parser/st t) " has gone..\r")
  (flush)
)
