(ns timer.core
  (:require [timer.parser :as parser])
  (:import [java.text SimpleDateFormat]
           [java.io Writer])
  (:gen-class))

(def default-time-gap 1000)

(defn- print-exit-message [gap-time]
  "Print exit massage"
  (println "Time Over"))

(defn- now []
  "Get current time, in millisecond"
  (System/currentTimeMillis))

(defn- time-gap
  "Parse the input into millisecond"
  ([] default-time-gap)
  ([& gap]
   (reduce + (map parser/parse gap))))

(defn- countdown
  "Sleep for a while and then print exit message"
  [s]
  (let [start-time (now)
        gap-time (apply time-gap s)]
    (println gap-time "ms")
    (Thread/sleep gap-time)
    (print-exit-message gap-time)))

(defn- zero-args
  "FIXME"
  []
  (println "Please enter the time"))

(defn -main
  "FIXME"
  [& args]
  (if (zero? (count args))
    (zero-args)
    (countdown args)))
