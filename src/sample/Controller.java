package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loadLists.LoadTasks;
import todoList.Adder;
import todoList.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private static Adder adder = new Adder();
    private final LoadTasks loadTasks = new LoadTasks();
    private final SavingListController savingListController = new SavingListController();

    @FXML
    public MenuItem saveFile;
    @FXML
    private Button addButton;
    @FXML
    private ListView<String> myList;
    @FXML
    private TextArea descriptionText;
    @FXML
    private Label dateTask;
    @FXML
    private MenuItem openFile;

    private final ObservableList<String> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        openFile.setOnAction(event -> {
            if(loadTodoList())
            {
                ArrayList<Task> tempList = new ArrayList<>();    // list for old Tasks
                int flag = 0;

                if(!adder.getTasks().isEmpty())     // checking if i already have Tasks before open new file so i can Append the old ones with the new ones
                {
                    tempList = adder.getTasks();
                    data.clear();
                    myList.getItems().removeAll();
                    flag = 1;
                }

                adder = loadTasks.getAdder();

                for(Task value : adder.getTasks())
                {
                    data.add(value.getName());
                }

                if(flag == 1)     // adding Tasks I already have into new adder
                {
                    for (Task task : tempList) {
                        data.add(task.getName());
                    }

                    for(Task newValue : tempList)
                    {
                        adder.addNewTask(newValue);
                    }
                }
            }
        });

        myList.setItems(data);

        addButton.setOnAction( actionEven -> {

            FXMLLoader addNewItemLoader = new FXMLLoader(getClass().getResource("../fxml_files_ui/addNewTask.fxml"));
            // Creating new stage to name it or to cancel it
            Stage newStage = new Stage();

            try {
                newStage.setScene(new Scene(addNewItemLoader.load(), 554, 381));
                newStage.setAlwaysOnTop(true);
                newStage.setResizable(false);
                newStage.setTitle("Adding Task");
                newStage.initModality(Modality.APPLICATION_MODAL);

                SecondController addNewItemController = addNewItemLoader.getController();
                addNewItemController.setAdder(adder);

                newStage.showAndWait();

                Optional<String> result = addNewItemController.getNewItem();
                if (result.isPresent()) {
                    data.add(result.get());
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        });
    }

    public void changeDescDate(MouseEvent e)
    {
        ObservableList<String> s = myList.getSelectionModel().getSelectedItems();
        List<Task> list = new ArrayList<>(adder.getTasks());

        String test = s.toString();

        test = test.replace('[', ' ');
        test = test.replace(']', ' ');
        test = test.trim();


        for(Task value : list)      // looping through every Task and then changing description and date
        {
            if(value.getName().equals(test)) {
                descriptionText.setText(value.getDescription());
                dateTask.setText("Due Date: " + value.getDate().toString());
                return;
            }
        }
    }


    public void deleteTask(ActionEvent event)
    {
        ObservableList<String> s = myList.getSelectionModel().getSelectedItems();
        List<Task> list = new ArrayList<>(adder.getTasks());

        String test = s.toString();

        test = test.replace('[', ' ');
        test = test.replace(']', ' ');
        test = test.trim();

        for(Task value : list)
        {
            int index = adder.getTasks().indexOf(value);

            if(value.getName().equals(test)) {
                adder.getTasks().remove(index);
                myList.getItems().remove(index);
                descriptionText.setText("");
                dateTask.setText("Due Date: ");
                return;
            }
        }
    }


    public void saveToDoList(ActionEvent event)
    {

        if(!myList.getItems().isEmpty())        // if myList already has some tasks then i can save them
        {
            FXMLLoader addNewItemLoader = new FXMLLoader(getClass().getResource("../fxml_files_ui/savingFiles.fxml"));
            Stage savingStage = new Stage();

            try {
                savingStage.setScene(new Scene(addNewItemLoader.load(), 317, 159));
                savingStage.setAlwaysOnTop(true);
                savingStage.setResizable(false);
                savingStage.setTitle("Saving Task");
                savingStage.initModality(Modality.APPLICATION_MODAL);

                savingListController.setAdder(adder);

                savingStage.showAndWait();

            } catch (IOException | CloneNotSupportedException e) {
                System.out.println("OOps something went wrong\n" + e.toString());
            }
        }
        else    // if i don't have any task then I get a window saying i can't continue
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("You don't have any tasks to save");
            alert.setContentText("Press OK to continue");

            alert.showAndWait();
        }
    }


    public boolean loadTodoList()
    {
        boolean serialized = loadTasks.loadTodoList();

        if(serialized)
        {
            initialize();
            return true;
        }
        else
        {
            initialize();
            System.out.println("something went wrong");
            return false;
        }
    }
}



