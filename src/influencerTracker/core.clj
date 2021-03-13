(ns influencerTracker.core
  (:use compojure.core
        ring.util.json-response
        ring.adapter.jetty)
   (:require [compojure.core :refer [defroutes GET POST]]
             [clojure.string :as str]
             [ring.util.response :as ring]
             [ring.middleware.resource :refer [wrap-resource]]
             [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
             [influencerTracker.db :as db]
             [influencerTracker.view :as view]))

(defn- not-found []
  (influencerTracker.view/not-found))

(defn- display-all-influencers []
  (view/index-page (db/get-all-influencers)))

(defn- create-influencer [username game views language timestamp]
  (when-not (str/blank? username)
    (db/create-influencer username game views language timestamp))
  (ring/redirect "/"))

(defroutes my_routes
  (GET "/" [] (display-all-influencers))
  (POST "/" [username game views language] (db/create-influencer username game views language)))

(def app
  (wrap-defaults my_routes site-defaults))

