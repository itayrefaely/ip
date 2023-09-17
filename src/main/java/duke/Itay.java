package duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Itay {

    static ArrayList<Task> tasks = new ArrayList<>(100);
    static int numTasks = 0;
    static String DIVIDER = "------------------------------------------------------------";
    
    public static void respond(String input) throws DukeException {
        System.out.println(DIVIDER);
        String splitInput[] = input.split(" ");
        String indicator = splitInput[0];

        switch(indicator) {
            case("list"):
                printList();
                break;
            case("mark"):
                handleMark(splitInput);
                break;
            case("unmark"):
                handleUnmark(splitInput);
                break;
            case("todo"):
                handleTodo(input, splitInput);
                break;
            case("deadline"):
                handleDeadline(input);
                break;
            case("event"):
                handleEvent(input);
                break;
            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public static void printList() {
        System.out.println("Here are the tasks in your list:");
            for(int i = 0; i < numTasks ; i++) {
                System.out.println(tasks.get(i).toString());
            }
        System.out.println(DIVIDER);
    }

    public static void handleMark(String[] splitInput) throws DukeException {
        int taskIdx = getTaskIndex(splitInput);
        tasks.get(taskIdx).setStatus(true);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks.get(taskIdx).toString());
        System.out.println(DIVIDER);
    }

    public static void handleUnmark(String[] splitInput) throws DukeException {
        int taskIdx = getTaskIndex(splitInput);
        tasks.get(taskIdx).setStatus(false);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks.get(taskIdx).toString());
        System.out.println(DIVIDER);
    }

    public static int getTaskIndex(String[] splitInput) throws DukeException {
        int taskIdx;
        String errorMessage = "OOPS!!! Please enter a valid task number.";

        try {
            taskIdx = Integer.parseInt(splitInput[1]) - 1;
            if (taskIdx < 0 || taskIdx >= numTasks) {
                throw new DukeException(errorMessage);
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException inputEx) {
            throw new DukeException(errorMessage);
        }
        return taskIdx;
    }

    public static void handleTodo(String input, String[] splitInput) throws DukeException {
        if(splitInput.length == 1) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        String description = input.substring(input.indexOf(' ') + 1);
        Task task = new Task(description, 'T');
        tasks.set(numTasks, task);
        printAddTask();
    }

    public static void handleDeadline(String input) throws DukeException {
        try {
            int firstSlashIdx = input.indexOf('/');

            String description = input.substring(input.indexOf(' ') + 1, firstSlashIdx - 1);
            Task task = new Task(description, 'D');

            String temp = input.substring(firstSlashIdx + 1);
            String deadline = temp.substring(temp.indexOf(' '));
            deadline.trim();
            task.setDeadlineTime(deadline);

            tasks.set(numTasks, task);
            printAddTask();
        } catch (StringIndexOutOfBoundsException | IllegalArgumentException inputEx) {
            throw new DukeException("OOPS!!! Description of deadline command must be of form: deadline ___ /by ___");
        }
    }

    public static void handleEvent(String input) throws DukeException {
        try {
            int firstSlashIdx = input.indexOf('/');
            int secondSlashIdx = input.indexOf('/', firstSlashIdx + 1);

            String description = input.substring(input.indexOf(' ') + 1, firstSlashIdx - 1);
            Task task = new Task(description, 'E');

            String temp = input.substring(firstSlashIdx);
            firstSlashIdx = temp.indexOf('/');
            secondSlashIdx = temp.indexOf('/', firstSlashIdx + 1);
            String startTime = temp.substring(temp.indexOf(' ') + 1, secondSlashIdx - 1);
            temp = temp.substring(secondSlashIdx);
            String endTime = temp.substring(temp.indexOf(' '));
            endTime.trim();
            
            task.setEventTime(startTime, endTime);

            tasks.set(numTasks, task);
            printAddTask();
        } catch (StringIndexOutOfBoundsException | IllegalArgumentException inputEx) {
            throw new DukeException("OOPS!!! Description of event command must be of form: event ___ /from ___ /to ___");
        } 
    }
    
    public static void printAddTask() {
        System.out.println("Got it. I've added this task:");   
        System.out.println(tasks.get(numTasks).toString());   
        numTasks++;
        System.out.println("Now you have " + numTasks + " tasks in the list.");
        System.out.println(DIVIDER);   
    }

    public static void main(String[] args) throws DukeException {
        System.out.println("Hello! I'm Itay");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);

        try (Scanner in = new Scanner(System.in)) {
            String input = in.nextLine();
            input = input.trim();

            while(! input.equals("bye")) {
                try {
                    respond(input);
                } catch(DukeException dukeEx) {
                    System.out.println(dukeEx.toString());
                }
                // Get the next input
                input = in.nextLine();
                input = input.trim();
            }
        }

        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }
}
