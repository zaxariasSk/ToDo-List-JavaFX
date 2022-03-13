/*  This class is used to get tasks saved in my computer
*   It does that by desirializing stored objects in file
*/



package loadLists;

import javafx.stage.FileChooser;
import todoList.Adder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadTasks {

    private Adder adder;

    public boolean loadTodoList() {

        try
        {
            String fileToOpen = null;
            String currentPath = getCurrentPath();
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(currentPath));

            //  set filter so i can only get .dat files
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Data files", "*.dat"));

            File selectedFile = fc.showOpenDialog(null);

            if(selectedFile != null)
            {
                fileToOpen = selectedFile.getName();
            }


            // Reading the object from a file
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(currentPath + "\\" + fileToOpen))) {
                // Method for deserialization of object
                adder = (Adder) in.readObject();
            }

            System.out.println("Object has been deserialized ");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        catch(ClassNotFoundException ex)
        {
            return false;
        }
        return true;
    }

    public Adder getAdder()
    {
        return adder;
    }


    public String getCurrentPath() {

        // taking my current Path
        Path currentRelativePath = Paths.get("");
        String dir = currentRelativePath.toAbsolutePath().toString();
        dir = dir + "\\src\\savedLists";        // default save location for to-do lists

        dir = dir.replaceAll("\\\\", "\\\\\\\\");
        return dir;
    }
}