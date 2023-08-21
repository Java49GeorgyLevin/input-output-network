package telran.view.test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class NumbersOperationsMenu {
	static String name;
public static Item getOperationsItem(String name) {
	NumbersOperationsMenu.name = name;
	return Item.of(name, NumbersOperationsMenu::performMethod);
	
	
	
}
static void twoNumbersAction(InputOutput io,
		BinaryOperator<Double> operator) {
	double firstNumber = io.readDouble("Enter first number", "no number");
	double secondNumber = io.readDouble("Enter second number","no number");
	io.writeLine(operator.apply(firstNumber, secondNumber));
}

static void performMethod(InputOutput io1) {
	Item [] items = {
			Item.of("Add two numbers",
					io -> twoNumbersAction(io, (a,b) -> a + b)),
			Item.of("Subtract two numbers", io -> twoNumbersAction(io, (a,b) -> a - b)),
			Item.of("Divide two numbers", io -> twoNumbersAction(io, (a,b) -> a / b)),
			Item.of("Multiply two numbers", io -> twoNumbersAction(io, (a,b) -> a * b)),
			Item.of("Compute percent",io -> {
				double whole = io.readDouble("Enter whole value", "Wrong whole value");
				double part = io.readDouble("Enter part value", "Wrong part value");
				io.writeLine(part / whole * 100);
			}), 
			Item.ofExit()
		};
			Menu menu = new Menu(name, items);
			menu.perform(io1);
}
}
