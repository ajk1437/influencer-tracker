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
  "Returns query with distinct game and number of inluencers who played them"
  (frequencies (map :game influencers)))

(defn distinct-username [influencers]
  "Returns query with distinct influencers and how much time did they stay in top 10 streams on twitch"
  (frequencies (map :username influencers)))

(defn distinct-language [influencers]
  "Returns query with distinct language"
  (frequencies (map :language influencers)))

(defn chart-game [influencers]
  "Returns games and precentage of total games played for each games"
  (sort-by :percentage #(> %1 %2)
           (map
            (fn [game]
              (hash-map
               :game (first game)
               :percentage (get-percentage (second game) (count influencers))))
            (distinct-games influencers))))

(defn average [coll]
  (int (quot (reduce + coll) (count coll))))

(defn avrage-viewers [influencers]
  "Returns hash-map with average, max and min viewers for influencers"
  (hash-map
   :average (average (map :views influencers))
   :max (apply max (map :views influencers))
   :mini (apply min (map :views influencers))))

(defn most-watched-language [influencers]
  "Returns the most used language"
  (key (apply max-key val (distinct-language influencers))))

;; (chart/view
;;  (chart/pie-chart
;;   (distinct-username (db/get-all-influencers))
;;   {:title (str "Which ClojureScript optimization "
;;                "settings do you use?")
;;    :render-style :donut
;;    :annotation-distance 0.82}))

(defn games-being-streamed-now []
  "Returns number of games being streamed right now"
  (get (twitch/get-total-games) "_total"))

(defn display-all-influencers []
  "Sort influencer by viewers"
  (view/index-page (sort-by :views #(> %1 %2) (db/get-all-influencers))))

(defn show-create-view []
  (view/add-influencer-form))

(defn show-update-view [id]
  (view/update-influencer-form (db/get-influencer-by-id id)))

(defn create-influencer! [username game views language]
  (when-not (str/blank? username)
    (db/create-influencer! username game views language)))

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

(defn display-statistics [influencers]
  (view/statistic-page (chart-game influencers) (avrage-viewers influencers) (most-watched-language influencers) (games-being-streamed-now)))


