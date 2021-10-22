[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117)

# Group gr2117 repository
This is the coding project called Xcitr for group 2117´s dating application in the course IT1901. The project is git modified and will open in gitpod if you press the gitpod label above.

## Running, Building and Testing the Project
The project uses maven configuration to build and run the application. To build the project, enter 'mvn install' in the terminal from the **modules-exciter** folder. This must be done before you can run the application and its tests.

To run the application you have to be in the **ui** module, which can be *accessed* by using the command 'cd ui', and then using the command 'mvn javafx:run'.

To test the different modules you have to be in the desired module by using 'cd *module name*' and writing the command 'mvn test' in the terminal.

Not all of the functionalities in the app, like having profile pictures for the users, is functional in gitpod. A problem is also the Login and Create Account buttons on the login and signup page. These are programmed so that one should be able to press enter to indicate that all the information as been entered to the textfields, but when enter is pressed when the app runs in gitpod the entire app carshes. However when the app is run in vscode or locally on the computer all these functionalities work. 

## Modules-Exciter
The project is organized in the following structre. Code and modules can be found under modules-exciter. Within this folder one can find all of the core logic that makes up the backend of the app, along with the code for the frontend user interface and the file storage. Within modules-exciter there is also a README-file with a complete app description.

### Core
[Link to Core](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/core)

The core module contains the different classes that make up the back-end development of the app. It has three classes, where User is a person that can like other users and get matches. While BotUser is an inherited class from User that ensures matches occurs. Exciter is the class:
- Exciter (main class)
- User (class that describes a user object)
- BotUser (class to ensure that the viewed "profiles" can like the user back even though they are not real users)

There is also a test class for the Exciter class that tests whether the like-function and match-function works how we intend it to.


### JSON
[Link to JSON](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/json)

There is only one class within the JSON module, which is a file handler class. This class saves all the users who have created an account in Xcitr, as well as all of the information on who the user has liked on the app and how many times they have liked another user. This is necessary infromation to store so that the users can match with each other. There is also a file test class that makes sure the information is stored in the right place and format.


### UI
[Link to UI](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/modules-exciter/ui)

The UI folder contains all of the front-end development of the app. This includes the app class which provides the framework for managing the JavaFX dating application. Then there are six controller classes that implements the two classes Exciter and User from the core module.
- ImageController (controller to handle the images that are being swiped and loaded from the users computer)
- LoginController (controller connected to login.fxml that enables a user to login to the application)
- SignUpController (connected to signup.fxml and manages the signup window where a new user can sign up so their information is stored and can move on to use the app)
- PrimaryController (connected to primary.fxml and manages the swiping page where the user can like and match with other users)
- SecondaryController (connected to profile.fxml and manages the user information as a profile page)
- MatchController (connected to match.fxml and manages the users matches and communication with them)

There is also an app class in this module that launches the Xcitr application in Scenebuilder.

### Testing
To determine the test coverage of our code, Jacoco has been implemented to test how much of the different classes and modules have been tested. To view these files one can also enter 'mvn verify' in the terminal, and one can view these under target for each module. The quality of the code is also checked using spotbugs and checkstyle using the same command.






