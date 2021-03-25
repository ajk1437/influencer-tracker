# Influencer tracker - collecting data and statistics

This is web application which contains data collcted from twitch.tv about most popular influencers. Every 15 minutes (twitch api is chaning every 5 min) application will collect data from top 5 streams. With data collected application does statistic about what is most popular game, average viewers, most viewers, least viewers amoung top influencers and etc.

This application is written in Clojure with Leiningen, more about that you can check here: https://leiningen.org/. 
Application is build with Compojure template ```lein new compojure-app influence-tracker```.#
Libraries used in this project are:#
*[Ring](https://github.com/ring-clojure/ring)
*[Clojure/java.jdbc](https://github.com/clojure/java.jdbc)
*[Compojure](https://github.com/weavejester/compojure)
*[Clj-time](https://github.com/clj-time/clj-time)
*[Overtone/at-at](https://github.com/overtone/at-at)


