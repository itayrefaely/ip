package duke;

/**
 * The Parser class is responsible for parsing user input and executing the corresponding actions.
 * It interprets user commands and delegates the execution to other classes within the Duke application.
 */
public class Parser {

    /**
     * Parses the user's input and performs the corresponding action.
     *
     * @param input The user's input command.
     * @throws DukeException If there's an issue parsing or executing the command.
     */
    public void parse(String input) throws DukeException {
        String splitInput[] = input.split(" ");
        String indicator = splitInput[0];

        switch(indicator) {
            case("list"):
                Itay.printList();
                break;
            case("mark"):
                Itay.handleMark(splitInput);
                break;
            case("unmark"):
                Itay.handleUnmark(splitInput);
                break;
            case("delete"):
                Itay.handleDelete(splitInput);
                break;
            case("todo"):
                Itay.handleTodo(input, splitInput);
                break;
            case("deadline"):
                Itay.handleDeadline(input);
                break;
            case("event"):
                Itay.handleEvent(input);
                break;
            default:
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public boolean isExit(String input) {
        return input.equals("bye");
    }
}
