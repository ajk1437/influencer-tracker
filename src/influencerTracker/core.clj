(ns influencerTracker.core
  (:use compojure.core
        ring.util.json-response
        ring.adapter.jetty)
   (:require [clojure.string :as str]
             [ring.middleware.resource :refer [wrap-resource]]
             [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
             [influencerTracker.db :as db]
             [influencerTracker.view :as view]
             [influencerTracker.twitchapi :as twitch]
             [clojure.data.json :as json]
             [cheshire.core :refer :all]
             [com.hypirion.clj-xchart :as chart]
             [clj-time.format :as f]
             [clj-time.core :as time]
             [clj-time.coerce :as tc]))

(defn is-numeric [x]
  {:pre [(number? x)]})

(defn get-percentage [p total]
  (/ (* p 100.0) total))

(defn distinct-games [influencers]
  (frequencies (map :game influencers)))

(defn distinct-username [influencers]
  (frequencies (map :username influencers)))

(defn chart-game [influencers]
  (sort-by :percentage #(> %1 %2)
           (map
            (fn [game]
              (hash-map
               :game (first game)
               :percentage (get-percentage (second game) (count influencers))))
            (distinct-games influencers))))

(defn top-streams-of-the-week [influencers]
   (filter
    #(>
      (f/unparse (f/formatter "yyyyMMdd") (:timestamp %))
      (f/unparse (f/formatter "yyyyMMdd")  (time/minus (time/now) (time/days 7)))
      influencers)))

(defn average [coll]
  (int
   (quot (reduce + coll) (count coll))))

(defn avrage-viewers []
  (average (map :views (db/get-all-influencers))))

(chart/view
 (chart/pie-chart
  (distinct-username (db/get-all-influencers))
  {:title (str "Which ClojureScript optimization "
               "settings do you use?")
   :render-style :donut
   :annotation-distance 0.82}))

(defn display-all-influencers []
  (view/index-page (sort-by :views #(> %1 %2) (db/get-all-influencers))))

(defn show-create-view []
  (view/add-influencer-form))

(defn show-update-view [id]
  (view/update-influencer-form (db/get-influencer-by-id id)))

(defn create-influencer [username game views language]
  (when-not (str/blank? username)
    (db/create-influencer username game views language)))

(defn delete-influencer [id]
  (when-not (str/blank? id)
    (db/delete-influencer id)))

(defn update-influencer [id username game views language timestamp]
  (when-not (str/blank? id)
    (db/update-influencer id username game views language timestamp))
  (view/index-page (db/get-all-influencers)))

(defn display-top-streams []
  (view/top-streams-page (twitch/get-map-top-stream)))

(defn display-top-game []
  (view/top-game-page (twitch/get-map-top-games)))

(defn display-statistics []
  (view/statistic-page (chart-game (db/get-all-influencers))))


