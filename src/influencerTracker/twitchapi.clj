(ns influencerTracker.twitchapi
   (:require [clojure.data.json :as json]
             [clj-http.client :as http]
             [clojure.string :as s]
             [influencerTracker.db :as db]))

(def baseURL "https://api.twitch.tv/helix/")
(def oauth2 "https://id.twitch.tv/oauth2/token?")
(def grant-type "&grant_type=client_credentials")
(def client-id "51fsw18ny5mvqfbc93tbmcpqdxx8w4")
(def client-secret "ph21a7svnn3b6zw29kpa5or163yn69")
;; url for auth
(def get-token (str oauth2 "client_id=" client-id "&client_secret=" client-secret grant-type))
(def content-type "application/json")

(def access_token
  "Get access_token"
  (get (json/read-str (:body (http/post get-token))) "access_token"))


(defn get-top-games []
  "Return top 10 games with most viewers"
  (json/read-str
   (:body
    (http/get (str baseURL "games/top")
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-top-game []
  (get-in (get-top-games) ["data" 0]))

(defn get-game-by-id [id]
  (json/read-str
   (:body
    (http/get (str baseURL "games?id=" id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-top-game-name []
  (get-in (get-top-games) ["data" 0 "name"]))

(defn search-categories [category]
  (json/read-str
   (:body
    (http/get (str baseURL "search/categories?query=" category)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn search-channels [channel live]
  (json/read-str
   (:body
    (http/get (str baseURL "search/channels?query=" channel "&live_only=" live)
              {:headers
               :Authorization (str "Bearer " access_token)
               {:client-id client-id
                :content-type content-type}}))))

(defn get-streams [game_id]
  (json/read-str
   (:body
    (http/get (str baseURL "streams?game_id=" game_id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))
(defn get-streams-user [name]
  (json/read-str
   (:body
    (http/get (str baseURL "streams?user_id=" name)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-streams-top []
  (json/read-str
   (:body
    (http/get (str baseURL "streams")
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-streams-top-id []
  (get-in (get-streams-top) ["data" 0 "user_id"]))

(defn get-streams-top-name []
  (get-in (get-streams-top) ["data" 0 "user_name"]))

(defn get-streams-top-gamename []
  (get-in (get-streams-top) ["data" 0 "game_name"]))

(defn get-stream-viewcount []
  (get-in (get-streams-user (get-streams-top-id)) ["data" 0 "viewer_count"]))

(defn get-stream-language []
  (get-in (get-streams-user (get-streams-top-id)) ["data" 0 "language"]))


(defn get-channel-info [id]
  (json/read-str
   (:body
    (http/get (str baseURL "channels?broadcaster_id=" id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer "access_token)
                :content-type content-type}}))))

(defn get-user [user_id]
  (json/read-str
   (:body
    (http/get (str baseURL "users?id=" user_id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

;; loop in background collecting data from twitch api
(defn set-interval [callback ms]
  (future (while true (do (Thread/sleep ms) (callback)))))

;; start work in background every 15min (900000 miliseconds) collect data 
;; [:username :game :views :language]
(def job (set-interval
          #(db/create-influencer
            (get-streams-top-name)
            (get-streams-top-gamename)
            (get-stream-viewcount) 
            (get-stream-language)) 900000))

;; cancel background job
(future-cancel job)