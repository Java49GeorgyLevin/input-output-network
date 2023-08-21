package telran.view.test;

import telran.view.*;

public class OperationsApp {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Main menu", getItems());
		menu.perform(io);

	}
	
	static Item[] getItems() {		
		Item[] items = {
			NumbersItem.getNumberItems("Number Operations"),
			DatesItem.getDateItems("Date Operations"),
			Item.ofExit()
		};
		
		return items;
	}

}
