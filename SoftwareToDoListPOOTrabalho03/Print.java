package ToDoApp;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Print {

    private List<Task> listOfToDos;
    private SimpleDateFormat sdf;

    Print(List<Task> listOfToDos) {
        this.listOfToDos = listOfToDos;
        this.sdf = new SimpleDateFormat("MM-dd-yyyy");
    }

    public void printHeading() {
        System.out.print(String.format("%1$-27s", "TASK"));
        System.out.print(String.format("%1$-27s", "PROJECT"));
        System.out.print(String.format("%1$-27s", "STATUS"));
        System.out.print(String.format("%1$-27s", "DATE CREATED"));
        System.out.println("DUE DATE");
        System.out.println("--------------------------------" +
                "--------------------------------------------" +
                "----------------------------------------");
    }

    public void printEntireList() {
        printHeading();

        listOfToDos.forEach(this::printBody);
    }


    public void printTasksByStatus(Status status) {
        printHeading();

        listOfToDos.stream()
                .filter(task -> task.getStatus() == status)
                .forEach(this::printBody);

    }

    public void printTasksByProject() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Select a Project below, by typing its name:");

        printIndexAndNameAndProjectOfTask();
        String project = reader.nextLine().toLowerCase().trim();

        printHeading();

        listOfToDos.stream()
                .filter(task -> task.getProjectName().toLowerCase().trim().equals(project))
                .forEach(this::printBody);

    }

    public void printBody(Task task) {

        System.out.print(listOfToDos.indexOf(task) + 1 + ". ");
        System.out.print(String.format("%1$-25s", task.getTitle()));
        System.out.print(String.format("%1$-25s", task.getProjectName()));
        System.out.print(String.format("%1$-28s", task.getStatus()));
        System.out.print(String.format("%1$-25s", sdf.format(task.getCreatedDate())));
        System.out.print(String.format("%1$-25s", sdf.format(task.getDueDate())));
        System.out.println("");
    }

    public void printTaskByDueDate() {
        printHeading();
        listOfToDos.sort(Comparator.comparing(Task::getDueDate));
        listOfToDos.stream().forEach(this::printBody);
    }

    public void printOnlyIndexAndNameOfTask() {
        for (Task list : listOfToDos) {
            System.out.print(listOfToDos.indexOf(list) + 1 + ". ");
            System.out.println(String.format("%1$-25s", list.getTitle()));
        }
    }

    public void printIndexAndNameAndProjectOfTask() {
        for (Task list : listOfToDos) {
            System.out.print(listOfToDos.indexOf(list) + 1 + ". ");
            System.out.print(String.format("%1$-25s", list.getTitle()));
            System.out.print(String.format("%1$-25s", list.getProjectName()));
            System.out.println();
        }
    }

    public void printIndexAndNameAndDueDateOfTask() {
        for (Task list : listOfToDos) {
            System.out.print(listOfToDos.indexOf(list) + 1 + ". ");
            System.out.print(String.format("%1$-25s", list.getTitle()));
            System.out.print(String.format("%1$-25s", sdf.format(list.getDueDate())));
            System.out.println();
        }
    }

    public void printWelcome() {

        System.out.println("\n Welcome to TO-DO application");
        System.out.println(" Pending tasks: " + getBackAmount(Status.PENDING) + " | Completed tasks: " + getBackAmount(Status.DONE));
        System.out.println("\n Pick an option:");
    }

    public int getBackAmount(Status status){
        int counter = 0;

        for(Task list: listOfToDos){
            if(list.getStatus()== status){
                counter++;
            }
        }
        return counter;
    }

    public void printOptions() {
        System.out.println("---------------------------------------------");
        System.out.println(" (1) Show Task List:");
        System.out.println(" (2) Add New Task");
        System.out.println(" (3) Edit Task (remove, mark as done, update)");
        System.out.println(" (4) Save and Quit");
    }
    public void printNotValiableOption() {
        System.out.println("You have not entered a viable option. Let's try this again. \n");
    }

    public void printWrongDateFormat() {
        System.out.println("Input of date was in wrong format. REQUIRED FORMAT: (MM-dd-yyyy)");
    }

    public void printWhenQuitApplication() {
        System.out.println("You have quit the application, your TO-DO list is saved.");
    }

    public void printIndexOutOfReach() {
        System.out.println("Task with selected index does not exist. Select number in front of task again:");
    }

    public void printUpdateOptions() {
        System.out.println("Insira (1) para editar o nome da tarefa");
        System.out.println("Insira (2) para editar o nome do projeto de uma tarefa especifica");
        System.out.println("Insira (3) para editar a data de uma tarefa");
    }

    public void printEditTaskOptions() {
        System.out.println("Insira (1) para remover itens da lista");
        System.out.println("Insira (2) para marcar tarefas como feitas");
        System.out.println("Insira (3) para atualizar tarefas");
    }

    public void printSortingOptions() {
        System.out.println("Aqui você pode imprimir as listas ordenadas por:");
        System.out.println("(1): Mostrar todas");
        System.out.println("(2): Todas as tarefas pendentes");
        System.out.println("(3): Todas as tarefas feitas");
        System.out.println("(4): Imprimir a tarefa pelo projeto:");
        System.out.println("(5): Data");
    }

    public void printPressEnterForMenu() {
        System.out.println("\n ENTER para ver as opções de novo");
    }
}
