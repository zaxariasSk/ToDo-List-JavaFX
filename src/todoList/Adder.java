package todoList;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Adder implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 3569041702857111116L;
    private static final ArrayList<Task> tasks = new ArrayList<>();     // TODO an afaireso kapoio stoixeio apo ti lista den to sbinei
    private ArrayList<Task> task2 = tasks;

    public void addNewTask(String name, String description, LocalDate date) {

        if(name != null && description != null && date != null)
        {
//            System.out.println("Name: " + name + "\n" + "Description: " + description + "\n" + "Date: " + date.toString());
            task2.add(new Task(name,description, date));
        }
//        System.out.println(tasks.size());
    }


    public void addNewTask(Task task)       // this methos is used only if I already have tasks. It is used in load file method in Controller
    {
        task2.add(task);
    }

    public ArrayList<Task> getTasks()
    {
        printList();
        return task2;
//      return new ArrayList<>(task2);
    }

    public void printList() {
        System.out.println("Mpika sti getTask");
        for(Task value : task2)
        {
            System.out.println(value.getName());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }


    public void setTask2(ArrayList<Task> task)
    {
        this.task2 = task;
    }


}

