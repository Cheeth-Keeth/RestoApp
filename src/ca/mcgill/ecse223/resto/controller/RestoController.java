package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;


import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.*;

public class RestoController {
	
	// Feature 1 goes here: Add a table and its seats to the restaurant	
	public static void createTable (int number, int x, int y, int width, int length, int numberOfSeats) throws InvalidInputException 
	{
		String error = ""; 
		if (x<0)
		{
			error = "Invalid size for a table x must not be negative"; 
		}
		if (y<0)
		{
			error = "Invalid size for a table y must not be negative " + error; 
		}
		if (number<= 0)
		{
			error = "Invalid number must be positive" + error;
		}
		if (width<=0)
		{
			error = "Invalid width must be positive" + error;
		}
		if (length<=0)
		{
			error = "Invalid length must be positive" +error;
		}
		if (error.length() > 0)
		{
			throw new InvalidInputException(error.trim());
		}
		
		
		   RestoApp r = RestoApplication.getRestoApp();
		   
		   List <Table> currentTables = r.getCurrentTables();
		   
		   for(Table currentTable : currentTables)
			{
				if(currentTable.doesOverlap(x, y, width, length))
				{
					error = "there is an overlap";
					if(error.length()>0)
					{
						throw new InvalidInputException(error.trim());
					}
				}
				else
				{
					continue;
				}
			}  
				   
		   try
		   {
			   Table table = new Table(number,x, y, width, length, r);
			   r.addCurrentTable(table);
			   
			   for (int i=1;i<= numberOfSeats ;i++ )
			   {
				   Seat seat= table.addSeat();
				   table.addCurrentSeat(seat);
			   }
			   RestoApplication.save();
		   }
	
		   catch(RuntimeException e)
		   {
			   throw new InvalidInputException(e.getMessage());
		   }
			
			
	}

	//Zoltak orderMenuItem
	
	public static void orderMenuItem(MenuItem menuItem, int quantity, List<Seat> seats) throws InvalidInputException{
		String error = "";
		if(menuItem == null)
		{
			error = "no MenuItem selected";
		}
		if(seats == null)
		{
			error = "no seats selected";
		}
		if(quantity <= 0)
		{
			error = "improper qunatity inputted";
		}
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if(current==false)
		{
			error = "not currently a menu item";
		}
		if(error.length()>0)
		{
			throw new InvalidInputException(error.trim());
		}
		RestoApp r = RestoApplication.getRestoApp();
		
		List<Table> currentTables = r.getCurrentTables();
		Order lastOrder = null;
		
		for(Seat seat: seats)
		{
			Table table = seat.getTable();
			current = currentTables.contains(table);
			if(current==false)
			{
				error = "table does not exist in current tables";
					throw new InvalidInputException(error.trim());
			}
			List<Seat> currentSeats= table.getCurrentSeats();
			current = currentSeats.contains(seat);
			if(current==false)
			{
				error = "seat does not exist in current seats";
					throw new InvalidInputException(error.trim());
			}
			if(lastOrder==null)
			{
				if(table.numberOfOrders()>0)
				{
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				else
				{
					error = "table has no orders";
					throw new InvalidInputException(error.trim());
				}
			}
			else
			{
				Order comparedOrder = null;
				if(table.numberOfOrders()>0)
				{
					comparedOrder = table.getOrder(table.numberOfOrders()-1);
				}
				else
				{
					error = "table has no orders";
					throw new InvalidInputException(error.trim());
				}
				if(!comparedOrder.equals(lastOrder))
				{
					error = "tables are not sharing the same order";
					throw new InvalidInputException(error.trim());
				}
			}
		}
		if(lastOrder==null)
		{
			error = "no current order";
			throw new InvalidInputException(error.trim());
		}
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		
		boolean itemCreated = false;
		OrderItem newItem = null;
		
		for(Seat seat: seats)
		{
			Table table = seat.getTable();
			if(itemCreated)
			{
				table.addToOrderItem(newItem, seat);
			}
			else
			{
				OrderItem lastItem = null;
				
				if(lastOrder.numberOfOrderItems()>0)
				{
					lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
				}
				table.orderItem(quantity, lastOrder, seat, pmi);
				if(lastOrder.numberOfOrderItems()>0 && !lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1).equals(lastItem))
				{
					itemCreated = true;
					newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
				}
			}
		}
		if(itemCreated==false)
		{
			
			error = "unable to create item" + lastOrder.numberOfOrderItems() + menuItem.getName();
			throw new InvalidInputException(error.trim());
		}
		try {
            RestoApplication.save();
        }
        catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
	}

	
	// Feature 2 goes here: Remove a table from the restaurant
	public static void removeTable (Table table) throws InvalidInputException {
        
    		String error = "";
    		
		if (table == null) {

		   error = "Must enter an existing table. ";

		}

		boolean reserved = table.hasReservations();

		if (reserved == true) {

		   error = error + "Cannot remove a reserved table.";

		}

		if (error.length() > 0) {

		   throw new InvalidInputException(error.trim());
		
		}

		RestoApp r = RestoApplication.getRestoApp();

		try {

		   List <Order> currentOrders = r.getCurrentOrders();

		   for (Order order : currentOrders) {

			List <Table> tables = order.getTables();

			boolean inUse = tables.contains(table);

			if (inUse == true) {

			   throw new InvalidInputException("Cannot remove a table that is currently in use.");

			}

		   }

		   r.removeCurrentTable(table);

		   RestoApplication.save();

		}

		catch (RuntimeException e) {

		   throw new InvalidInputException(e.getMessage());

		}
		
	}



	// Feature 3 goes here: Update the table number and number of seats of a table
	public static void updateTable( Table table, int newNumber, int numberOfseats) throws InvalidInputException{
		String error = ""; 
		if( table == null) {
			error = "A table must be specified for the update. "; 
		}
		if(newNumber<=0 || numberOfseats<=0) {
			error = error + "A table number or number of seat must be positive."; 
		}
		if(error.length() > 0){
			throw new InvalidInputException(error.trim());
		}
		//Check if the table has reservation.
		Boolean reserved = table.hasReservations(); 
		if( reserved == true) {
			throw new InvalidInputException("The table cannot be updated when it is reserved."); 
		}
		RestoApp r = RestoApplication.getRestoApp(); 
	    	try {
			//Get current orders from the application.
			List<Order> currentOrders = r.getCurrentOrders(); 
			//Get the list of table from current order and check if the table is in use
			for(int i = 0; i<currentOrders.size(); i++ ) {
				Order order = currentOrders.get(i); 				
				List <Table> tables = order.getTables(); 
				Boolean isUse = tables.contains(table); 
				if(isUse == true) { 
					throw new InvalidInputException("The table cannot be updated when it is in use.");
				}
			}
			if(!(table.setNumber(newNumber))) { 
				throw new RuntimeException("The number is already used!");  // ?????
			}
			int n = table.numberOfCurrentSeats();
			//If the new number of seat is larger than the current number of seat, add seats.
			for(int i = 0; i < (numberOfseats - n); i++) {
				Seat seat = table.addSeat(); 
				table.addCurrentSeat(seat); 
			}
			//If the new number of seat is smaller than the current number of seat, remove seats.
			for(int i = 0; i < (n - numberOfseats); i++ ) {
				Seat seat = table.getCurrentSeat(0); 
				table.removeCurrentSeat(seat); 
			}
			RestoApplication.save(); 
		}catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	// Feature 4 goes here: Change the Location of a table
	
	public static void moveTable(Table table, int x, int y) throws InvalidInputException{
		String error = "";
		int width = table.getWidth();
		int length = table.getLength();
		if(table==null)
		{
			error = "There is no table inputted";
		}
		if(x<0)
		{
			error = "Invalid size for a table";
		}
		if(y<0)
		{
			error = "Invalid size for a table";
		}
		if(error.length()>0)
		{
			throw new InvalidInputException(error.trim());
		}
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		for(Table currentTable : currentTables)
		{
			if(currentTable.doesOverlap(x, y, width, length)&&currentTable!=table)
			{
				error = "there is an overlap";
				if(error.length()>0)
				{
					throw new InvalidInputException(error.trim());
				}
			}
			else
			{
				continue;
			}
		}
		table.setX(x);
		table.setY(y);
		 try {
	            RestoApplication.save();
	        }
	        catch (RuntimeException e) {
	            throw new InvalidInputException(e.getMessage());
	        }
	}
	
	
	
	
	// Feature 5 goes here: Display the menu according to food/beverage categories
	//(includes loading the menu from a file and saving all data required for all features to a file)
	
	public static List<ItemCategory> getItemCategories()
	{		
		List<ItemCategory> ourItemCategories = new ArrayList<ItemCategory>(Arrays.asList(ItemCategory.values()));
		
		return ourItemCategories;
	}
	
	//gets a list of menu items FOR a specified category
	public static List<MenuItem> getMenuItems(ItemCategory itemCategory) throws InvalidInputException
	{
		String error = "";
		if (itemCategory == null) { // if the item category is null, throw exception
			error = "Item Category Cannot Be Null";
		}
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		
		List<MenuItem> menuItemList = new ArrayList<>(); //creates the new list of menu items that will be populated by all item of a certain category
		RestoApp r = RestoApplication.getRestoApp();
		//if(r.equals(null)) System.out.println("the restoapp is null"); //for debugging
		
		Menu menu = r.getMenu();
		//if(menu.equals(null)) System.out.println("the menu is null"); //for debugging
		
		List<MenuItem> menuItems = menu.getMenuItems(); //gets the list of all menu items
		
		//if(menuItems.isEmpty()) System.out.println("the menuitemslist is null"); //for debugging
		int numMenuItems = menuItems.size();

	
		
		for(int i=0; i<numMenuItems; i++) {
			MenuItem menuItem = menuItems.get(i);
			
			
			Boolean current = menuItems.get(i).hasCurrentPricedMenuItem(); // creates boolean true if the menu item at the index 
																		   // is a current menu item, ie has a price
			
			//System.out.println(current);
			
			ItemCategory category = menuItem.getItemCategory();
			
			if(current && category.equals(itemCategory)) {
				menuItemList.add(menuItem); // if it is the right menu item and has is a current menu item, adds to menuItemList
			}
		}
		
		return menuItemList; // return the list containing all the menu items of a  specific category
	}
	
	public static List<Table> getTables() {
		return RestoApplication.getRestoApp().getTables();
	}
	
	public static List<Table> getCurrentTables() {
		return RestoApplication.getRestoApp().getCurrentTables();
	}
	
	public static List<Order> getCurrentOrders() {
		return RestoApplication.getRestoApp().getCurrentOrders();
	}
	
	
	public static List<MenuItem> getMenuItems()
	{
		return RestoApplication.getRestoApp().getMenu().getMenuItems();
	}
	
	
	//Feature 6 goes here: Reserve table
	public static List<Reservation> getCurrentReservations() {
		return RestoApplication.getRestoApp().getReservations();
	}
	
	//Zoltak 1
	public static void startOrder(List<Table> tables) throws InvalidInputException
	{
		String error = "";
		if (tables == null) { 
			error = "No tables";
		}
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		for(Table table: tables)
		{
			boolean current = currentTables.contains(table);
			if(current == false) 
			{
				throw new InvalidInputException("Table does not exist in current tables");
			}
		}
		boolean orderCreated = false;
		Order newOrder = null;
		for(Table table: tables)
		{
			if(orderCreated)
			{
				table.addToOrder(newOrder);
			}
			else
			{
				Order lastOrder = null;
				if(table.numberOfOrders()>0)
				{
					lastOrder= table.getOrder(table.numberOfOrders()-1);
				}
				table.startOrder();
				if(table.numberOfOrders()>0&&!table.getOrder(table.numberOfOrders()-1).equals(lastOrder))
				{
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders()-1);
				}
			}
		}
		if(orderCreated==false)
		{
			throw new InvalidInputException("No orderCreated");
		}
		r.addCurrentOrder(newOrder);
		try {
            RestoApplication.save();
        }
        catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
	}
	
	//Zoltak 2
	/*public static void endOrder(Order order) throws InvalidInputException{
		String error = "";
		if (order == null) {
			error = "Order Error";
		}
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		RestoApp r = RestoApplication.getRestoApp();
		List<Order> currentOrders = r.getCurrentOrders();
		boolean current = currentOrders.contains(order);
		if(current == false)
		{
			throw new InvalidInputException(error);
		}
		List <Table> tables = order.getTables();
		List<Table> tempTables = tables;
		for(Table table: tempTables)
		{
			if(table.numberOfOrders()>0&&table.getOrder(table.numberOfOrders()-1).equals(order))
			{
				table.endOrder(order); 
			}
			
			
		}
		if(allTablesAvailableOrDifferentCurrentOrder(tables,order))
		{
			r.removeCurrentOrder(order);
		}
		try {
            RestoApplication.save();
        }
        catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
	}

	}*/
	
	public static void endOrder (Order order) throws InvalidInputException {

		if (order == null) {

		throw new InvalidInputException("Error. Order is null.");
		}

		RestoApp r = RestoApplication.getRestoApp();

		List<Order> currentOrders = r.getCurrentOrders();

		boolean current = currentOrders.contains(order);

		if(!current)
		{
			throw new InvalidInputException("Order is not in current orders");
		}

		List<Table> tables = order.getTables();
		
		List<Table> tablesForOrder = new ArrayList<>();
 		for(Table table: tables)
		{
			tablesForOrder.add(table);
		}
		
		
		for (Table table : tablesForOrder)
		{ 
			if(table.numberOfOrders()>0 && table.getOrder(table.numberOfOrders()-1).equals(order))
			{
				table.endOrder(order);
			}
		}

		if(allTablesAvailableOrDifferentCurrentOrder(tables,order))
		{
			r.removeCurrentOrder(order);
		}
		
		TakeOutCustomer cust=null;
		if(order.hasTakeOutCustomer())
		{
			cust = order.getTakeOutCustomer();
			try
			{
				deleteTakeOutCustomer(cust);
			}
			catch (Exception e)
			{
				throw new InvalidInputException(e.getMessage()); 
			}
		}

		try {
		RestoApplication.save();
		System.out.println("save worked end order");
		}
		catch (Exception e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	
	//Zoltak 3
	public static boolean allTablesAvailableOrDifferentCurrentOrder(List <Table> tables,Order order) {
		for(Table table: tables)
		{
			if(!table.getStatus().equals(Status.Available))
			{
				int x = table.numberOfOrders()-1;
				Order lastOrder = table.getOrder(x);
				if (order.equals(lastOrder))
					{
						return false; 
					}
			}
		}
		return true;
	}

	public static void reserveTable(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress,
			           String contactPhoneNumber, List<Table> tables ) throws InvalidInputException {
		
		System.out.println("at start of controller reserve");
		
		String error = ""; 
		if( date == null ) {
			error = error + "A date need to be specified for reservation! "; 
			System.out.println("controller date null");
		}
		if(time == null) {
			error = error + "A time need to be specified for reservation! "; 
			System.out.println("controller time null");
		}
		
		java.util.Date currentDate = new java.util.Date(); 
		date = Reservation.cleanDate(date); 
		Date cTime = Reservation.cleanDate(new Date(time.getTime()));
		java.util.Date tempDate = new java.util.Date(date.getTime() + time.getTime()-cTime.getTime());
		
		if(tempDate.before(currentDate)){
			error = error + "The date and time is in the past"; 
		}
		if(numberInParty <= 0 ) {
			error = error + "Number of people need to be sepcified for reservation!"; 
		}
		if(contactName == null || contactName.equals("")) {
			error = error + "Please specifiy the contact person's name!"; 
		}
		if(contactEmailAddress == null || contactEmailAddress.equals("")) {
			error = error + "Please specifiy the contact person's Email Address!"; 
		}
		if(contactPhoneNumber == null || contactPhoneNumber.equals("")) {
			error = error + "Please specifiy the contact person's phone Number!"; 
		}
		if(error.length() > 0){
			throw new InvalidInputException(error.trim());
		}
		
		System.out.println("getting resto app");
		RestoApp r = RestoApplication.getRestoApp();
		
		System.out.println("getting tables");
		List<Table> currentTables = r.getCurrentTables();
		int seatCapacity = 0; 
		
		
		System.out.println("for tables");
		for(Table table : tables) {
			
			boolean current = currentTables.contains(table); 
			if(current == false){
				throw new InvalidInputException("The table is not there"); 
			}
			seatCapacity = seatCapacity + table.numberOfCurrentSeats(); 
			List<Reservation> reservations = table.getReservations(); 
			
			
			System.out.println("before for reservations");
			for(Reservation reservation : reservations) {
				boolean overlaps = reservation.doesOverlap(date, time); 
				if(overlaps == true) {
					throw new InvalidInputException("There is a reservation at this time!"); 
				}
				System.out.println("in res for loop");
			}
			System.out.println("past for reservations");
		}
		if(seatCapacity < numberInParty) {
			System.out.println("seat capacity is bad");
			throw new InvalidInputException("There is not enough seats!");
		}
		else {
				
			System.out.println("making table array");
			Table[] tableArray = new Table[tables.size()]; 
			for( int i = 0; i < tables.size(); i++) {
				tableArray[i] = tables.get(i);
			}
				
				
			System.out.println("About to create new object for res");
			Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, tableArray);
			r.addReservation(res);////////////////not sure if need
				//Sort?-Skip for now.
		}
		RestoApplication.save();
	}
	//View Order
    public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException {
	
		if(table == null ) { 
			throw new InvalidInputException("There is no table!"); 
		}
		
		RestoApp r = RestoApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
	    boolean containTables = currentTables.contains(table);
	    
	    try {
		    if( containTables == false ) { 
				throw new InvalidInputException("Table is not a current table!"); 	
			}
		    
		    Status status = table.getStatus(); 
		    if( status == Status.Available) {
		    		throw new InvalidInputException("Table is not in use!"); 
		    }
		    Order lastOrder = getLastOrder(table); 
		    List<Seat> currentSeats = table.getCurrentSeats(); 
		    List<OrderItem> result = new ArrayList<OrderItem>(); 
		    
		    for(Seat seat : currentSeats) {
				List<OrderItem> orderItems = seat.getOrderItems(); 
				for(OrderItem orderItem : orderItems) {
					Order order = orderItem.getOrder(); 
					if(lastOrder.equals(order) && !result.contains(orderItem)) {
						result.add(orderItem); 
					}
				}
			}
	        return result; 
	    }catch(RuntimeException e){
	    		throw new InvalidInputException(e.getMessage());
	    }
	}
	
	private static Order getLastOrder(Table table) throws InvalidInputException {
		Order lastOrder = null; 
		if(table.numberOfOrders() > 0) {
			lastOrder = table.getOrder(table.numberOfOrders() - 1); 
			return lastOrder; 
		}
		else {
			throw new InvalidInputException("There is no order!"); 
		}
	}
	public static void cancelOrderItem (OrderItem orderItem) throws InvalidInputException
	{
		String error = ""; 
		if (orderItem == null)
		{
			error = "cancel order item error";
		}
		
		if (error.trim().length() > 0) 
		{
			throw new InvalidInputException(error);
		}
		
		List <Seat> seats =orderItem.getSeats();
		Order order =orderItem.getOrder();
		
		List <Table> tables = new ArrayList<>();
		
		for (Seat seat: seats)
		{
			Table table = seat.getTable();
			
			Order lastOrder = null;
			if (table.numberOfOrders()>0)
			{
				lastOrder = table.getOrder(table.numberOfOrders()-1);
			}
			else 
			{
				error = "table has no orders"; 
				throw new InvalidInputException(error.trim());
			}
			if (lastOrder.equals(order) && !tables.contains(table))
			{
				tables.add(table);
			}
			
		}
		
		for (Table table : tables)
		{
			table.cancelOrderItem(orderItem);
		}
		
		try 
		{
            RestoApplication.save();
        }
        catch (RuntimeException e)
		{
            throw new InvalidInputException(e.getMessage());
        }
		
		}

	public static void cancelOrder (Table table) throws InvalidInputException
	{
		String error = ""; 
		if (table == null)
		{
			error = "cancel order error";
		}
		
		if (error.trim().length() > 0) 
		{
			throw new InvalidInputException(error);
		}
		
		RestoApp r = RestoApplication.getRestoApp();
		List <Table> currentTables = r.getCurrentTables();
		boolean current = currentTables.contains(table);
	
		if(current == false)
		{
			throw new InvalidInputException(error);
		}
		
		table.cancelOrder(); 
		
		try {
            RestoApplication.save();
        }
        catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
		
		
	}
	
	public static void cancelOrder(Order order) throws InvalidInputException { 
		
		String error = "";
		
		if (order == null)
		{
			error = "no order selected";	
		}
		
		if (error.length() > 0 )
		{ 
			throw new InvalidInputException(error.trim());
		}
		List <Table> tables = order.getTables(); 
		Order lastOrder = null; 
		for (Table table : tables)
		{
			
			if(lastOrder==null)
			{
				
				if(table.numberOfOrders()>0)
				{
				
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
			
				else
				{
					error = "table has no orders";
					throw new InvalidInputException(error.trim());
				}
			
			}
			
			else
			{
				Order comparedOrder = null;
				
				if(table.numberOfOrders()>0)
				{
					comparedOrder = table.getOrder(table.numberOfOrders()-1);
				}
				
				else
				{
					error = "table has no orders";
					throw new InvalidInputException(error.trim());
				}
				
				if(!comparedOrder.equals(lastOrder))
				{
					error = "tables are not sharing the same order";
					throw new InvalidInputException(error.trim());
				}
			}
		}
		
		for (Table table : tables )
		{
			table.cancelOrder();
		}
		
		try 
		{
            RestoApplication.save();
        }
        catch (RuntimeException e) 
		{
            throw new InvalidInputException(e.getMessage());
        }
			
	}

	public static void addMenuItem(String name, ItemCategory category, double price) throws InvalidInputException {
		if(name == null) {
			throw new InvalidInputException("Must enter a name for a new menu item");
		}
		
		if(category == null) {
			throw new InvalidInputException("Must enter a category for a new menu item");
		}
		
		if(price < 0) {
			throw new InvalidInputException("Price cannot be negative");	
		}
		
		RestoApp r = RestoApplication.getRestoApp();
		Menu menu = r.getMenu();
		//Runtime Exception????
		try {
			MenuItem menuItem = new MenuItem(name, menu);
			menuItem.setItemCategory(category);
			PricedMenuItem pmi = new PricedMenuItem(price,r,menuItem);
			menuItem.addPricedMenuItem(pmi);
			menuItem.setCurrentPricedMenuItem(pmi);
		} 
		catch(Exception e) {
			throw new InvalidInputException("A menu item with this name already exists");
		}

		RestoApplication.save();
	}
		
	public static void removeMenuItem(MenuItem menuItem) throws InvalidInputException {
		if(menuItem == null) {
			throw new InvalidInputException("Must select a menu item to remove");	
		}
		
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if(current == false) {
			throw new InvalidInputException("No priced menu item exists for this menu item");
		}
		
		menuItem.setCurrentPricedMenuItem(null);
		
		RestoApplication.save();
	}

	
	//TakeOut Order Stuff
	
	//Creates a takeout cust and assigns them an order
	public static void createTakeOutCustomer(String name, String phoneNumber, String orderNumber) throws InvalidInputException
	{
		String error = "";
		if (name == null || phoneNumber ==null || orderNumber==null) { //input validation
			error = "Fields must not be null.";
		}
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		RestoApp r = RestoApplication.getRestoApp();
		
		int orderNum=-1;
		try {
			orderNum = Integer.parseUnsignedInt(orderNumber);
		}
		catch(Exception e)
		{
			throw new InvalidInputException("");
		}
		
		if(orderNum<0)
		{
			throw new InvalidInputException("Cannot have negative order number.");
		}
		
		//Get order object from order number
		
		List <Order> currentOrders = r.getCurrentOrders();
		if(currentOrders.isEmpty())
		{
			throw new InvalidInputException("There are no current orders.");
		}
		
		Order correctOrder = null;
		for (Order order : currentOrders)
		{
			if (order.getNumber() == orderNum)
			{
				correctOrder=order;
				break;
			}
		}
		if(correctOrder==null)
		{
			error = "Order with that number doesn't exist";
			throw new InvalidInputException(error);
		}
		
		String custName = name;
		String custPhone = phoneNumber;
		
		try
		{
			TakeOutCustomer aNewCustomer = new TakeOutCustomer(custName, custPhone, correctOrder, r);
		}
		catch(Exception e) {
			System.out.println("error when creating new customer");
			throw new InvalidInputException(e.getMessage());
		}
		
		
		
		
		
		try
		{
			RestoApplication.save();
	    }
	    catch (Exception e)
		{
	    	throw new InvalidInputException(e.getMessage());
    	}
	}
	
	public static void editTakeOutCustomer(String currentPhone, String newName, String newPhone) throws InvalidInputException
	{
		String error = "";
		if (currentPhone==null || newName==null || newPhone ==null) { //input validation
			error = "Fields must not be null.";
		}
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		RestoApp r = RestoApplication.getRestoApp();
		
		//TODO
		
		try
		{
			RestoApplication.save();
		}
		catch (Exception e)
		{
			throw new InvalidInputException(e.getMessage());
		}	
	}
	
	/*
	public static void setTakeOutOrder(String orderNumber, String customerPhone) throws InvalidInputException
	{
		String error = "";
		if (orderNumber == null || customerPhone ==null) //input validation
		{ 
			error += "Fields must not be null.";
		}
		int orderNum =0;
		try
		{
			orderNum = Integer.parseUnsignedInt(orderNumber); //i think this will give an error if its negative
		}
		catch (Exception e)
		{
			error += "Enter a valid order number.";
		}
		
		
		if (error.trim().length() > 0) {
			throw new InvalidInputException(error);
		}
		
		RestoApp r = RestoApplication.getRestoApp();
		
		TakeOutCustomer cust = TakeOutCustomer.getWithPhoneNumber(customerPhone);
		
		Order correctOrder = null;
		
		List <Order> currentOrders = r.getCurrentOrders();
		for (Order order : currentOrders)
		{
			if (order.getNumber() == orderNum)
			{
				correctOrder=order;
				break;
			}
		}
		
		if(correctOrder==null)
		{
			error = "Order with that number doesn't exist";
			throw new InvalidInputException(error);
		}
		
		correctOrder.setTakeOutCustomer(cust);
		//cust.setOrder(correctOrder);
		
		
		
		//Need this??????
		Table takeOutOrderTable = r.getTakeoutOrderTable();
		List<Table> tablesList = null;
		tablesList.add(takeOutOrderTable);
		
		
		// Need to set the table to the takeout table???????
		//correctOrder.setTables(tablesList); //FIX
		//table.addToOrder(newOrder); //good
		
		//Order anOrder = Order.get
		//TODO: set order as a takeout order?
		
		//TODO: create a way to set the tabelout table
		
		
		try
		{
			RestoApplication.save();
		}
		catch (Exception e)
		{
			throw new InvalidInputException(e.getMessage());
		}
	}
	*/
	
	public static void setTakeOutOrderTable(String tableNum) throws InvalidInputException
	{
		String error = "";
		if (tableNum==null)
		{
			error += "Null field entered";
		}
		
		int num=0;
		try
		{
			num = Integer.parseUnsignedInt(tableNum);
		}
		catch (Exception e)
		{
			error = "Enter a valid number.";
			throw new InvalidInputException(error);
		}
		
		RestoApp r = RestoApplication.getRestoApp();
		
		List<Table> currentTablesList= r.getCurrentTables();
		Table correctTable =null;
		for(Table table: currentTablesList)
		{
			if(table.getNumber()==num)
			{
				correctTable = table;
				break;
			}
		}
		if(correctTable==null)
		{
			error += "Table with that number not found";
			throw new InvalidInputException(error);
		}
	
		
		r.setTakeoutOrderTable(correctTable);
		
		try
		{
			RestoApplication.save();
		}
		catch (Exception e)
		{
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static List<TakeOutCustomer> getAllTakeOutCustomers() throws InvalidInputException
	{
		RestoApp r = RestoApplication.getRestoApp();
		return r.getTakeOutCustomers();
	}
	
	public static List<Order> getTakeOutOrders() throws InvalidInputException
	{
		RestoApp r = RestoApplication.getRestoApp();
		Table t = r.getTakeoutOrderTable();
		return t.getOrders();
	}
	
	public static void deleteTakeOutCustomer(TakeOutCustomer cust) throws InvalidInputException
	{
		cust.delete();
		try
		{
			RestoApplication.save();
		}
		catch (Exception e)
		{
			throw new InvalidInputException(e.getMessage());
		}
	}
	

	public static void updateMenuItem(MenuItem menuItem, String name, ItemCategory category, double price) throws InvalidInputException {
		if(menuItem == null) {
			throw new InvalidInputException("Menu item is null");
		}
		
		if(name == null) {
			throw new InvalidInputException("Must enter a name for a new menu item");
		}
		
		if(category == null) {
			throw new InvalidInputException("Must enter a category for a new menu item");
		}
		
		if(price < 0) {
			throw new InvalidInputException("Price cannot be negative");	
		}
		
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if(current == false) {
			throw new InvalidInputException("This menu item is not current");
		}
		
		boolean duplicate = menuItem.setName(name);
		if(duplicate == false) {
			throw new InvalidInputException("Another menu item has this name");
		}
		
		menuItem.setItemCategory(category);
		
		if(price != menuItem.getCurrentPricedMenuItem().getPrice()) {
			RestoApp r = RestoApplication.getRestoApp();
			PricedMenuItem pmi = new PricedMenuItem(price,r,menuItem);
			menuItem.addPricedMenuItem(pmi);
			menuItem.setCurrentPricedMenuItem(pmi);
		}

		RestoApplication.save();
	}

	//Feature 9: View Order

	//Feature: IssueBill
	public static void issueBill(List<Seat> seats) throws InvalidInputException {
			
			String error = "";
			
			if (seats == null) {
				
				error += "Cannot issue a bill for a null list of seats";
				
			}
			
			if (seats.size() == 0) {
				
				error += "Cannot issue a bill for an empty list of seats";
				
			}
			
			if (error.length() > 0) {

				throw new InvalidInputException(error.trim());
				
			}
			
			RestoApp r = RestoApplication.getRestoApp();
			
			try {
			
				List<Table> currentTables = r.getCurrentTables();
				
				Order lastOrder = null;
				
				for (Seat seat : seats) {
					
					Table table = seat.getTable();
					
					Boolean currentTable = currentTables.contains(table);
					
					if (!currentTable) {
						
						throw new InvalidInputException("Can only issue a bill for seats at current tables");
						
					}
					
					List<Seat> currentSeats = table.getCurrentSeats();
					
					Boolean currentSeat = currentSeats.contains(seat);
					
					if (!currentSeat) {
						
						throw new InvalidInputException("Can only issue a bill for seats at current tables");
						
					}
					
					if (lastOrder == null) {
						
						if (table.numberOfOrders() > 0) {
							
							lastOrder = table.getOrder(table.numberOfOrders() - 1);
							
						} else {
							
							throw new InvalidInputException("Cannot issue a bill if an order doesnt exist");
							
						}
						
					} else {
						
						Order comparedOrder = null;
						
						if (table.numberOfOrders() > 0) {
							
							comparedOrder = table.getOrder(table.numberOfOrders() - 1);
							
						} else {
							
							throw new InvalidInputException("Cannot issue a bill if an order doesnt exist");
							
						}
						
						if (!comparedOrder.equals(lastOrder)) {
							
							throw new InvalidInputException("All seats must be under the same order to be issued under the same bill");
							
						}
						
					}
				
				}
				
				if (lastOrder == null) {
					
					throw new InvalidInputException("Cannot issue a bill if an order doesnt exist");
					
				}
				
				Boolean billCreated = false;
				Bill newBill = null;
				
				for (Seat seat: seats) {
					
					Table table = seat.getTable();
					
					if (billCreated) {
						
						table.addToBill (newBill, seat);
						
					} else {
						
						Bill lastBill = null;
						
						if (lastOrder.numberOfBills() > 0) {
							
							lastBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
							
						}
						
						table.billForSeat(lastOrder, seat);
						
						if (lastOrder.numberOfBills() > 0 && !lastOrder.getBill(lastOrder.numberOfBills() - 1).equals(lastBill)) {
							
							billCreated = true;
							newBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
							
						}
						
					}
					
				}
				
				if (!billCreated) {
					
					throw new InvalidInputException("Bill cannot be created");
					
				}
				
				RestoApplication.save();
				
			} catch (RuntimeException e){
				
				throw new InvalidInputException(e.getMessage());
				
			}
			
		}

}

