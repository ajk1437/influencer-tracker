# Influencer tracker - collecting data and statistics

This is web application which contains data collcted from twitch.tv about most popular influencers. Every 15 minutes (twitch api is chaning every 5 min) application will collect data from top 5 streams. With data collected application does statistic about what is most popular game, average viewers, most viewers, least viewers amoung top influencers and etc.

This application is written in Clojure with Leiningen, more about that you can check here: https://leiningen.org/. 
Application is build with Compojure template
```lein new compojure-app influence-tracker```

