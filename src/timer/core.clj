(ns timer.core
  (:require [timer.parser :as parser]
            [timer.io :as io])
  (:gen-class))

(defn- now []
  "Get current time, in millisecond"
  (System/currentTimeMillis))

(def default-count-down-gap 1000)
(def default-count-up-gap 1000)

(defn- time-gap
  "Parse the input into millisecond"
  ([] default-count-down-gap)
  ([& gap]
   (reduce + (map parser/parse gap))))

(defn- count-down
  "Sleep for a while and then print exit message"
  [s]
  (let [gap-time (apply time-gap s)]
    (future (io/print-progress-bar (now) gap-time))
    (Thread/sleep gap-time)
    (io/print-exit-message gap-time))
  (shutdown-agents)
  )

(defn- count-up
  "When no args passed, the timer works as a stop watch. Print a hint message every default-count-up-gap"
  [start-time]
  (Thread/sleep default-count-up-gap)
  (io/print-hint-message (- (now) start-time))
  (count-up start-time))

(defn -main
  "The entrance of the timer. If no args passed to the timer, it will work as a count-up, otherwise it will work as a count-down."
  [& args]
  (io/print-start-message (now))
  (if (zero? (count args))
    (count-up (now))
    (count-down args)))
