[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117)

# Group gr2117 repository
This is the coding project called Xcitr for group 2117´s dating application in the course IT1901. The project is git modified and will open in gitpod if you press the gitpod label above.

## Running, Building and Testing the Project
The project uses maven configuration to build and run the application. To build the project, enter 'mvn install' in the terminal from the **modules-exciter** folder. This must be done before you can run the application and its tests.

To run the application you have to be in the **ui** module, which can be *accessed* by using the command 'cd ui', and then using the command 'mvn javafx:run'.

To test the different modules you have to be in the desired module by using  'cd *module name*' and writing the command 'mvn test' in the terminal. 

## Modules-Exciter
The project is organized in the following structre. Code and modules can be found under modules-exciter. Within this folder one can find all of the core logic that makes up the backend of the app, along with the code for the frontend user interface and the file storage. Within modules-exciter there is also a README-file with a complete app description.

### Core
The core module contains the different classes that make up the back-end development of the app. It has four classes, where MatchListener is an interface that allows the User-class to register how many matches a user object has. While BotUser is an inherited class from User that ensures matches occurs:
- Exciter (main class)
- MatchListener (Action listiner interface)
- User (class that describes a user object)
- BotUser (class to ensure that the viewed "profiles" can like the user back even though they are not real users)

There is also a test class for the Exciter class that tests whether the like-function and match-function works how we intend it to. 

### JSON
There is only one class within the JSON module, which is a file handler class. This class saves information on who the user has liked on the app, as well as how many times they have liked the same person. This is necessary infromation to store so that the users can match with each other. There is also a file test class that makes sure the information is stored in the right place and format. 

### UI
The UI folder contains all of the front-end development of the app. This includes the app class which provides the framework for managing of JavaFX dating application. Then there are six controller classes that implements the two classes Exciter and User from the core module. 
- ImageController (controller to handle the images that are being swiped and loaded from the users computer)
- LoginController (controller connected to login.fxml that enables a user to login to the application)
- SignUpController (connected to signup.fxml and manages the signup window where a new user can sign up so their information is stored and can move on to use the app)
- PrimaryController (connected to primary.fxml and manages the swiping page where the user can like and match with other users)
- SecondaryController (connected to profile.fxml and manages the user information as a profile page)
- MatchController (connected to match.fxml and manages the users matches and communication with them)

There is also an app class in this module that launces the Xcitr application in Scenebuilder. 

UI testing

