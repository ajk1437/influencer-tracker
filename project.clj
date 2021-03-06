(defproject influencer-tracker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [mysql/mysql-connector-java "8.0.23"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-core "1.9.1"]
                 [ring/ring-json "0.5.0"]
                 [ring-json-response "0.2.0"]
                 [ring/ring-jetty-adapter "1.9.1"]
                 [compojure "1.6.2"]
                 [clj-time "0.15.2"]
                 [com.novemberain/validateur "2.5.0"]
                 [clj-http "2.1.0"]
                 [org.clojure/data.json "1.1.0"]
                 [com.hypirion/clj-xchart "0.2.0"]
                 [clojure.java-time "0.3.2"]
                 [overtone/at-at "1.2.0"]
                 [midje "1.9.10"]
                 [ring/ring-mock "0.4.0"]]

  :plugins [[lein2-eclipse "2.0.0"]
            [lein-ring "0.12.5"]]

  :ring {:handler influencerTracker.handler/app})