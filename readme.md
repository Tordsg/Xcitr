[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117)

# Group gr2117 repository
This is the coding project called Xcitr for group 2117´s dating application in the course IT1901. The project is git modified and will open in gitpod if you press the gitpod lable above.

## Running, Building and Testing the Project

## Modules-Exciter
The project is organized in the following structre. Code and modules can be found under modules-exciter. Within this folder one can find all of the core logic that makes up the app, along with the code for the user interface and the file storage. Within this module there is also a README-file with a complete app description.

### Core
The core module contains the different classes that make up the back-end development of the app. It has four classes, where MatchListener is an interface that allows the User-class to register how many matches a user object has:
- Exciter (main class)
- MatchListener (Action listiner interface)
- User (class that describes a user object)

There is also a test class for the Exciter class that tests whether the like-function and match-function works how we intend it to. 

### JSON
#### FileHandler
There is only one class within the JSON module, which is a file handler class. This class saves information on who the user has liked on the app, as well as how many times they have liked the same person. This is necessary infromation to store so that the users can match with each other. There is also a file test class that makes sure the information is stored in the right place and format. 

#### Test

### UI
The UI folder contains all of the front-end development of the app. This includes the app class which provides the framework for managing of JavaFX dating application. Then there are to controller classes that implements the two classes Exciter and User from the core module. The primary controller manage the profile-card page where the user can like other users. The secondary controller connects the User logic with the profile page in the app. 

