package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import todoList.Adder;
import todoList.Task;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
//        this.adder = (Adder) adder.clone();
        // Serialization
        try
        {
            File actualFile = new File(dir, fileName);

            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(actualFile);
            ObjectOutputStream out = new ObjectOutputStream(file);

            System.out.println("Mpika edo pera");

            if(adder == null)
            {
                System.out.println("Einai null");
                out.close();
                file.close();
                return;
            }
            else
            {
                ArrayList<Task> list = adder.getTasks();

                for(Task value : list)
                {
                    System.out.println(value.getName());
                }
                System.out.println("Den einai Null");
            }

            // Method for serialization of object
            out.writeObject(adder);
            out.close();
            file.close();

            System.out.println("Object has been serialized");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void setAdder(Adder adder) throws CloneNotSupportedException {
        this.adder = (Adder) adder.clone();

        ArrayList<Task> list = this.adder.getTasks();

        for(Task value : list)
        {
            System.out.println(value.getName());
        }
    }

    @FXML
    public void handleKeyReleased() {
        String text = saveName.getText();

        // to isEmpty elegxei an exo string
        boolean emptyText = text.isEmpty() || text.trim().isEmpty();   // to trim afairei ta (spaces) ara me afto elegxo an exo dosei apla ena space i oxi

        if (!emptyText) {
            saveButton.setDisable(false);     // an den exo dosei kati i an exo dosei mono keno tote i apo pano metabliti ginetai true kai kano disable ta buttons mou
        }
        else
        {
            saveButton.setDisable(true);
        }
    }
}















