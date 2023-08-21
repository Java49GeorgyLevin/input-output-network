package telran.view;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;

public class DatesItem {
	
	public static Item getDateItems(String title) {
		return Item.of(title, ioM -> {
			Item[] items = {
				Item.of("Date after a given number of days", io -> calculateDateLong(io, (a,b) -> a.plusDays(b))),
				Item.of("Date before a given number of days", io -> calculateDateLong(io, (a,b) -> a.minusDays(b))),
				Item.of("Date between two dates", io -> calculateDateDate(io, (a,b) ->  ChronoUnit.DAYS.between(b, a))),
				Item.ofExit()
			};
			Menu menu = new Menu(title, items);
			menu.perform(ioM);
			});	
		}
		
		
		static void calculateDateLong(InputOutput io, BiFunction<LocalDate, Long, LocalDate> operator) {
			LocalDate first = io.readDate("Enter date", "Must be any date");
			long second = io.readLong("Enter number days", "Must be any number");
			io.writeLine(operator.apply(first, second));
		}
		
		static void calculateDateDate(InputOutput io, BiFunction<LocalDate, LocalDate, Long> operator) {
			LocalDate first = io.readDate("Enter date", "Must be any date");
			LocalDate second = io.readDate("Enter date", "Must be any date");
			io.writeLine(operator.apply(first, second));
		}

}
