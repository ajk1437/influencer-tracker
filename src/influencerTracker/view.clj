(ns influencerTracker.view
  (:use hiccup.page hiccup.element)
  (:require [hiccup.core :refer [h]]
            [hiccup.form :as form]
            [clojure.string :as str]
            [influencerTracker.layout :as layout]
            [ring.util.anti-forgery :as anti-forgery]
            [clj-time.format :as fmt]
            [clj-time.core :as t]
            [validateur.validation :refer :all]
            [influencerTracker.twitchapi :as twitchapi]))

(def custom-formatter (fmt/formatter "yyyy-MM-dd"))

(defn display-all-influencers [influencers]
  [:div
   [:h1 "Top influencers"]
   [:p "Here are the most popular influencers with most peek viewers on twtich!"]
   [:table  {:class "table"}
    [:thead {:class "thead-light"}
     [:th {:scope "col"} "#"]
     [:th {:scope "col"} "Username"]
     [:th {:scope "col"} "Game"]
     [:th {:scope "col"} "Peek live views"]
     [:th {:scope "col"} "Language"]
     [:th {:scope "col"} "Timestamp"]
     [:th {:scope "col"} "Remove"]
     [:th {:scope "col"} "Edit"]]
    (map
     (fn [influencer]
       [:tr
        [:td (h (:id influencer))]
        [:td (h (:username influencer))]
        [:td (h (:game influencer))]
        [:td (h (:views influencer))]
        [:td (h (:language influencer))]
        [:td (h (.format (java.text.SimpleDateFormat. "dd/MM/yyyy hh:mm") (:timestamp influencer)))]
        [:td [:a {:class "btn btn-primary btn-danger" :href (str "/delete/" (h (:id influencer)))} "Remove"]]
        [:td [:a {:class "btn btn-primary btn-info" :href (str "/update/" (h (:id influencer)))} "Edit"]]]) influencers)]])

;;

(defn add-influencer-form []
  (layout/common-layout
   [:div {:class "form-group"}
    [:h1 {:class "text-center"} "Add influencer"]
    (form/form-to [:post "/add-influencer"]
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
                  [:br])]))

(defn update-influencer-form [influencer]
  (layout/common-layout
   [:div {:class "form-group"}
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
                     (form/submit-button {:class "btn btn-primary btn-block btn-lg"}  "Edit influencer")
                     [:br])) influencer)]))

(defn display-top-streams [stream]
  [:div
   [:h1 "Top live channels"]
   [:p "10 Most popular stream at this moment on twitch!"]
   [:table  {:class "table"}
    [:thead {:class "thead-light"}
     [:th {:scope "col"} "#Twitch user id"]
     [:th {:scope "col"} "Username"]
     [:th {:scope "col"} "Thumbnail"]
     [:th {:scope "col"} "Game name"]
     [:th {:scope "col"} "Viewers count"]
     [:th {:scope "col"} "Language"]]
    (map
     (fn [stream]
       [:tr
        [:td (h (:user_id stream))]
        [:td (h (:username stream))]
        [:td [:img {:src (h (:thumbnail_url stream))}]]
        [:td (h (:game_name stream))]
        [:td (h (:viewer_count stream))]
        [:td (:language stream)]]) stream)]])

(defn display-top-game [games]
  [:div
   [:h1 "Top games"]
   [:p "10 Most popular games at this moment on twitch!"]
   [:table  {:class "table"}
    [:thead {:class "thead-light"}
     [:th {:scope "col"} "#"]
     [:th {:scope "col"} "Game name"]
     [:th {:scope "col"} "Art"]]
    (map
     (fn [games]
       [:tr
        [:td]
        [:td (h (:name games))]
        [:td [:img {:src (:art games)}]]]) games)]])

(defn display-statistics [games]
   [:div
    [:h1 "Statitcs about games"]
    [:p "Most popular games by % of how much Influencer plays"]
    [:table  {:class "table"}
     [:thead {:class "thead-light"}
      [:th {:scope "col"} "#"]
      [:th {:scope "col"} "Game name"]
      [:th {:scope "col"} "Percentage %"]]
     (map
      (fn [games]
        [:tr
         [:td]
         [:td (h (:game games))]
         [:td (h (format "%.2f" (:percentage games))) "%"]]) games)]])

(defn statistic-page [games]
  (layout/common-layout
   (display-statistics games)))


(defn top-game-page [games]
  (layout/common-layout
   (display-top-game games)))

(defn top-streams-page [stream]
  (layout/common-layout
   (display-top-streams stream)))

(defn index-page [influencers]
  (layout/common-layout
   (display-all-influencers influencers)))

