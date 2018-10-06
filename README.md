# AltTube-Backend
Backend microservices for video sharing application. Sort of like a bare bones version of youtube, you can: upload videos, 
create and edit your account so other users can know more about you, leave comments on videos, etc. 
Also each video has a live chat feature built on web sockets that allows communication in real time.  

This is the backend portion of this app, to view/run the frontend visit this repo:  
https://github.com/caciano-programmer/AltTube-Frontend

This app is built into 4 microservices: Account, Video, Comments, Chat. 

  Account: Main microserice that handles user authentication and account create and edit features. This service communicates to a mysql server 
  where each account is stored. Authentication is handled by a combination of jwt and csrf tokens for stateless authentication.  
  
  Video: This microservice handles video uploads. Videos are checked for validation(file type & size constraints) and then through a 
  message broker(Activemq) cookies are sent to Account for authentication.  
  
  Comments: This microservice handles comments crud operations and communicates with a nosql database(Mongodb) to store comments and replies.  
  
  Chat: Lastly this is a lightweight microservice running websockets to implement a live chat feature for videos.

# How To Run
 To run this application you will need to have docker installed. For more on that go here: https://www.docker.com/get-started  
 Since this is a multi-module application we will also need docker-compose to run each module and database server in its own container. For
 more on that go here: https://docs.docker.com/compose/  
 
 Once docker and docker-compose are both installed running this application is extremely simple. First clone this repository and from command 
 line CD inside of application root directory. Then simply execute the following command which will be called on the docker-compose.yml file:  
 
  `docker-compose up`  
  
 And voila, docker will begin to load the dependencies and execute commands. Please give it a couple minutes to load all the containers 
 containing microservices and database servers etc. Remember this is just the backend, to view and interact with this application please visit 
 this repo to run the frontend portion: https://github.com/caciano-programmer/AltTube-Frontend  
 
