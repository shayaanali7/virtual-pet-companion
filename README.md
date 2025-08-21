Group 18 Virtual Pet Game
Welcome to Group 18's Virtual Pet Game, an interactive JavaFX-based simulator where you can feed your pet, buy it gifts, take it to the vet, and more!

Folder Structure
The workspace contains two folders by default, where:

src: the folder to maintain sources

lib: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the bin folder by default.

If you want to customize the folder structure, open .vscode/settings.json and update the related settings there.

Dependency Management
Dependencies are managed directly through Eclipse. More details on how to set up and configure dependencies can be found in Eclipse's official documentation.

Requirements
To run the game successfully, you will need the most recent version of Java (Java 24 or higher) as well as JavaFX. Follow these steps to configure your Eclipse project correctly:

Download Java: Install the latest version of Java from the official website: Java Downloads.

Download JavaFX: Ensure that you have the latest version of JavaFX from Gluon.

Set up Java and JavaFX in Eclipse:

In Eclipse, create a new Java project or open an existing one.

Add JavaFX to your project by right-clicking your project > Properties > Java Build Path > Libraries > Add External JARs..., and then select the JavaFX libraries.

Under VM Arguments, add the following to specify the JavaFX library path:

--module-path "path_to_javafx_lib" --add-modules javafx.controls,javafx.fxml
Replace "path_to_javafx_lib" with the path to your downloaded JavaFX libraries.

Running the Executable JAR
If you prefer to run the game as an executable JAR file, you can do so by using the following command:

java --module-path "path_to_javafx_lib" --add-modules javafx.controls,javafx.fxml -jar pathtojar.jar
Replace "path_to_javafx_lib" with the actual path to your JavaFX libraries, and replace pathtojar.jar with the path to the downloaded JAR file.

This will run the game with the necessary JavaFX modules included.