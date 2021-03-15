(ns influencerTracker.handler    
  (:use ring.middleware.json 
        ring.adapter.jetty)
  (:require [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer [resource-response redirect]]
            [compojure.core :refer [defroutes ANY]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET POST]]
            [influencerTracker.core :as core]
            [influencerTracker.db :as db]))

(defn wrap-return-favicon [handler]
  (fn [req]
    (if (= [:get "/favicon.ico"] [(:request-method req) (:uri req)])
      (resource-response "favicon.ico" {:root "public/img"})
      (handler req))))

(defroutes routes
  (GET "/" [] (core/display-all-influencers))
  (GET "/add-influencer" [] (core/show-create-view))
  (POST "/add-influencer" [username game views language] (db/create-influencer username game views language) (redirect "/"))
  (GET "/delete/:id" [id] (core/delete-influencer id) (redirect "/"))
  (GET "/update/:id" [id] (core/show-update-view id))
  (POST "/update-influencer"  [id username game views language timestamp] (core/update-influencer id username game views language timestamp) (redirect "/"))
  (GET "/top-streams" [] (core/display-top-streams))
    (GET "/top-game" [] (core/display-top-game))
  (route/not-found "Not Found")
  (route/resources "/"))

(def app
  (wrap-defaults routes site-defaults))