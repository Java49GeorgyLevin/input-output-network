package telran.view;

import java.util.function.BinaryOperator;

public class NumbersItem {	
	
	public static Item getNumberItems(String title) {
		return	Item.of(title, ioM -> {
			Item [] items =	{	
				Item.of("Add numbers", io -> calculate(io, (a,b) -> a + b)),
				Item.of("Subtract numbers", io -> calculate(io, (a,b)->a - b)),
				Item.of("Multiply numbers", io -> calculate(io, (a,b)->a * b)),
				Item.of("Divide numbers", io -> calculate(io, (a,b)->a / b)),
				Item.ofExit()
				};
			
			Menu menu = new Menu(title, items);
			menu.perform(ioM);
		});
	}
	
	static void calculate(InputOutput io, BinaryOperator<Double> operator) {
		double first = io.readDouble("Enter first number", "Must be any number");
		double second = io.readDouble("Enter second number", "Must be any number");
		io.writeLine(operator.apply(first, second));
	}

}
