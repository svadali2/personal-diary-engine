# personal-diary-engine
a diary engine that gives one the ability to:

1. add a particular diary entry
2. find a particular diary entry based on the id
3. update the links put on a particular diary entry
4. provides user based jwt auth so that users can securely access information

# Testing
Open up your favorite ide and run the PersonalDiaryEngineApplication.java program

This should open up a localhost server from where you can connect to http://localhost:8080/swagger-ui.html# to test out the endpoints.

There is an Authentication Controller and a Diary Engine Controller.

The Auth Controller has one single endpoint that returns a new jwt token for every user. It only happens once for a unique username so save that somewhere for the rest of the requests!

Every endpoint in the Diary Enginer Controller takes in an authorization header(strangely enough we are using it for authentication), and then reverse computes a username which is stored in the db with every entry. So, everytime, one wants to update/get a particular entry of a diary object, he/she will only have access to the entries under one's username. 

# Data Model
I put a basic data model as below for the Entry(feel free to use this for testing create operations as well:

{
  "id": 80393746,
  "username": "john" 
  "words": "good morning",
  "dateTime": "21-10-2020 20:20:32",
  "theme": "good night",
  "links": [
    "https://www.google.com"
  ]
}

This ensures there are a group of links associated with a particular entry. The datetime object has to be in the specific format given - 'dd-mm-yy hh-mm-ss'. There is also validation on the links and they have to be valid urls. The id is optional and will be generated in the backend. Here is a sample request:
{
  "username": "john" 
  "words": "good morning",
  "dateTime": "21-10-2020 20:20:32",
  "theme": "good night",
  "links": [
    "https://www.google.com"
  ]
}

I used a MongoDb for the persistence layer and used JPA and Hibernate along with SpringBoot to perform crud ops on the db.

MongoDB connection string: mongodb+srv://admin:admin@cluster0.9bskj.mongodb.net/test?authSource=admin&replicaSet=atlas-goh1fv-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true
