(ns timer.core
  (:require [timer.parser :as parser]
            [timer.io :as io])
  (:gen-class))

(defn- now []
  "Get current time, in millisecond"
  (System/currentTimeMillis))

(def default-count-down-gap 1000)
(def default-count-up-gap 1000)
(def start-time (now))

(defn- time-gap
  "Parse the input into millisecond"
  ([] default-count-down-gap)
  ([& gap]
   (reduce + (map parser/parse gap))))

(defn- count-down
  "Sleep for a while and then print exit message"
  [s]
  (let [start-time (now)
        gap-time (apply time-gap s)]
    (Thread/sleep gap-time)
    (io/print-exit-message gap-time)))

(defn- count-up
  "When no args passed, the timer works as a stop watch. Print a hint message every default-count-up-gap"
  []
  (Thread/sleep default-count-up-gap)
  (io/print-hint-message (- (now) start-time))
  (count-up)
  )

(defn -main
  "The entrance of the timer. If no args passed to the timer, it will work as a count-up, otherwise it will work as a count-down."
  [& args]
  (io/print-start-message)
  (if (zero? (count args))
    (count-up)
    (count-down args)))
