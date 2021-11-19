# Restserver

## PlantUML diagram for how the restserver module interacts with the rest of the application

<p align="center">
    <img width="700" src="/uploads/d032c8773b7bd7bcd3418d50935bcd80/restserver.png">
</p>

## REST-api

The REST-api consists of different pages which uses POST, GET and DELETE methods.

All requested bodies must be in a application/json format. Many of the mapping-methods requires a body of a User object. The User object should be in this format:

{
    "name" : "Name",
    "age" : 17,
    "email" : "name@mail.no"
    
}

More variables can be added to the User object.

In the bodies that requests a string should have a format with brackets and the the string as here:
{
    "123abc"
}

This applies to where the body is a password-string or mail-string.

In the bodies that requests a list of users the format should be like this:

[
    {
        "id": null,
        "name": "Derik",
        "age": 27,
        "userInformation": "",
        "email": "Derik@mail.no",
        "likedUsers": {},
        "matches": [],
        "imageId": 5,
        "likeBack": false
    },
    {
        "id": null,
        "name": "Joe",
        "age": 19,
        "userInformation": "Superman is the best movie ever and I am searching for someone to share it with",
        "email": "Joe@mail.no",
        "likedUsers": {},
        "matches": [],
        "imageId": 4,
        "likeBack": false
    }
]


- On the page **/user** there is a GET-method and a DELETE-method. The GET-method takes in a header "Authorization" of a users UUID and returns the current user of the application. In the DELETE-method there is a header "mail" with a mail-string. The method returns true if the User object is deleted, and false otherwise.

- On the page **/createAccount** there is a POST-method which takes in a User object as a body, and a header "Pass" as string. The method returns the User object if a new account is made.

- On the page **/user/matches** there is a GET-method which takes in the header "Authorization" of a users UUID. The method returns the users matches.

- On the page **/login** there is a POST-method which takes in the header "mail" with an email, and a body of a password-string. The methods returns the user if the user excists.

- On the page **/user/update** there is a POST-method with header "Authorization" of a user UUID, and a User object as body. The method returns the updated User object.

- On the page **/user/update/password** there is a POST-method which has the header "Authorization" of a users UUID, and a password-string as body. The method returns the updated User object.

- On the page **/like** there is a POST-method. The method takes in a header "Authorization" of a users UUID, and a List of User objects as body. The method returns a new random User object if the users in the list excists.

- On the page **/two** there is a GET-method which has a header "Authorization" of a users UUID. The method returns a list of two new User object if the user from header excists.

- On the page **/user/likes** there is a GET-method with header "Authorization" of a users UUID, and a header "mail" of a mail-string. The method returns a number of likes between two User objects if they excists and the user has liked the other one.

- On the page **/user/new** there is a POST-method with header "Authorization" of a users UUID, and a list of User objects as body. Returns a new random User object as long as there are more User objects.

- On the page **/message** there is a POST-method and a GET-method. In the POST-method there is a header "Autorization" of users UUID, and a header "mail" of a mail-string. The method returns a chat if there is a chat between the two User objects. In the GET-method there is a header "Authorization" of users UUID, and a header "mail" of a mail-string. The method returns a chat if there is a chat between the two User objects.
