(ns timer.core
  (:require [timer.parser :as parser])
  (:import [java.text SimpleDateFormat]
           [java.io Writer])
  (:gen-class))

(def default-time-gap 1000)


(defn- print-exit-message [gap-time]
  "Print time"
  (println gap-time "ms")
  (println "Time Over"))

(defn- now []
  "Get current time, in millisecond"
  (System/currentTimeMillis))

(defn- time-gap
  ([] default-time-gap)
  ([gap & other]
   (parser/parse gap)
   ))


(defn -main
  "Sleep for a while and the print exit message"
  [& args]
  (let [start-time (now)
        gap-time (apply time-gap args)]
    (Thread/sleep gap-time)
    (print-exit-message gap-time)))
