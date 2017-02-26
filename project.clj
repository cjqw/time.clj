(defproject timer "0.3.0"
  :description "A timer written in clojure"
  :url "https://github.com/cjqw/timer"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [instaparse "1.4.5"]]
  :plugins [[lein-codox "0.10.3"]]
  :main ^:skip-aot timer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
