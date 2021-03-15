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
             [cheshire.core :refer :all]))

(defn is-numeric [x]
  {:pre [(number? x)]})


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


