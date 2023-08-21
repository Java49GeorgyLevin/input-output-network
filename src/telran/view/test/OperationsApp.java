package telran.view.test;

import telran.view.*;

public class OperationsApp {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		Menu menu = new Menu("Main menu", getItems());
		menu.perform(io);

	}

	
	static Item[] getItems() {
		String numOp = "Number Operations";
		String dateOp = "Date Operations";
		Item[] items = {
			subItems(numOp, NumbersItem.getNumberItems()),
			subItems(dateOp, DatesItem.getDateItems()),
			Item.ofExit()
		};
		
		return items;
	}
	
	static Item subItems(String title, Item[] items) {
		
		return Item.of(title, io -> new Menu(title, items).perform(io));
	}


}
