package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import todoList.Adder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SavingListController implements Initializable {

    @FXML
    public Button saveButton;
    @FXML
    private TextField saveName;

    private String fileName;
    private String dir;
    private static Adder adder;


    public SavingListController()
    {
        fileName = "";
        dir = "";
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveButton.setOnAction(event -> {

            boolean exists = listFilesForFolder();   // checking if the given name already exists


            if(exists)      // if the file exists
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(saveButton.getScene().getWindow());
                alert.setTitle("Information Dialog");
                alert.setHeaderText("You already have a file with the same name");
                alert.setContentText("Change the name and continue");

                alert.showAndWait();
            }
            else
            {
                try {
                    saveTodoList();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(saveButton.getScene().getWindow());
                alert.setTitle("Information Dialog");
                alert.setHeaderText("You saved your data successfully");

                alert.showAndWait();
                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            }

        });
    }

    //
    public boolean listFilesForFolder() {

        // taking my current Path
        Path currentRelativePath = Paths.get("");
        dir = currentRelativePath.toAbsolutePath().toString();
        dir = dir + "\\src\\savedLists";

        dir = dir.replaceAll("\\\\", "\\\\\\\\");

        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        fileName = saveName.getText();
        fileName = fileName + ".dat";

        for (File file : listOfFiles)
        {
            if (file.isFile() && fileName.equals(file.getName()))
            {
                    return true;
            }
        }
        return false;
    }

    private void saveTodoList() throws CloneNotSupportedException {
        // Serialization
        File actualFile = new File(dir, fileName);
        try ( FileOutputStream file = new FileOutputStream(actualFile);
              ObjectOutputStream out = new ObjectOutputStream(file);)       //Saving an object in a file
        {

            if(adder == null)
                return;

            // Method for serialization of object
            out.writeObject(adder);

            System.out.println("Object has been serialized");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void setAdder(Adder adder) throws CloneNotSupportedException {
        this.adder = (Adder) adder.clone();
    }

    @FXML
    public void handleKeyReleased() {
        String text = saveName.getText();

        boolean emptyText = text.isEmpty() || text.trim().isEmpty();

        if (!emptyText) {
            saveButton.setDisable(false);
        }
        else
        {
            saveButton.setDisable(true);
        }
    }
}















