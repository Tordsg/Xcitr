# Release 3

The aim of release 3 is to further develop the Xcitr application in two ways:

1. Building a REST-API.
2. Expansion of the JavaFX application according to three new user stories.

This translated into four milestones that the team set for the third release. For the first two milestones, setting up the REST server and setting up the REST client, we set the due date for completion the 12th of November. For the third milestones, complete testing coverage, we set a due date on the 15th of November. Then the final milestones is the release3 milestone where the whole project is to be delivered, due the 19th of November. We set these milestones for ourselves to make sure that the workload was evenly distributed, also the due date for the first three milestones were set quite early so that there was sufficient time remaining before the final deliverable to fix documentation, javadoc and code refinement. 

## User stories

The requirements for the improved app functionality in the third release are set by three user stories: Bob (us-7), James (us-8) and Phyllis (us-9). These are explained in further detail in [userstories.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/userstories.md), but are summerized by the following three points:

1. us-7: being able to choose an avatar as a profile picture

2. us-8: match notification

3. us-9: chat between logged in user and their matches

## Progress plan

### Clientpackager

In order to make the client application packable to single uber jar file, there needs to be added a new modules to do this that is independent from the ui.

### Core

The goal for the core module is to modify it so that it can communicate and run with a REST server. This means that all of the back-end code associated with the user and bot user will be removed from the core and become its own module. This is to provide the REST client and REST server with equal access to the code that defines a User and a BotUser, which would be difficult otherwise.

The Exciter class will have to be altered so that it is compatible with the REST server. Instead of directly communicating with the applications user interface, the Exciter class recieves requests and information from the REST server according to the user activity. Based on this, the Exciter class decides what functions should be called on, and what information is going to be stored to have a fully developed back-end.

### Integration

To implement proper integration testing for the entire application, a new module that has access to all of the application modules needs to be created with sufficient testing methods.

### JSON

Due to the added functionality of the chat, a new class has to be created in the json module. A new MessageHandler class will be responsible for reading and writing the chat messages that are being sent between the user and their matches (i.e. bot users).

Both the UserHandler class and the MessageHandler class have to be edited so that they are compatible with the REST API.

### Restserver

In order to build a functioning REST API a new restserver module has to be created. This module has to contain two classes, one that initalizes the Xcitr application and another that controls the server. A ServerController will contain all the necessary methods and functions needed so that the class can understand the HTTP requests from the REST client and communicate these to the core and persistence and then obtain the requested functionality from the core and send a HTTP response back to the REST client.

### User interface

In the ui module our goal is to implement a REST client that can send requests to the server and view its response. This means that we have to add two new classes in this module: an AppStarter class that makes the client application packable to a single jar and a ClientHandler class that can function as a bridge between the ui and the server.

In order to support the chat requirements set by Phyllis (us-9) and James (us-8), the MatchController class has to be extended to support this additional functionality. To support the requirement set by Bob (us-7) the ImageController has to be extended also. 

### User

The User module is a module that both the REST client and the REST server needs to have equal access to. The module sets up the framework for a user and the bot users in the application, as well as this module needs to implement the back-end logic for the new chat function in the app. Also to store and retrieve the information in the json files it it necessary to add two deserialization classes for User.java and BotUser.java, so that the json stored strings can be converted to User and BotUser objects.

### Testing

A final goal for the release is to implement sufficient testing coverage for all the modules including the restserver. To test the ui we are also implementing a mock server to test the endpoints for the application. As well as we need to add integration testing to the application to test the all of the modules as a group.

## Work habits, work flow and code quality

For the third release we divided the group into two teams: one REST server team and one REST client team. Both teams used pair programming to a great extent when coding the server and client, while we delegated smaller individual tasks to each other based on what problems arised during the process and indivudal interests.
