package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import todoList.Adder;
import todoList.Task;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SecondController implements Initializable {
    private String name;
    private String whatToDo;
    private Optional<String> returnValue;
    private Adder adder;

    @FXML
    private Button nextButtonName;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField taskName;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker dateTask;


    public Optional<String> getNewItem() {
        return returnValue;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nextButtonName.setDisable(true);

        nextButtonName.setOnAction((actionEvent) ->
        {
            name = taskName.getText();
            whatToDo = description.getText();

            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            LocalDate date = dateTask.getValue();

            adder.addNewTask(name, whatToDo, date);

            returnValue = Optional.of(taskName.getText());      // bazo sto return ti timi tou taskName kai meta tin epistrefo sto proto controller
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        });

        cancelButton.setOnAction((actionEvent) ->
        {
            returnValue = Optional.empty();     // an den exo tpt tote girizo null
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        });
    }


    @FXML
    public void handleKeyReleased() {
        String text = taskName.getText();
        String text2 = description.getText();
        LocalDate date = dateTask.getValue();

        // to isEmpty elegxei an exo string
        boolean emptyText = text.isEmpty() || text.trim().isEmpty();   // to trim afairei ta (spaces) ara me afto elegxo an exo dosei apla ena space i oxi
        boolean emptyDesc = text2.isEmpty() || text2.trim().isEmpty();
        boolean emptyDate = dateTask.getValue() == null;

        if (!emptyText && !emptyDesc && !emptyDate) {
            nextButtonName.setDisable(false);     // an den exo dosei kati i an exo dosei mono keno tote i apo pano metabliti ginetai true kai kano disable ta buttons mou
        }
        else
        {
            nextButtonName.setDisable(true);
        }
    }

    public void setAdder(Adder adder)
    {
        this.adder = adder;

        List<Task> list = new ArrayList<>(this.adder.getTasks());

        System.out.println("\nFrom setAdder\n");

        for(Task value : list)
        {
            System.out.println(value.getName());
        }

        System.out.println("\nEnd of setAdder\n");

    }

}
