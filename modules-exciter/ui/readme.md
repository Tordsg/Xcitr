# Readme for ui module

## PlantUML diagram for the ui module

![uiDiagram](/uploads/377314bb8d4c40e91331c96a6cd98b21/uiDiagram.png)

## Notes

Notes on some of the choices made in the ui module:

- In some of the controller classes we have getters for certain fxml objects in order to test and click on their children.

- ImageController: We have some dependencies in the ImageController class that are only used once. InputStream, ImageIO and SwingFXUtils are dependencies that are implemented so that it is possible to have images that are packed together on the standalone jar. If these are not used then the jar cannot be shipped standalone, because the images would have to be sent separately. This is a good solution for our application because the user only can choose their avatar from a selection of preloaded images, compared to uploading a personal profile picture from their computer.