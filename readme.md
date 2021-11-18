[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://amethyst-impala-87u6ryfm.ws.gitpod.stud.ntnu.no)

# Group gr2117 repository

This is the coding project called Xcitr for group 2117´s application in the course IT1901. The project is git modified and will open in gitpod if you press the gitpod label above. Note that the application takes about 5-10 minutes to start up in gitpod due to applications animations, this process is much more efficient when the application is run from a local IDE.

## Running, Building and Testing the Project

The project uses maven configuration, with the spring boot framework to run the application from a server. To build the project go to **modules-exciter** with `cd modules-exciter` and run `mvn clean install`. It is also possible to run 'mvn install' instead, but `mvn clean install` decreases the chances of getting a bug during building. To run the application one can choose one of two options:

In the first option go from **modules-exciter**, start by going into the **restserver** module by entering `cd restserver` and then `cd target`. Write `java -jar server-standalone.jar` in the terminal to start the server. Open a new terminal and go from **modules-exciter** and go into the **ui** module by entering 'cd ui' and then 'cd target' to get to the target folder. To run the application write `java -jar Xcitr.jar` in the terminal. 

The second option is to go from **modules-exciter** and go to the **restserver** module by entering `cd restserver`. In the terminal write `mvn spring-boot:run` to start up the server. Afterwards open a new terminal and from **modules-exciter** and go to the **ui** module by entering 'cd ui' in the terminal. To start the application enter 'mvn javafx:run' in the terminal. 

To run the tests go into **modules-exciter** and enter `mvn test` into the terminal to run the tests in every module. To run the tests in only one module go into the module and run 'mvn test' here.

## Tools for code-quality

We configure three tools related to code quality:
1. **Checkstyle** - checks more superficial and stylistic properties of the code as text
2. **Spotbugs** - analyzes the code for common mistakes
3. **Jacoco** - collects and presents information and test-coverage

All these can be run by entering `mvn verify` in the terminal from **modules-exciter**. 

To determine the test coverage of our code, Jacoco has been implemented to test how much of the different classes and modules have been tested. After running `mvn verify` one can view these under target for each module. The quality of the code is also checked using spotbugs and checkstyle. To only check spotbugs enter `mvn spotbugs:spotbugs` in **modules-exciter**. To only check checkstyle enter `mvn checkstyle::check` in the terminal from **modules-exciter** for all modules or go into the module and run `mvn checkstyle::check` in the terminal.

## Work habits and work flow

To develop the Xcitr application our group have based our work process throughout the project on the Scrum framework. Instead of daily sprints with the whole group we have had two weekly sprints where we updated each other on our progress and set new goals and plans for the nest sprint. Before we start working on a release we have start meeting where we set the overall goals with additional sub-goals for the deliverable, as well as divided

The project is version controlled using git. This means that we as a group actively use git to set up milestones and issues for the project, as well as we create multiple branches to be able to work on different aspects of the code simoultenously.

#### Branches

The *default* branch on the Xcitr project is the **master** branch, which is protected and its only the dev branch that can be merged into the master branch. The **dev** branch is also protected, but where all working branches are created from. When working on a new aspect or issue on the project a branch is created from dev. The name conventions for branches are all lower case letters with a hyphen between each word (ex. ui-testing). Note: this convetion was implented after the first release, meaning that some of the early branches do not follow this rule.

#### Issues

Before we begin working on each release the group sits down to write down goals and issues for each group member for the deliverable. The name convetion for the issues is that they are in english and are descriptive. Issues are also added once a new problem arises during the work process.

#### Commit messages

When committing changes in the code the commit messages must be concise, descriptive and in english, starting with a capital letter.

## Modules-Exciter

The project is organized in the following structre. Code and modules can be found under modules-exciter. Within this folder one can find all of the core logic that makes up the backend of the app, along with the code for the frontend user interface and the file storage. Within modules-exciter there is also a README-file with a complete app description.

### core

[Link to core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/core)

The core module contains only one class for the applications core functionality, the Exciter class.

There is also a test class for the Exciter class that tests whether the like-function and match-function works how we intend it to.

### json

[Link to json](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json)

There is two classes within thes JSON module, which is the [UserHandler](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json/UserHandler) and the [MessageHandler](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json/MessageHandler) class.

The former handles saving and reading user objects from file. This will include all the informations such as name, age, mail, matches, and other temporary variables that makes it possible to continue usage of the app due to server crash.

MessageHandler stores all the chats between users. The class supports getting specific chats by specifing which user the chat is between.
Where the parameters is the mail of users.

```java
public Chat getChat(String user1, String user2)
```

### restserver

[Link to restserver](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver)

The server is built on the Spring framework. The module consist of two classes, [ExciterApplication](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver/ExciterApplication), and [ServerController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver/ServerController). The former starts up the server on localhost with default port 8080, while the latter is the controller that handles requests from clients.

### ui

[Link to ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui)

The ui module contains all of the front-end development of the app. This includes the App class which provides the framework for managing the JavaFX application, the AppStarter class that makes the client application packable to a single jar and the ClientHandler that handles the applications´ REST Client. Further there are six controller classes that implements the core and user modules to assemble the user interface.

- [ImageController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/ImageController): handles the images that are being swiped
- [LoginController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/LoginController): connected to login.fxml and enables a user to log in on the Xcitr-application
- [SignUpController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/SignUpController): connected to signup.fxml and manages the signup window where a new user can create a new account on the application
- [PrimaryController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/PrimaryController): connected to primary.fxml and manages the swiping-page where the user can like and match with other users
- [SecondaryController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/SecondaryController): connected to profile.fxml and manages the user information as a profile page
- [MatchController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/MatchController): connected to match.fxml and manages the users matches and communication with them

### user

[Link to user](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user)

The user module contains a class that defines a user in the application, a class for the chat functionality, as well as a class that defines the bot users. This module is separate from the core logic so that the REST client and the REST server has equal access to these classes and methods. There are also two classes that deserializes the user and chat data so that it can be transmitted between the client and server.

- [BotUser](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/BotUser)
- [Chat](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/Chat)
- [ChatDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/ChatDeserializer)
- [User](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/User)
- [UserDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/UserDeserializer)


