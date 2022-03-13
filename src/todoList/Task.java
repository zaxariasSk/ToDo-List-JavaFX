package todoList;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 3234030596489659385L;
    private final String name;
    private final String description;
    private final LocalDate date;

    public Task(String name, String description, LocalDate date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate()
    {
        return date;
    }

}
