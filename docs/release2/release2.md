# Release 2
The aim of this release is to make the app more functional with more sufficient persistence and a more sophisticated ui. The core logic will be expanded to support a greater input of user information, including an appropriate hash for the users password. For this release there will not be implemented cloud-based storage so that real users can use the app simoultaneously, meaning that there will be added a BotUser class to make sure some of the profiles the user likes also can like the user back. There will also be added a login and signup page, together with a match page where the user will be able to see all the people he or she has matched with. 

## Progress plan
### Core
The goal for the app logic is to expand the user class so that the app users can input and store more information about themselves. This includes information on their name, age, email, password and biographical information. There also needs to be a one-to-one correlation between the user, through email for example, and the image they choose to upload as their profile picture. Another aim is for the user to get matches with other users, meaning there needs to be a BotUser class that can "like" the user back to simulate getting a match. Because of this new BotUser class the main logic class, Exciter, also needs to be updated to make sure it registers the BotUsers likes.

### Persistence
The goal for persistence in this release is to expand it so that it can store all of the user information, including all matches and likes. Because this release will include both a sign up and login page the app needs to be able to store multiple user objects and to be able to iterate through them.

### UI
For release 2 the goal is to expand the ui substantially with a login page, signup page and a match page. Additionally its aimed to update the user interface so that it stands more in line with the figma models from release 1. The user should be able to switch between the login and signup page depending on whether they already have a profile on the app. From the swiping page the user shoudl be able to move to their profile page where their email and bio can be updated. Here they will also have the opportunity to log out of the app. The user can also move to the match page where there will be an overview of everyone they have matched with as well as some form of communication platform. All of these windows or pages will follow the same graphical profile.

### Testing 
Another goal for this release is to implement more testing coverage for both the persistence and the ui. This expansion is natural considering the additional functionality and complexity that will be added to the application. The our goal is not to test all of the UI but at least a few of the controller classes and then move on to finish these in release 3. 

Legg in screenshots av testdekningsgraden

Skriv om vi velger implisitt lagring div (buker dette til dette og dette til dette)

hva gjæør vi for å oppnå milepælene våre

Alt nyttig hvis noen andre skulle tatt over prosjektet vårt

