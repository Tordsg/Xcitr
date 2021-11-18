# Release 1

The aim of this release is to make a minimal viable product of the Xcitr dating app so that it has sufficient logic, persistence and ui implemntation. It should be functional to the extent where an already stored user can favour other stored users against each other and match with them. The user should also be able to access a profile page where he or she has the opportunity to see their profile and bio on the app.

## User stories

The goals for the first release are linked to three user stories: us-1, us-2 and us-3. These are more thoroughly explained in [userstories.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2117/gr2117/-/tree/master/userstories.md), but in short they set three requirements that must be met for the release:

1. being able to compare to people against each other

2. like someone three times in a row before they can match with that user

3. have a profile page on the app

## Work plan for the release

### core logic

The goal for the core is to make a class that can represent a user and all of the information that will be available for them to input when they in the future sign up to use the application. This class should also implement logic to check how many matches and likes this user has. To do this there will be an implementation of a listener interface that can check if the user has a match and wheter the user likes another person.

There must also be a main class for the app logic. For the first release this class will contain some placeholder users to make sure we can test the minimal function, which includes mostly set- and get-functions regarding the different users that appear on the primary window of the app. This class must also implement some like-function so that the user object can be "informed" on how many times they have liked someone.

### persistence

For the persistence a JSON module is implemented to make sure matches and likes are saved and stored in a local file. Only one FileHandler class is needed to make sure the app can save and read users, as well as creating a file to store this information.

### ui

The goal for the ui is to have a matching page where Ulf Reidar can be presented with different persons and choose which one he likes best. This page will also have a profile button where one should get access to the profile page. Hence there will only be made two fxml and controller classes for this release together with an App-class.

## Illustration

Below are three figma frames roughly describing how the applications matching page is going to look like after it is finished.

1. First the user is presented with two profile cards, where only the profile picture, the name and the age is displayed. In the top right corner is a small profile picture of the user. If this is pressed the user will be directed to their profile where they can edit their information, pictures, preferences, etc. The Xcitr-icon in the bottom right corner can be pressed to get an overview of all the people the user has matched with.

![pic1](/uploads/ee0e85422ef1d66505f52fc82cdee24e/pic1.png)

2. When the user likes one of the profiles more than the other, she swipes the card upwards. Here you can see that there is a counter 1/3 describing how many times the user has liked this persons profile. When this ever reaches 3/3 there will be a match.

![pic2](/uploads/3fc1b05c6dd29303bffa0b1cdf67db1c/pic2.png)

3. After swiping to tell the application which profile is the preferred one, this card will fall back onto the screen together with a new profile. The user can now compare the person they have already liked to a new profile and see if this person is the most likable. If the user is unsure on which profile she prefers, then she can press the profile card and view the profiles bio.

![pic3](/uploads/e0c708d613f6f114a24e87a0832fc717/pic3.png)