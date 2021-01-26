import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDate time;

    Event(String name, String time) throws DateTimeParseException {
        super(name);
        this.time = LocalDate.parse(time);
    }

    LocalDate getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        LocalDate date = this.getTime();
        return String.format("[E]%s (at: %s %s %s)", super.toString(), date.getDayOfMonth(), date.getMonth(), date.getYear());
    }
}