# personal-diary-engine
a diary engine that gives one the ability to:

1. add a particular diary entry
2. find a particular diary entry based on the id
3. update the links put on a particular diary entry
4. provides user based jwt auth so that users can securely access information

# Testing

To test endpoints, feel free to go to this link: https://personal-diary-engine.herokuapp.com/swagger-ui.html . Otherwise, you could run it locally as well. Keep reading for instructions.

Open up your favorite ide and run the PersonalDiaryEngineApplication.java program.

This should open up a localhost server from where you can connect to http://localhost:8080/swagger-ui.html# to test out the endpoints.

There is an **Authentication Controller** and a **Diary Engine Controller**.

The Auth Controller has one single endpoint that returns a new jwt token for every user. It only happens once for a unique username so save that somewhere for the rest of the requests!

Every endpoint in the Diary Enginer Controller takes in an authorization header(strangely enough we are using it for authentication), and then reverse computes a username which is stored in the db with every entry. So, everytime, one wants to update/get a particular entry of a diary object, he/she will only have access to the entries under one's username. 

One particular point of debate in my mind was whether to include an endpoint especially for retrieving links - it felt like ultimately, a costly and repetitive affair for the backend to take the cost of sending the same links twice to the frontend through get/entries/{id} for links and for an entry object. Given the current Entry object, it is difficult to imagine a scenario where one would be distinctly disadvantaged by not having an endpoint for getting links. Having two separate endpoints could also encourage one to make a call to the backend repetitively - calls to the REST API are ultimately costly and should be done sparingly. In caset the individual fields inside an Entry object are more important - an endpoint with query parameters for the fields required could be created such that the client could accurately ask for the required information. This is ultimately implementation specific but given the lack of context around this work, it is not easy to build user empathy for the domain. As such there is simply one endpoint at the moment that retrieves the entire Entry object. 

All code in the files have been unit tested thoroughly. Feel free to run the tests in the respective project folders.

# Data Model
I put a basic data model as below for the Entry(feel free to use this for testing create operations as well:
```
{
  "id": 80393746,
  "words": "good morning",
  "dateTime": "21-10-2020 20:20:32",
  "theme": "good night",
  "links": [
    "https://www.google.com"
  ]
}
```
This ensures there are a group of links associated with a particular entry. The datetime object has to be in the specific format given - 'dd-mm-yy hh-mm-ss'. There is also validation on the links and they have to be valid urls. The id is optional and will be generated in the backend. Here is a sample request:
```
{
  "words": "good morning",
  "dateTime": "21-10-2020 20:20:32",
  "theme": "good night",
  "links": [
    "https://www.google.com"
  ]
}
```

I used a MongoDb for the persistence layer and used JPA and Hibernate along with SpringBoot to perform crud ops on the db.

MongoDB connection string: mongodb+srv://admin:admin@cluster0.9bskj.mongodb.net/test?authSource=admin&replicaSet=atlas-goh1fv-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true

# Programming Style

Lastly, all code written in this project follows the SOLID principles in theory. There is a particular emphasis across files on having a **Single Responsibility** for every calss written and they all follow the **Open/Closed** principles in that they are extensible but closed for modification. E.g., we can create new subrepositories in the future as and when necessary. By using annotations, beans and Spring, we have been able to obtain strong **Dependency Inversion**.This, in effect, made the code much more scalable, decoupled and cleaner. 



Please feel free to reach out with any queries. Thanks!
