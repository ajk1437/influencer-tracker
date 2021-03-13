(ns influencerTracker.view
  (:use hiccup.page hiccup.element)
  (:require
   [hiccup.core :refer [h]]
   [hiccup.form :as form]
   [clojure.string :as str]
   [influencerTracker.layout :as layout]
   [ring.util.anti-forgery :as anti-forgery]))

(defn display-all-influencers [influencers]
  [:div {:class "card text-center"}
   [:h1 "All influencers"]
   [:p "*Here are the most popular influencers of all time on twtich!*"]
   [:br]
   [:table  {:class "table table-bordered"}
    [:th "Id"]
    [:th "Username"]
    [:th "Game"]
    [:th "Views"]
    [:th "Language"]
    [:th "Timestamp"]
    [:th "Remove"]
    [:th "Edit"]
    (map
     (fn [influencer]
       [:tr
        [:td (h (:id influencer))]
        [:td (h (:username influencer))]
        [:td (h (:game influencer))]
        [:td (h (:views influencer))]
        [:td (h (:language influencer))]
        [:td (h (:timestamp influencer))]
        [:td [:a {:href (str "/delete/" (h (:id influencer)))} "Remove"]]
        [:td [:a {:href (str "/update/" (h (:id influencer)))} "Edit"]]]) influencers)]])


(defn index-page [influencers]
  (layout/common-layout ""
                        [:div {:class "col-lg-1"}]
                        [:div {:class "col-lg-10"}
                         (display-all-influencers influencers)]
                        [:div {:class "col-lg-1"}]))
