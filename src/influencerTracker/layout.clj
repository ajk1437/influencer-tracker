(ns influencerTracker.layout
  (:use [hiccup.element :only (link-to)])
  (:require [hiccup.page :as h]
            [hiccup.page :refer [html5 include-css]]))

(defn- navbar []
  [:nav {:class "navbar navbar-dark bg-dark"}
   [:a {:class "navbar-brand" :href "/"}
    [:img {:src "/logo.png" :style "width: 35px;"} " Twitch influencers!"]]
   [:a {:class "navbar-brand" :href "/top-streams"} "Top streams"]
   [:a {:class "navbar-brand" :href "/top-game"} "Top game"]
   [:a {:class "navbar-brand" :href "/add-influencer"} "Add influencer"]])

(defn common-layout [header & body]
  (h/html5
   [:head
    [:title "Influencer tracker"]
    [:link {:rel "schortcut icon" :type "image/png" :href "/logo.png"}]
    (include-css "/css/bootstrap.css")]
   [:body
    (navbar)
    [:div {:class "container"}
     [:h1 header]
     body]]))

(defn not-found []
  (common-layout "NOT FOUND" [:div {:id "error"} "Unfortunately, the page you requested could not be found!!!"]))
