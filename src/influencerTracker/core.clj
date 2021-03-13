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

(defn is-numeric [x]
  {:pre [(number? x)]})

(defn display-all-influencers []
  (view/index-page (db/get-all-influencers)))

(defn show-update-view [id]
  (view/update-influencer-form (db/get-influencer-by-id id)))

(defn create-influencer [username game views language]
  (when-not (str/blank? username)
    (db/create-influencer username game views language))
  (ring/redirect "/"))

(defn delete-influencer [id]
  (when-not (str/blank? id)
    (db/delete-influencer id))
  (ring/redirect "/"))

(defn update-influencer [id username game views language timestamp]
  (when-not (str/blank? id)
    (db/update-influencer id username game views language timestamp))
  (view/index-page (db/get-all-influencers)))

(defn wrap-return-favicon [handler]
  (fn [req]
    (if (= [:get "/favicon.ico"] [(:request-method req) (:uri req)])
      (ring/resource-response "favicon.ico" {:root "public/img"})
      (handler req))))

(defroutes my_routes
  (GET "/" [] (display-all-influencers))
  (POST "/" [username game views language] (db/create-influencer username game views language))
  (GET "/delete/:id" [id]  (delete-influencer id))
  (GET "/update/:id" [id] (show-update-view id))
  (POST "/update-influencer"  [id username game views language timestamp] (update-influencer id username game views language timestamp)))


