package jeff;

import java.time.format.DateTimeParseException;

public class CommandEvent extends Command{

    CommandEvent (String main, String supp) {
        super(main, supp);
    }
    @Override
    public String execute(TaskList tasks, Storage storage) throws JeffException {
        try {
            String[] dateTime = supplementaryInfo.split("by ")[1].split(" ", 2);
            Task event = new Event(mainInfo, dateTime[0], dateTime[1]);
            tasks.addTask(event);
            return "Got it. I've added this task:\n" + event + tasks.queryNumTasks();
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            throw new JeffException("please provide date & time as YYYY-MM-DD HH:MM");
        }
    }
}