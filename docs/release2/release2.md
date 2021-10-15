# Release 2
The aim of this release is to make the app more functional with more sufficient persistence and a more sophisticated ui. The core logic will be expanded to support a greater input of user information, including an appropriate hash for the users password. For this release there will not be implemented cloud-based storage so that real users can use the app simoultaneously, meaning that there will be added a BotUser class to make sure some of the profiles the user likes also can like the user back. There will also be added a login and signup page, together with a match page where the user will be able to see all the people he or she has matched with. 

## User Story
After a year and a half living in a global pandemic, Ulf Reidar has recognized an increased need to connect with new people. He finds that he does not have a platform where he can get in touch with people with the same interests as him without being forced to meet more people than he is comfortable with. Ulf wants an app or webpage where he can find a meaningful connection that goes beyond just swiping on people left and right. He wants to find a new friend, girlfriend or companion who has some of the same interests as he does, like camping and knitting by the fire. And he wants to be certain that there is almost a guaranteed mutual interest between him and this new person. After being “introduced” he wants to be able to get in touch and meet this person in real life. Being comfortable that it is safe also from a pandemic viewpoint.

Due to this he wants an application where two persons are presented to him so that he has the opportunity to compare to different persons on the app. If he can "choose" this person over someone else multiple times then he can also be more sure of his own interest for the other user. When he matches with someone he would like the application to provide an overview of everyone he has matched with, together with some form of communication platform. He wants to be able to login and logout as he pleases but not loose any of his matches when he logs out. 

## Progress plan
### Core
The goal for the app logic is to expand the user class so that the app users can input and store more information about themselves. This includes information on their name, age, email, password and biographical information. There also needs to be a one-to-one correlation between the user, through email for example, and the image they choose to upload as their profile picture. Another aim is for the user to get matches with other users, meaning there needs to be a BotUser class that can "like" the user back to simulate getting a match. Because of this new BotUser class the main logic class, Exciter, also needs to be updated to make sure it registers the BotUsers likes.

### Persistence
The goal for persistence in this release is to expand it so that it can store all of the user information, including all matches and likes. Because this release will include both a sign up and login page the app needs to be able to store multiple user objects and to be able to iterate through them.

### UI
For release 2 the goal is to expand the ui substantially with a login page, signup page and a match page. Additionally its aimed to update the user interface so that it stands more in line with the figma models from release 1. The user should be able to switch between the login and signup page depending on whether they already have a profile on the app. From the swiping page the user shoudl be able to move to their profile page where their email and bio can be updated. Here they will also have the opportunity to log out of the app. The user can also move to the match page where there will be an overview of everyone they have matched with as well as some form of communication platform. All of these windows or pages will follow the same graphical profile.

### Testing 
Another goal for this release is to implement more testing coverage for both the persistence and the ui. This expansion is natural considering the additional functionality and complexity that will be added to the application. 

## Illustration