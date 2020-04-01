(defproject parse-and-sort "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [metosin/reitit "0.4.2"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [clj-time "0.15.2"]]
  :main ^:skip-aot parse-and-sort.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
