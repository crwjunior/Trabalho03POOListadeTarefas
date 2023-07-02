package ToDoApp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Display {

    private Scanner scanner;
    private ToDoList toDoList;
    private FileHandler arquivoSalvo;
    private Print printer;
    private SimpleDateFormat dataForm;

    public Display() throws IOException, ClassNotFoundException {
        scanner = new Scanner(System.in);
        toDoList = new ToDoList();
        arquivoSalvo = new FileHandler();
        dataForm = new SimpleDateFormat("MM-dd-yyyy");

        FileHandler fileHandler = new FileHandler();
        toDoList.setToDoList(fileHandler.loadFromFile());
        printer = new Print(toDoList.getToDoList());
    }


    public String userInput() {
        return scanner.nextLine();
    }

    public void start() throws IOException, ClassNotFoundException {
        printer.printWelcome();
        response();
    }

    public void response() {
        while (true) {
            printer.printOptions();

            switch (userInput()) {
                case "1":
                    orderListOptions();
                    break;
                case "2":
                    addTask();
                    break;
                case "3":
                    editTask();
                    break;
                case "4":
                    quitAndSave();
                    return;
                default:
                    printer.printNotValiableOption();
            }
            printer.printPressEnterForMenu();
            userInput();
        }

    }

    public void addTask() {
        System.out.println("Add New Task \n");
        System.out.println("Name of Task:");

        Task task = new Task();
        task.setTitle(userInput());
        System.out.println("Name of Project");
        task.setProjectName(userInput());
        System.out.println("Due date? (MM-dd-yyyy)");

        while (true) {
            try {
                task.setDueDate(dataForm.parse(userInput()));
                break;
            } catch (ParseException e) {
                printer.printWrongDateFormat();
            }
        }

        Date date = new Date();
        String strDate = dataForm.format(date);
        try {
            task.setCreatedDate(dataForm.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        toDoList.addToDo(task);
        System.out.println("Tarefa: " + task.getTitle() + " foi adicionada");
    }

    public void quitAndSave() {
        printer.printWhenQuitApplication();
        try {
            arquivoSalvo.saveToFile(toDoList.getToDoList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTaskNumberAndChangeItToDone() {

        System.out.println("Qual tarefa você quer marcar como feita? \n ");
        printer.printOnlyIndexAndNameOfTask();
        System.out.println("\n Insira o numero em frente a tarefa que você quer marcar como feita(0 -> Retorne para o menu)");
        Task searched;

        while (true) {
            try {
                int getTaskByNumber = Integer.parseInt(userInput());

                if (getTaskByNumber != 0) {
                    searched = toDoList.getTaskInToDo(getTaskByNumber - 1);
                    break;
                } else {
                    response();
                }

            } catch (Exception e) {
                printer.printIndexOutOfReach();
            }
        }

        searched.setStatus(Status.DONE);
        System.out.println("Tarefa foi marcada como feita");
    }

    public void removeTask() {
        System.out.println("Qual projeto abaixo você quer deletar? \n");
        printer.printOnlyIndexAndNameOfTask();
        System.out.println("\n Insira o numero em frente a tarefa que você quer deletar(0 -> Retorne para o menu)");

        while (true) {
            try {
                int removeProjectByNumber = Integer.parseInt(userInput());
                if (removeProjectByNumber != 0) {
                    toDoList.remove(removeProjectByNumber - 1);
                    break;
                } else {
                    response();
                }
            } catch (Exception e) {
                printer.printIndexOutOfReach();
            }
        }

        System.out.println("Tarefa foi removida");

    }

    public void update() {
        //has a functionality of editing name, project and date of the project
        // get their name
        printer.printUpdateOptions();
        switch (userInput()) {
            case "0":
                response();
                break;
            case "1":
                editName();
                break;
            case "2":
                editProject();
                break;
            case "3":
                editDate();
                break;
            default:
                printer.printNotValiableOption();
                update();
        }
    }

    public void editName() {
        System.out.println("Aqui você pode editar o nome de uma de suas tarefas: \n ");
        printer.printOnlyIndexAndNameOfTask();
        System.out.println("\n Insira o numero em frente a tarefa que você quer trocar (0 -> Retorne para o menu)");
        Task searched;

        while (true) {
            try {
                int getTitleByNumber = Integer.parseInt(userInput());

                if (getTitleByNumber != 0) {
                    searched = toDoList.getTaskInToDo(getTitleByNumber - 1);
                    break;
                } else {
                    response();
                }
            } catch (Exception e) {
                printer.printIndexOutOfReach();
            }
        }

        System.out.println("Você quer mudar o nome de " + searched.getTitle() + " para?");
        String newName = userInput();
        searched.setTitle(newName);
        System.out.println("O nome agora é " + newName);
    }

    public void editProject() {
        System.out.println("Aqui você pode editar o nome de um projeto que sua tarefa foi designada:\n ");
        printer.printIndexAndNameAndProjectOfTask();
        System.out.println("\nInsira o numero em frente a tarefa que você quer trocar de projeto (0 -> Retorne para o menu)");
        Task searched;

        while (true) {
            try {
                int getProjectByNumber = Integer.parseInt(userInput());

                if(getProjectByNumber != 0){
                    searched = toDoList.getTaskInToDo(getProjectByNumber - 1);
                    break;
                }
                else{
                    response();
                }

            } catch (Exception e) {
                printer.printIndexOutOfReach();
            }
        }

        System.out.println("A que projeto " + searched.getTitle() + " deveria pertencer?");
        String newProject = userInput();
        searched.setProjectName(newProject);
        System.out.println(searched.getTitle() + " agora pertence ao projeto: " + newProject);
    }

    public void editDate() {
        System.out.println("Aqui você pode editar a data de uma tarefa a baixo:\n ");
        printer.printIndexAndNameAndDueDateOfTask();
        System.out.println("\n Insira o numero em frente a tarefa que você quer trocar de data (0 -> Retorne para o menu)");

        try {
            int getProjectByNumber = Integer.parseInt(userInput());

            if(getProjectByNumber != 0){
            Task searched = toDoList.getTaskInToDo(getProjectByNumber - 1);
            System.out.println("Insira uma nova data para a tarefa " + searched.getTitle() + " abaixo (MM-dd-yyyy)");

            while (true) {
                try {
                    searched.setDueDate(dataForm.parse(userInput()));
                    break;
                } catch (ParseException e) {
                    printer.printWrongDateFormat();
                }
            }
            System.out.println(searched.getTitle() + " A data foi mudada para " + dataForm.format(searched.getDueDate()));}
            else{
                response();
            }
        } catch (NumberFormatException e) {
            printer.printIndexOutOfReach();
            editDate();
        }
    }


    public void editTask() {
        //method with all edit subclasses
        printer.printEditTaskOptions();

        switch (userInput()) {
            case "0":
                response();
                break;
            case "1":
                removeTask();
                break;
            case "2":
                getTaskNumberAndChangeItToDone();
                break;
            case "3":
                update();
                break;
            default:
                printer.printNotValiableOption();
                editTask();
        }
    }


    public void orderListOptions() {
        printer.printSortingOptions();

        switch (userInput()) {
            case "0":
                response();
                break;
            case "1":
                printer.printEntireList();
                break;
            case "2":
                printer.printTasksByStatus(Status.PENDING);
                break;
            case "3":
                printer.printTasksByStatus(Status.DONE);
                break;
            case "4":
                printer.printTasksByProject();
                break;
            case "5":
                printer.printTaskByDueDate();
                break;
            default:
                printer.printNotValiableOption();
                orderListOptions();
        }
    }

}

