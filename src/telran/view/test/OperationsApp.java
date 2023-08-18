package telran.view.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import telran.view.*;

public class OperationsApp {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Main menu", getItems());
		menu.perform(io);

	}

	static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		double first = io.readDouble("Enter first number", "Must be any number");
		double second = io.readDouble("Enter second number", "Must be any number");
		io.writeLine(operator.apply(first, second));
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
	
	static Item[] getItems() {
		String numOp = "Number Operations";
		String dateOp = "Date Operations";
		Item[] items = { Item.of(numOp, subMenu(numOp, getNumberItems())::perform),
		Item.of(dateOp, subMenu(dateOp, getDateItems())::perform),
		Item.ofExit()
		};
		
		return items;
	}
	
	static Menu subMenu(String title, Item[] items) {
		
		return new Menu(title, items);
	}


	static Item[] getNumberItems() {
		Item[] items = { Item.of("Add numbers", io -> calculate(io, (a,b)->a + b)),
				Item.of("Subtract numbers", io -> calculate(io, (a,b)->a - b)),
				Item.of("Multiply numbers", io -> calculate(io, (a,b)->a * b)),
				Item.of("Divide numbers", io -> calculate(io, (a,b)->a / b)),
				Item.ofExit()};
		return items;
	}
	
	static Item[] getDateItems() {
		Item[] items = { Item.of("Date after a given number of days", io -> calculateDateLong(io, (a,b) -> a.plusDays(b))),
				Item.of("Date before a given number of days", io -> calculateDateLong(io, (a,b) -> a.minusDays(b))),
				Item.of("Date between two dates", io -> calculateDateDate(io, (a,b) ->  ChronoUnit.DAYS.between(b, a))),
				Item.ofExit()				
		};
		return items;
	}
		

}
