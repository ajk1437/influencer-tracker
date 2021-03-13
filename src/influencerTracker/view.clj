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

(defn- influencer-form []
  [:div {:class "form-group card"}
   [:h1 {:class "text-center"} "Add influencer"]
   (form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 [:div {:class "form-group"}
                  (form/label "username" "Username")
                  (form/text-field {:class "form-control"} "username")]
                 [:div {:class "form-group"}
                  (form/label "game" "Game")
                  (form/text-field {:class "form-control"} "game")]
                 [:div {:class "form-group"}
                  (form/label "views" "Views ")
                  (form/text-field {:class "form-control"}  "views")]
                 [:div {:class "form-group"}
                  (form/label "language" "Language")
                  (form/text-field {:class "form-control"}  "language")]
                 (form/submit-button {:class "btn btn-primary btn-lg btn-block"}  "Add influencer")
                 [:br])])

(defn update-influencer-form [influencer]
  (layout/common-layout ""
                        [:div {:class "form-group card"}
                         [:h1 {:class "text-center"} "Edit influencer"]
                         (map
                          (fn [influencer]
                            (form/form-to [:post "/update-influencer"]
                                          (anti-forgery/anti-forgery-field)
                                          (form/hidden-field "id" (:id influencer))
                                          [:div {:class "form-group"}
                                           (form/label "username" "Username")
                                           (form/text-field {:class "form-control"} "username" (:username influencer))]
                                          [:div {:class "form-group"}
                                           (form/label "game" "Game")
                                           (form/text-field {:class "form-control"} "game" (:game influencer))]
                                          [:div {:class "form-group"}
                                           (form/label "views" "Views ")
                                           (form/text-field {:class "form-control"}  "views" (:views influencer))]
                                          [:div {:class "form-group"}
                                           (form/label "language" "Language")
                                           (form/text-field {:class "form-control"}  "language" (:language influencer))]
                                           [:div {:class "form-group"}
                                            (form/label "timestamp" "Timestamp")
                                            (form/text-field {:class "form-control"}  "timestamp" (:timestamp influencer))]
                                          (form/submit-button {:class "btn btn-primary btn-lg btn-block"}  "Edit influencer")
                                          [:br])) influencer)]))


(defn index-page [influencers]
  (layout/common-layout ""
                        [:div {:class "col-lg-1"}]
                        [:div {:class "col-lg-10"}
                         (influencer-form)
                         (display-all-influencers influencers)]
                        [:div {:class "col-lg-1"}]))
