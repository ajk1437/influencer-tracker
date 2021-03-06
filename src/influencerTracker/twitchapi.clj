(ns influencerTracker.twitchapi
  (:use overtone.at-at)
  (:require [clojure.data.json :as json]
            [clj-http.client :as http]
            [clojure.string :as s]
            [influencerTracker.db :as db]
            [clj-time.core :as time]
            [clj-time.coerce :as tc]
            [clj-time.format :as f]))

;; for underlined errors 
;;(declare mk-pool stop-and-reset-pool! every show-schedule)

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

;; GAMES
(defn get-top-games []
  "Get top 10 games with most viewers"
  (json/read-str
   (:body
    (http/get (str baseURL "games/top")
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-top-game []
  "Get top games with most viewers"
  (get-in (get-top-games) ["data" 0]))

(defn get-game-by-id [id]
  "Get games with id"
  (json/read-str
   (:body
    (http/get (str baseURL "games?id=" id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-top-game-name [rank]
  "Get top games name"
  (get-in (get-top-games) ["data" rank "name"]))

(defn get-top-game-box-art-url [rank]
  "Get top games image width:100 height:100"
  (clojure.string/replace (get-in (get-top-games) ["data" rank "box_art_url"]) "{width}x{height}" "150x150"))

(defn get-game-summary [game]
  "Get summary of game live channels and live viewers"
  (json/read-str
   (:body
    (http/get (str "https://api.twitch.tv/kraken/streams/summary?game=" (clojure.string/lower-case game))
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :Accept "application/vnd.twitchtv.v5+json"}}))))

(defn get-map-top-games []
  "Create hash-map for top games on twitch"
  (let [x (get-top-games)]
    (for [y (range 0 9)]
      (hash-map
       :game_id (get-in x ["data" y "id"])
       :name (get-in x ["data" y "name"])
       :art (clojure.string/replace (get-in x ["data" y "box_art_url"]) "{width}x{height}" "150x150")
       :channels (get (get-game-summary (get-in x ["data" y "name"])) "channels")
       :viewers (get (get-game-summary (get-in x ["data" y "name"])) "viewers")))))

(defn get-total-games []
  "Get number of total games streaming on twitch"
  (json/read-str
   (:body
    (http/get (str "https://api.twitch.tv/kraken/games/top")
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :Accept "application/vnd.twitchtv.v5+json"}}))))


;;CATEGORIES
;;
(defn search-categories [category]
  "Returns a list of games or categories that match the query via name"
  (json/read-str
   (:body
    (http/get (str baseURL "search/categories?query=" category)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn search-channels [channel live]
  "Returns channelsthat match the query via channel name and who are live right now"
  (json/read-str
   (:body
    (http/get (str baseURL "search/channels?query=" channel "&live_only=" live)
              {:headers
               {:Authorization (str "Bearer " access_token)
                :client-id client-id
                :content-type content-type}}))))

(defn get-streams [game_id]
  "Gets information about active streams for game"
  (json/read-str
   (:body
    (http/get (str baseURL "streams?game_id=" game_id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-streams-user [user_id]
  "Gets information about user stream for user id"
  (json/read-str
   (:body
    (http/get (str baseURL "streams?user_id=" user_id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))


;; STREAMS
(defn get-streams-top []
  "Gets information about active streams"
  (json/read-str
   (:body
    (http/get (str baseURL "streams")
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-map-top-stream []
  "Create hash-map for first 10 streams"
  (let [x (get-streams-top)]
    (for [y (range 0 10)]
      (hash-map
       :user_id (get-in x ["data" y "user_id"])
       :username (get-in x ["data" y "user_name"])
       :title (get-in x ["data" y "title"])
       :thumbnail_url (clojure.string/replace (get-in x ["data" y "thumbnail_url"]) "{width}x{height}" "150x150")
       :game_name (get-in x ["data" y "game_name"])
       :viewer_count (get-in x ["data" y "viewer_count"])
       :language (get-in x ["data" y "language"])))))

(defn get-stream-id [stream x]
  "Get language from user id"
  (get-in stream ["data" x "user_id"]))

(defn get-stream-name [stream x]
  "Get language from username"
  (get-in stream ["data" x "user_name"]))

(defn get-stream-gamename [stream x]
  "Get language from game name"
  (get-in stream ["data" x "game_name"]))

(defn get-stream-viewer-count [stream x]
  "Get language from view count"
  (get-in stream ["data" x "viewer_count"]))

(defn get-stream-language [stream x]
  "Get language from stream"
  (get-in stream ["data" x "language"]))

;;CHANNELS

(defn get-channel-info [id]
  "Returns a list of channels that match the query via broadcaster_id"
  (json/read-str
   (:body
    (http/get (str baseURL "channels?broadcaster_id=" id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

(defn get-user [user_id]
  "Gets information about one or more specified Twitch users"
  (json/read-str
   (:body
    (http/get (str baseURL "users?id=" user_id)
              {:headers
               {:client-id client-id
                :Authorization (str "Bearer " access_token)
                :content-type content-type}}))))

;; Create a pool
(def my-pool (mk-pool))

;; Task 1 get infulencer whose stream is rank as 1.
(every 900000
       #(let [streams (get-streams-top)]
          (db/create-influencer!
           (get-stream-name streams 0)
           (get-stream-gamename streams 0)
           (get-stream-viewer-count streams 0)
           (get-stream-language streams 0)))
       my-pool
       :desc "Add rank 1. influencers task")

;; Task 2 get infulencer whose stream is rank as 2.
(every 900000
       #(let [streams (get-streams-top)]
          (db/create-influencer!
           (get-stream-name streams 1)
           (get-stream-gamename streams 1)
           (get-stream-viewer-count streams 1)
           (get-stream-language streams 1)))
       my-pool :desc
       "Add rank 2. influencers task")

;; Task 3 get infulencer whose stream is rank as 3.
(every 900000
       #(let [streams (get-streams-top)]
          (db/create-influencer!
           (get-stream-name streams 2)
           (get-stream-gamename streams 2)
           (get-stream-viewer-count streams 2)
           (get-stream-language streams 2)))
       my-pool :desc
       "Add rank 3. influencers task")

;; Task 4 get infulencer whose stream is rank as 4.
(every 900000
       #(let [streams (get-streams-top)]
          (db/create-influencer!
           (get-stream-name streams 3)
           (get-stream-gamename streams 3)
           (get-stream-viewer-count streams 3)
           (get-stream-language streams 3)))
       my-pool :desc
       "Add rank 4. influencers task")

;; Task 5 get infulencer whose stream is rank as 5.
(every 900000
       #(let [streams (get-streams-top)]
          (db/create-influencer!
           (get-stream-name streams 4)
           (get-stream-gamename streams 4)
           (get-stream-viewer-count streams 4)
           (get-stream-language streams 4)))
       my-pool :desc
       "Add rank 5. influencers task")

;;show that task are working 
(show-schedule my-pool)

;; stop collecting data
;;(stop-and-reset-pool! my-pool :strategy :kill)

