# Welcome to TweeMedia
Now you can download media from your favorite Twitter profiles using the Twitter Api !!

# Consuming the API Rest
- Payload
```
  {
    "twitterProfile" : "twitter profile",
    "registryCount" : "0",
    "getPhotos" : "1",
    "getVideos" : "1"
  }
```
- Local url example: 
  - *localhost:8080/TweeMedia/GetMedia*


*Notes: Use 0 in registryCount to retrieve all tweets*

# Set up
1. Create file "twitter4j.properties" in resources folder
2. Fill up with data (keys) from https://developer.twitter.com/en/portal/dashboard

File format:
debug=true
oauth.consumerKey=*********************
oauth.consumerSecret=******************************************
oauth.accessToken=**************************************************
oauth.accessTokenSecret=******************************************

# Accesing the Resources
- Base path : /TweeMedia
- H2 console full path : localhost:8080/TweeMedia/h2-console/
- Swagger UI : localhost:8080/TweeMedia/swagger-ui/index.html

# References
- Hexagonal Architecture: https://betterprogramming.pub/hexagonal-architecture-with-spring-boot-74e93030eba3