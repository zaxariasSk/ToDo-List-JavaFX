package loadLists;

import javafx.stage.FileChooser;
import todoList.Adder;
import todoList.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadTasks {

    private Adder adder;

    public boolean loadTodoList() {

        try
        {
            String fileToOpen = null;
            String currentPath = getCurrentPath();
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(currentPath));
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



            ArrayList<Task> list = adder.getTasks();

            for(Task value : list)
            {
                System.out.println(value.getName());
            }

            System.out.println("Object has been deserialized ");
        }

        catch(IOException ex)
        {
            ex.printStackTrace();
//            System.out.println("Edo: IOException is caught");
            return false;
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
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
        dir = dir + "\\src\\savedLists";

        dir = dir.replaceAll("\\\\", "\\\\\\\\");
        return dir;
    }


}


// "C:\\Users\\Zack\\IdeaProjects\\ToDo List\\src\\savedLists\\test.dat"