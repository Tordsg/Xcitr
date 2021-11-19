[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://amethyst-impala-87u6ryfm.ws.gitpod.stud.ntnu.no)

# Group gr2117 repository

This is the coding project called Xcitr for group 2117´s application in the course IT1901. The project is git modified and will open in gitpod if you press the gitpod label above. Note that the application takes about 5-10 minutes to start up in gitpod due to the app animations, when the application is run from a local IDE this process is much more efficient.

## Running, Building and Testing the Project

The project uses maven configuration, with the spring boot framework to run the application from a server. To build the project go to **modules-exciter** by entering `cd modules-exciter` in the terminal and run `mvn clean install`. It is also possible to run `mvn install` instead, but `mvn clean install` decreases the chances of getting a bug during building. There are two ways to run the application:

1. In the first option, from **modules-exciter** folder, go into the **restserver** module by entering `cd restserver` and then `cd target` in the terminal. Then write `java -jar server-standalone.jar` to start the server. Open a new terminal and go to the **modules-exciter** folder and then `cd ui` to go to the **clientpackager** module. Enter `cd target` to get to the target folder. Lastly, to run the entire application write `java -jar Xcitr.jar` in the terminal. The application will now start up.

2. In the second option, go from  the **modules-exciter** folder and into the **restserver** module by entering `cd restserver` in the terminal. Then write `mvn spring-boot:run` to start up the server. Afterwards open a new terminal and from the **modules-exciter** folder, go to the **clientpackager** module by entering `cd ui` in the terminal. To start the application enter `mvn javafx:run` in the terminal. The application will now start up.

To run the tests go into **modules-exciter** and enter `mvn test` in the terminal to run the tests for every module. To run isolated tests for a individual module go into the module that should be tested and run `mvn test` here.

## Work habits, work flow and code quality

### Tools for code-quality

We configure three tools related to code quality:

1. **Checkstyle** - checks more superficial and stylistic properties of the code as text
2. **Spotbugs** - analyzes the code for common mistakes
3. **Jacoco** - collects and presents information and test-coverage

All these can be run by entering `mvn verify` in the terminal from **modules-exciter**.

To determine the test coverage of our code, Jacoco has been implemented to test how much of the different classes and modules are being tested. After running `mvn verify` or `mvn clean install` one can view the jacoco test coverage reports under the target folder for each module. The quality of the code is also checked using spotbugs and checkstyle. To check spotbugs enter `mvn spotbugs:spotbugs` in **modules-exciter**. To check checkstyle enter `mvn checkstyle::check` in the terminal from **modules-exciter** for all modules or go into indivudal modules and run `mvn checkstyle::check` in the terminal.

### Scrum and work flow

To develop the Xcitr application our group have based our work process throughout the project on the Scrum framework. Instead of daily scrums, the group has two weekly scrums where we update each other on our progress and set new goals and plans for the current and next sprint. Due to some differences in coding experience the group has also operated with pair programming to a great extent during the entire developing process.

Before we start working on a release we have a sprint review where we reflect on the projects progress and what work needs to be completed and by whom to reach the goals for the release. We also have a planning sprint meeting where we set our own milestones with additional sub-goals for the deliverable. The group communication during the rest of the week include a combination of work sessions with the whole group or in pairs, video calls on discord and a messenger chat where we communicate problems and successes during the week. We also use these mediums to plan meeting times with our learning assistant.

The group did not appoint a scrum master in the beginning of the project phase, however prior to each srum meeting each group member writes down issues, goals or other important points that the group needs to be informed about or points that need to be discussed. There is a collective responsibility to make sure that we work effectively and within the scrum framework.

### Structural choices on git

The project is version controlled using git. This means that we as a group actively use git to set up milestones and issues for the project, as well as we create multiple branches to be able to work on different aspects of the code simoultenously.

#### Branches

The *default* branch on the Xcitr project is the **master** branch. This branch is protected and it is only the **dev** branch that can be merged into the master branch. The **dev** branch is also protected, but this is the branch where all working branches are branched from when working on a new issue on the project. The name conventions for branches are all lower case letters with a hyphen between each word (ex. ui-testing). Note: this convetion was implented after the first release, meaning that some of the early branches did not follow this rule.

#### Issues

Before we begin working on each release the group meets to set milestones, goals, and issues for the group members that needs to be completed for the deliverable. The name convention for the issues is that they are descriptive and in English. Issues are also created once a new problem arises during the working process.

#### Commit messages

When committing changes in the code the commit messages must be concise, descriptive and in English.

## Modules-Exciter

The project is organized in the following structure. Code and modules can be found under the modules-exciter folder. Within this folder one can find all of the core logic and persistence that makes up the backend of the app, along with the code for the frontend user interface, the restserver and the integration testing. Within modules-exciter there is also a README-file with a complete app description and an illustration for how to use the app.

### [clientpackager](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/clientpackager)

This module contains only one class, this is the AppStarter class that makes client application packable to single uber jar file.

### [core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/core)

The core module contains only one class for the applications core functionality, the Exciter class.

There is also a test class for the Exciter class that tests whether the like-function and match-function works how we intend it to.

### [integration](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/integration)

Test class for integration testing for the application.

### [json](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json)

There is two classes within thes JSON module, which is the [UserHandler](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json/UserHandler) and the [MessageHandler](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json/MessageHandler) class. These two classes stores data in an open standard file format and data interchange format that uses human-readable text to store and transmit the user data.

The former handles saving and reading user objects from file. This will include all the informations such as name, age, mail, matches, and other temporary variables that makes it possible to continue usage of the app due to server crash.

MessageHandler stores all the chats between users. The class supports getting specific chats by specifing which user the chat is between.
Where the parameters is the mail of users.

```java
public Chat getChat(String user1, String user2)
```

The application utilizes both explicit and implicit storage. The user excplicitly stores their name, email, age and password when they create an account. Also on the profile page the user chooses to save updated information about themselves. The reason for why we use explicit storage in these two cases is because the user themselves can choose what information will be saved or updated, it does not happen automatically. Otherwise the app stores imformation implicitly because the stored information reflects user activity, for example liking someone, matching or chatting.

### [restserver](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver)

The server is built on the Spring framework. The module consist of two classes, [ExciterApplication](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver/ExciterApplication), and [ServerController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/restserver/ServerController). The former starts up the server on localhost with default port 8080, while the latter is the controller that handles requests from clients.

### [ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui)

The ui module contains all of the front-end development of the application. This includes the App class which provides the framework for managing the JavaFX application, the AppStarter class that makes the client application packable to a single jar and the ClientHandler that handles the applications´ REST Client. Further there are six controller classes that implements the core and user modules to assemble the user interface.

- [ImageController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/ImageController): handles the images that are being swiped
- [LoginController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/LoginController): connected to login.fxml and enables a user to log in on the Xcitr-application
- [SignUpController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/SignUpController): connected to signup.fxml and manages the signup window where a new user can create a new account on the application
- [PrimaryController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/PrimaryController): connected to primary.fxml and manages the swiping-page where the user can like and match with other users
- [ProfileController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/ProfileController): connected to profile.fxml and manages the user information as a profile page
- [MatchController](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui/MatchController): connected to match.fxml and manages the users matches and communication with them

### [user](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user)

The user module contains a class that defines a user in the application, a class for the chat functionality, as well as a class that defines the bot users. This module is separate from the core logic so that the REST client and the REST server has equal access to these classes and methods. There are also two classes that deserializes the user and chat data so that it can be transmitted between the client and server.

- [BotUser](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/BotUser)
- [Chat](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/Chat)
- [ChatDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/ChatDeserializer)
- [User](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/User)
- [UserDeserializer](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/user/UserDeserializer)

## Architecture

![packageDiagram](/uploads/a82ce1e96d23c6d8e787218ed2ba2d68/packageDiagram.png)
