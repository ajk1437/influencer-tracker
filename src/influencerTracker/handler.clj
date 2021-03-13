(ns influencerTracker.handler    
  (:use ring.middleware.json 
        ring.adapter.jetty)
  (:require [ring.adapter.jetty :as ring]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure.core :refer [defroutes ANY]]
            [compojure.route :as route]
            [influencerTracker.core :as core]))

(defroutes routes
  core/my_routes
  (route/not-found "Not Found")
  (route/resources "/"))

(def app
  (wrap-defaults routes site-defaults))