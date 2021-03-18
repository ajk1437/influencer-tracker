(ns influencerTracker.handler-test
  (:use clojure.test
        ring.mock.request)
  (:require [clj-http.client :as http]
            [influencerTracker.handler :as handler]))

(deftest test-app
  (testing "main route"
    (let [response (handler/app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "add-influencer route"
    (let [response (handler/app (request :get "/add-influencer"))]
      (is (= 200 (:status response)))))

  (testing "top-live-channel route"
    (let [response (handler/app (request :get "/top-live-channel"))]
      (is (= 200 (:status response)))))

  (testing "top-game route"
    (let [response (handler/app (request :get "/top-game"))]
      (is (= 200 (:status response)))))
  
  (testing "display-statistics route"
    (let [response (handler/app (request :get "/display-statistics"))]
      (is (= 200 (:status response)))))

  (testing "delete route, returns 302 status code if it deleted"
    (let [response (handler/app (request :get "/delete/1"))]
      (is (= 302 (:status response)))))

  (testing "update route"
    (let [response (handler/app (request :get "/update/1"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (handler/app (request :get "/invalid"))]
      (is (= 404 (:status response))))))

(defn github-profile [username]
  (let [response (http/get (str "https://api.github.com/users/" username))]
    (when (= (:status response) 200)
      (:body response))))
