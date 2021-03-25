# Influencer tracker - collecting data and statistics

This is web application which contains data collcted from twitch.tv about most popular influencers. Every 15 minutes (twitch api is chaning every 5 min) application will collect data from top 5 streams. With data collected application does statistic about what is most popular game, average viewers, most viewers, least viewers amoung top influencers and etc.  

This application is written in Clojure with Leiningen, more about that you can check here: https://leiningen.org/.   
Application is build with Compojure template   
  
```lein new compojure-app influence-tracker```.  
  
Libraries used in this project are:  
[Ring](https://github.com/ring-clojure/ring)  
[Clojure/java.jdbc](https://github.com/clojure/java.jdbc)  
[Compojure](https://github.com/weavejester/compojure)  
[Clj-time](https://github.com/clj-time/clj-time)  
[Overtone/at-at](https://github.com/overtone/at-at)  

For collecting data application is using at-at simplee function scheduler, every 15 minutes data is insert into MySQL database. Data about influencer which is collecting is looking like this, example:  
| ID            | Username      | Game          |    Views      | Language      | Timestamp     |
| ------------- | ------------- | ------------- | ------------- | ------------- | ------------- |
| 1  | summit1g  | Content Cell  | Grand Theft Auto V'  | 34444  | en  | 2021-03-01T13:48:59.10|  
  
    
Homepage - this page contains a list of all the influencers collected in the database, you can delete or update them. There is also a count of how many data is there in the database (Influencers count).  
  
Statistics page - here we have the statistics about most played games shown in table and also the percentage a specific game takes of all the collected data. We also have the average, min and max peek viewers for influencers and most streamed language, and how many games are played at this moment.  
  
Top live channel - is showing 10 most popular stream at this moment on twitch. In this table you can see Twitch user id, Username, Thumbnail (screenshoot from live stream), game name, viewers count and language.  
  
Top game - shows 10 most popular games at this moment on twitch. It this table you can see game art, game name, how many live channels are there and how many live viewers.  
  
  
You can run this application buy downloading it and navigate to folder influencer-tracker\src\influencerTracker and use command  
```lein ring server```  
  
    
      
Reference:  
Dmitri Sotnikov, Web Development with Clojure_ Build Bulletproof Web Apps with Less Code (2016, Pragmatic Bookshelf)  
Ryan Baldwin, Clojure Web Development Essentials Develop your own web application with the effective use of the Clojure programming language-Packt Publishing (2015)  
Daniel Higginbotham, Clojure for the Brave and True (2015)
Luke VanderHart, Ryan Neufeld, Clojure Cookbook: Recipes for Functional Programming (2014, O'Reilly Media)



 


