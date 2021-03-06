(ns influencerTracker.layout
  (:use [hiccup.element :only (link-to)])
  (:require [hiccup.page :as h]
            [hiccup.page :refer [html5 include-css]]))

(defn- navbar []
  [:nav {:class "navbar navbar-dark bg-dark"}
   [:div {:class "container-fluid"}
    [:a {:class "navbar-brand mb-0 h1" :href "/"}
     [:img {:src "/logo.png" :style "width: 35px;"} " Twitch influencers"]]
    [:a {:class "navbar-brand" :href "/display-statistics"} "Statistics"]
    [:a {:class "navbar-brand" :href "/top-live-channel"} "Top live channel"]
    [:a {:class "navbar-brand" :href "/top-game"} "Top game"]
    [:a {:class "navbar-brand" :href "/add-influencer"} "Add influencer"]]])

(defn common-layout [body]
  (h/html5
   [:head
    [:title "Influencer tracker"]
    [:link {:rel "schortcut icon" :type "image/png" :href "/logo.png"}]
    (include-css "/css/bootstrap.css")]
   [:body
    (navbar)
    [:div {:class "container"} body]]))

(defn not-found []
  (common-layout  [:div {:id "error"} "NOT FOUND! Unfortunately, the page you requested could not be found!!!"]))


