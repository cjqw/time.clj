(ns timer.io
  (:require [timer.parser :as parser])
  (:import [java.io Writer]))

(defn print-start-message [start-time]
  "Print start massage"
  (println "The timer start to work"))

(defn print-exit-message [gap-time]
  "Print exit massage"
  (println "Time Over"))

(defn print-hint-message [t]
  "Print hint message of count up."
  (print (parser/st t) " has gone..\r")
  (flush)
  )

(defn- progress-bar [percentage]
  (str "["
       (apply str (take percentage (repeat "=")))
       (apply str (take (- 10 percentage) (repeat " ")))
       "]"))

(defn- print-bar [percentage t]
  (print (str (progress-bar percentage) " "
              (parser/st t) "\r"))
  (flush))

(defn print-progress-bar [start-time gap-time]
  "Print the progress bar asynchoronusly."
  (Thread/sleep 100)
  (let [millisecond (- (System/currentTimeMillis) start-time)
        percentage (quot (* 10 millisecond) gap-time)]
    (if (> 10 percentage)
      (do (print-bar percentage millisecond)
          (print-progress-bar start-time gap-time))
      )))
