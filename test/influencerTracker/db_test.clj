(ns influencerTracker.db-test
  (:use clojure.test
        ring.mock.request)
  (:require [clj-http.client :as http]
            [influencerTracker.db :as db]))

(deftest test-app

  (testing "insert new influencer"
    (let [response (db/create-influencer! "username" "game" 1 "language")]
      (is (= 1 (nth response 0)))))

  (testing "get all influencer"
    (let [response (db/get-all-influencers)]
      (is (not= 0 (count response)))))

  (testing "get number of influencer"
    (let [response (db/get-all-influencers)]
      (is (not= nil response))))

  (testing "get influencer by id"
    (let [response (db/get-influencer-by-id 4)]
      (is (not= nil (get (first response) :username)))))

  (testing "delete influencer"
    (let [response (db/delete-influencer 1017)]
      (is (= 1 (nth response 0)))))

  (testing "update influencer"
    (let [response (db/update-influencer 996 "username" "game" 1 "language" (new java.util.Date))]
      (is (= 1 (nth response 0))))))
  
  
 
