import java.time.format.DateTimeParseException;

public class Parser {

    static boolean execute(String message, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        boolean isExit = false;
        String[] messageSplit = message.split(",");
        String[] messageMain = messageSplit[0].split(" ", 2);
        Command cmd;
        try {
            cmd = Command.valueOf(messageMain[0]);
        } catch(IllegalArgumentException e) {
            throw new DukeException("I can't understand the message");
        }

        switch(cmd) {

        case list:
            if (tasks.getNumTasks() == 0) {
                ui.print("No tasks right now");
            } else {
                String toPrint = "";
                for (int i = 0; i < tasks.getNumTasks(); i++) {
                    toPrint += (i + 1) + "." + tasks.getTask(i) + "\n";
                }
                ui.print(toPrint);
            }
            break;

        case todo:
            if (messageMain.length < 2) {
                throw new DukeException("please provide a description for todo");
            }
            Task todo = new ToDo(messageMain[1]);
            tasks.addTask(todo);
            ui.print("Got it. I've added this task:\n" + todo + tasks.queryNumTasks());
            break;

        case deadline:
            if (messageMain.length < 2) {
                throw new DukeException("please provide a description for deadline");
            }
            try {
                String[] dateTime = messageSplit[1].split("by ")[1].split(" ", 2);
                Task deadline = new Deadline(messageMain[1], dateTime[0], dateTime[1]);
                tasks.addTask(deadline);
                ui.print("Got it. I've added this task:\n" + deadline + tasks.queryNumTasks());
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new DukeException("please provide a date and time for deadline");
            } catch(DateTimeParseException e) {
                throw new DukeException("please provide datetime as YYYY-MM-DD HH:MM");
            }
            break;

        case event:
            if (messageMain.length < 2) {
                throw new DukeException("please provide a description for event");
            }
            try {
                String[] dateTime = messageSplit[1].split("at ")[1].split(" ", 2);
                Task event= new Event(messageMain[1], dateTime[0], dateTime[1]);
                tasks.addTask(event);
                ui.print("Got it. I've added this task:\n" + event + tasks.queryNumTasks());
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException("please provide a date and time for event");
            } catch(DateTimeParseException e) {
                throw new DukeException("please provide datetime as YYYY-MM-DD HH:MM");
            }
            break;

        case done:
            if(messageMain.length != 2) {
                throw new DukeException("wrong number of arguments for done");
            }
            try {
                int taskIndex = Integer.parseInt(messageMain[1]) - 1;
                Task doneTask = tasks.getTask(taskIndex);
                doneTask.setDone();
                ui.print("Nice! I've marked this task as done:\n" + doneTask);
            } catch(IndexOutOfBoundsException e) {
                throw new DukeException("task number does not exist");
            } catch(NumberFormatException e) {
                throw new DukeException("indicate task number as an integer");
            }
            break;

        case delete:
            if(messageMain.length != 2) {
                throw new DukeException("wrong number of arguments for delete");
            }
            try {
                int taskIndex = Integer.parseInt(messageMain[1]) - 1;
                Task toRemove = tasks.getTask(taskIndex);
                tasks.deleteTask(taskIndex);
                ui.print("Noted. I've removed this task:\n" + toRemove + tasks.queryNumTasks());
            } catch(IndexOutOfBoundsException e) {
                throw new DukeException("task number does not exist");
            } catch(NumberFormatException e) {
                throw new DukeException("indicate task number as an integer");
            }
            break;

        case bye:
            ui.print("Bye. Hope to see you again!");
            storage.save(tasks.getTaskList());
            isExit = true;
        }
        return isExit;
    }
}
