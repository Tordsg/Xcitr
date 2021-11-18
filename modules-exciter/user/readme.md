# User 

## Sequence diagram for a match on the app

Below is a sequence diagram that illustrates how a user gets a match with another user on the application.

![sequenceDiagram_of_match](/uploads/710da31f6e281cbdccb37fdd10625543/sequenceDiagram_of_match.png)

## Notes for the user module

- Testing: The deserializing classes are not tested directly in the user module, but is rather indirectly tested by all of the other test classes because of the nature of a deserializing class. We know that these classes are well functioning because the only way to read, write and retrieve information through the REST API regarding a user and the chats are by converting the json strings to objects using deserialization.
