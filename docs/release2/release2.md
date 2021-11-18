# Release 2

The aim of this release is to make the app more functional with more sufficient persistence and a more sophisticated ui. The core logic will be expanded to support a greater input of user information, including an appropriate hash for the users password. For this release there will not be implemented cloud-based storage so that real users can use the app simoultaneously, meaning that there will be added a BotUser class to make sure some of the profiles the user likes also can like the user back to simulate matches. There will also be added a login and signup page, together with a match page where the user will be able to see all the people he or she has matched with. 

## User Stories

The goals and milestones for this second release are implemented from us-4, us-5 and us-6, more thoroughly explained in [userstories.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/userstories.md). In short they set three main requirements for this release:

1. being able to see an overview of all the matches a user has

2. sign up page

3. log in page

## Progress plan

### Core

The goal for the app logic is to expand the user class so that the app users can input and store more information about themselves. This includes information on their name, age, email, password and biographical information. There also needs to be a one-to-one correlation between the user, through email for example, and the image they choose to upload as their profile picture. Another aim is for the user to get matches with other users, meaning there needs to be a BotUser class that can "like" the user back to simulate getting a match. Because of this new BotUser class the main logic class, Exciter, also needs to be updated to make sure it can register the BotUsers likes.

### Persistence

The goal for persistence in this release is to expand it so that it can store all of the user information such as name, age, email, password and bio, including all of the people the user has matched with and who they like. Because this release will include both a sign up and login page the app also needs to be able to store multiple user objects and to be able to iterate through them so that an already registered user can log in again. This means that this release will use of both explicit and implicit saving. When a user signs up for the app, their information is explicitly saved when the 'create account' button is pressed. Also on the profile page the user will be able to update their bio, password, etc., excplicitly through a 'save' button. Information will be saved implicitly when the user likes other profiles and matches with them.

### UI

For release 2 the goal is to expand the ui substantially with a login page, signup page and a match page. Additionally its aimed to update the user interface so that it stands more in line with the figma models from release 1. The user should be able to switch between the login and signup page depending on whether they already have an account or not. From the swiping page the user should be able to move to their profile page where their email and bio can be updated. Here they will also have the opportunity to log out of the app. The user can also move to the match page where there will be an overview of everyone they have matched with as well as some form of communication platform. All of these windows or pages will follow the same graphical profile.

### Testing

Another goal for this release is to implement more testing coverage for both the persistence and the ui. This expansion is natural considering the additional functionality and complexity that will be added to the application.


