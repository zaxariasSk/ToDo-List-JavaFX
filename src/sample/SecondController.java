package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import todoList.Adder;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            returnValue = Optional.of(taskName.getText());      // I return taskName value in Controller
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();

        });

        // if user press cancel button
        cancelButton.setOnAction(actionEvent ->
        {
            returnValue = Optional.empty();     // if i have nothing then i return null
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        });
    }


    @FXML
    public void handleKeyReleased() {
        String text = taskName.getText();
        String text2 = description.getText();
        LocalDate date = dateTask.getValue();

        // isEmpty() is checking if i have any Strings
        boolean emptyText = text.isEmpty() || text.trim().isEmpty();   // Using trim to delete all white spaces so i can check if user gave any input
        boolean emptyDesc = text2.isEmpty() || text2.trim().isEmpty();
        boolean emptyDate = dateTask.getValue() == null;

        if (!emptyText && !emptyDesc && !emptyDate) {
            nextButtonName.setDisable(false);       // if any of these is empty then I set my nextButton to false so the user cannot continue
        }
        else
        {
            nextButtonName.setDisable(true);
        }
    }

    public void setAdder(Adder adder)
    {
        this.adder = adder;
    }

}
