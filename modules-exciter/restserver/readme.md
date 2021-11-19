# Restserver

## PlantUML diagram for how the restserver module interacts with the rest of the application

![restserver](/uploads/d032c8773b7bd7bcd3418d50935bcd80/restserver.png)


## RESt-api

The REST-api consists of different POST, GET and DELETE methods.

On the page /user it is a GET-method and a DELETE-method. The GET-method takes in a header "Authorization" of a users UUID and returns the currentuser of the application. In the DELETE-method there is a header "mail" with a mail-string. The method returns true if the user-object is deleted, false otherwise.

On the page /createAccount there is a POST-method which takes in a user-object as body and a header "Pass" as string. The method returns the user object if a new account is made.

On the page /user/matches there is a GET-method which takes in the header "Authorization" of a users UUID. The method returns the users matches.

On the page /login there is a POST-method which takes in the header "mail" with an email and a body of a password string. The methods returns the user if the user excists.

On the page /user/update there is a POST-method with header "Authorization" of a user UUID and a user-object as body. The method returns the updated user-object.

On the page /user/update/password there is a POST-method which has the header "Authorization" of a users UUID and a password-string as body. The method returns the updated user-object.

On the page /like there is a POST-method. The method takes in a header "Authorization" of a users UUID and a List of User-objects as body. The method returns a new random user-object if the users in the list excists.

On the page /two there is a GET-method which has a header "Authorization" of a users UUID. The method returns a list of two new user-object if the user from header excists.

On the page /user/likes there is a GET-method with header "Authorization" of a users UUID and a header "mail" of a mail-string. The method returns a number of likes between two user-objects if they excists and the user has liked the other one.

On the page /user/new there is a POST-method with header "Authorization" of a users UUID and a list of user-objects as body. Returns a new random user-object as long as there are more user-objects.

On the page /message there is a POST-method and a GET-method. In the POST-method there is a header "Autorization" of users UUID and a header "mail" of a mail-string. The method returns a chat if there is a chat between the two user-objects. In the GET-method there is a header "Authorization" of users UUID and a header "mail" of a mail-string. The method returns a chat if there is a chat between the two user-objects.




