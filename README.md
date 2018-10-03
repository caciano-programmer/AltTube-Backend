# AltTube-Backend
Backend microservices for video sharing application. Sort of like a bare bones version of youtube, you can: upload videos, 
create and edit your account so other users can know more about you, leave comments on videos, etc. 
Also each video has a live chat feature built on web sockets that allows communication in real time.  

This is the backend portion of this app, to view/run the frontend visit this repo: https://github.com/caciano-programmer/AltTube-Frontend.git

This app is built into 4 microservices: Account, Video, Comments, Chat. 

  Account: Main microserice that handles user authentication and account create and edit features. This service communicates to a mysql server 
  where each account is stored. Authentication is handled by a combination of jwt and csrf tokens for stateless authentication.  
  
  Video: This microservice handles video uploads. Videos are checked for validation(file type & size constraints) and then through a 
  message broker(Activemq) cookies are sent to Account for authentication.  
  
  Comments: This microservice handles comments crud operations and communicates with a nosql database(Mongodb) to store comments and replies.
  Chat: Lastly this is a lightweight microservice running websockets to implement a live chat feature for videos.

# How To Run
  
