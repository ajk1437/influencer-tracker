(ns influencerTracker.twitchapi-test
    (:use clojure.test
          ring.mock.request)
    (:require [clj-http.client :as http]
              [influencerTracker.twitchapi :as ta]))


(deftest test-app
  (testing "get top games method"
    (let [response (ta/get-top-games)]
      (is (not= nil (get-in response ["data" 0 "id"])))))

  (testing "get top game method"
    (let [response (ta/get-top-game)]
      (is (not= nil (get response "id")))))

  (testing "get game by id method"
    (let [response (ta/get-game-by-id 509658)]
      (is (not= nil (get-in response ["data" 0 "id"])))))

  (testing "get top game name route"
    (let [response (ta/get-top-game-name 0)]
      (is (not= nil response))))

  (testing "get top game art method"
    (let [response (ta/get-top-game-box-art-url 0)]
      (is (not= nil response))))

  (testing "get game summary method"
    (let [response (ta/get-game-summary "League of Legends")]
      (is (not= nil (get response "channels")))))

  (testing "get total games on twitch method"
    (let [response (ta/get-total-games)]
      (is (not= nil (get response "_total")))))

  (testing "search categoriesmethod"
    (let [response (ta/search-categories "Just Chatting")]
      (is (not= nil (get-in response ["data" 0])))))

  (testing "get streams method"
    (let [response (ta/get-streams 509658)]
      (is (not= nil (get-in response ["data" 0])))))

  (testing "get streams of user method"
    (let [response (ta/get-streams-user 459331509)]
      (is (not= nil (get-in response ["data" 0])))))

  (testing "get top streams method"
    (let [response (ta/get-streams-top)]
      (is (not= nil (get-in response ["data" 0])))))

  (testing "get channel info method"
    (let [response (ta/get-channel-info 459331509)]
      (is (not= nil (get-in response ["data" 0])))))

  (testing "get user by id twitch method"
    (let [response (ta/get-user 71092938)]
      (is (not= nil (get-in response ["data" 0])))))
  
  )