(ns influencerTracker.db
  (:require [clojure.java.jdbc :as sql]
            [clj-time.coerce :as c]
            [clj-time.format :as f]))

(def db-connection
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//localhost:3306/influencers"
   :user "root"
   :password "root"})

(defn create-influencer [username game views language]
  (let [timestamp (new java.util.Date)]
  (sql/insert! db-connection :influencer [:username :game :views :language :timestamp] [username game views language timestamp])))

(defn get-all-influencers []
  (into [] (sql/query db-connection ["select * from influencer"])))


(defn get-count-influencers []
  (into [] (sql/query db-connection ["select COUNT(*) from influencer"])))

(defn get-influencer-by-id [id]
  (into [] (sql/query db-connection ["select * from influencer where id = ?" id])))

(defn delete-influencer [id]
  (sql/delete! db-connection :influencer
               ["id = ?" id]))

(defn update-influencer [id username game views language timestamp]
  (sql/update! db-connection :influencer {:id id :username username :game game :views views :language language :timestamp timestamp} ["id = ?" id]))
