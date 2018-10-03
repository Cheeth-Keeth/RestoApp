package ca.mcgill.ecse223.resto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Table.Status;
import ca.mcgill.ecse223.resto.model.*;



public class RestoPage extends JFrame {
	
	private static final long serialVersionUID = -6426310869335015542L;
	
	
	// UI elements
	private JLabel errorMessage;
	private String error = null;
	
	
	//Buttons
		//Table
		private JButton addTableBUTTON;
		private JButton removeTableBUTTON;
		private JButton moveTableBUTTON;
		private JButton updateTableBUTTON;
		private JButton issueBillBUTTON;
		private JButton viewBillsBUTTON;
		
		private JButton startOrderBUTTON;
		private JButton endOrderBUTTON;
		private JButton orderMenuItemBUTTON;
		private JButton viewOrderBUTTON;
		private JButton cancelOrderBUTTON;
		
		private JButton addReservationBUTTON;
		private JButton removeReservationBUTTON;
		

		private JButton setTakeOutTableBUTTON;
		private JButton newCustomerBUTTON;
		private JButton setTakeOutOrderBUTTON; //not needed


		private JButton addMenuItemBUTTON;
		private JButton removeMenuItemBUTTON;
		private JButton updateMenuItemBUTTON;

		
		private DefaultTableModel tableModelMenu;
		private DefaultTableModel tableModelReservations;
		

		private JPanel reservationTab;
		private JScrollPane reservationsTableContainer;
		
		private JPanel menuTab;
		private JScrollPane menuTableContainer;
		
		private JScrollPane tablePanelContainer;


	
	//UpdateTable Object for Update Table UI.
		UpdateTable updateTable = new UpdateTable();
		RemoveTable removeTable = new RemoveTable();
		CreateTable createMyTable = new CreateTable();
		MoveTable moveTable = new MoveTable();
		AddReservation addReservation = new AddReservation(this);
		ViewOrder viewOrder = new ViewOrder(); 

		StartOrder startOrder = new StartOrder();
		EndOrder endOrder = new EndOrder();
		OrderMenuItem orderMenuItem = new OrderMenuItem();
		IssueBill issueBill = new IssueBill();
		ViewBills viewBills = new ViewBills();
		CancelOrder cancelOrder = new CancelOrder();
		

		SetTakeOutTable setTakeOutTable = new SetTakeOutTable();
		NewCustomer newCustomer = new NewCustomer();
		
		

		AddMenuItem addMenuItem = new AddMenuItem(this);
		RemoveMenuItem removeMenuItem = new RemoveMenuItem(this);
		UpdateMenuItem updateMenuItem = new UpdateMenuItem(this);

		

		
	public RestoPage()
	{
		initComponents();
		refreshData();
	}
	
	
	
	
	private void initComponents()
	{
		setTitle( "Resto Application" );
		setLayout(new GridLayout(1,2)); //Splitting the main Frame into two sides left/right
		setBackground( Color.gray);
		setResizable(true); // Do we want this? YES (i think)
		int frameWidth = getContentPane().getWidth();
		int frameHeight = getContentPane().getHeight();
		setSize(1600, 1000); //careful b/c pack() is run later, pack resizes everything to fit in as small space as possible
		
		
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		
		
		//////////////////////////////////////////Left side of the frame///////////////////////////////////////////////////
		JPanel leftSide = new JPanel();
        //leftSide.setBorder(BorderFactory.createLineBorder(getForeground()));
		leftSide.setLayout(new GridLayout(1,1));
        add(leftSide);
       
        tablePanelContainer = displayTableLayout();
        
    	// add scroll pane
		leftSide.add(tablePanelContainer);
        //add(tablePanelContainer);
        
        
        
        
        
        
        
        
        

        //////////////////////////////////////////Right side of the frame/////////////////////////////////////////////////
        JTabbedPane rightSideTabbedPane = new JTabbedPane();
        
	        // A tab
        	reservationTab = new JPanel();
        		reservationTab.setLayout(new GridLayout(3,1));
        		
        		//Add Reservation Button
        		addReservationBUTTON = new JButton();
        		addReservationBUTTON.setText("Add Reservation");
        		reservationTab.add(addReservationBUTTON);
        		addReservationBUTTON.addActionListener(new java.awt.event.ActionListener() {
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				addReservationButtonActionPerformed(evt);
        			}
        		});
	        
        		//Remove Reservation Button
        		removeReservationBUTTON = new JButton();
        		removeReservationBUTTON.setText("Remove Reservation (does nothing yet)");
        		reservationTab.add(removeReservationBUTTON);
        		removeReservationBUTTON.addActionListener(new java.awt.event.ActionListener() {
        			public void actionPerformed(java.awt.event.ActionEvent evt) {
        				removeReservationButtonActionPerformed(evt);
        			}
        		});
        		
        		
        		reservationsTableContainer = displayList();

	        	// add scroll pane
        		reservationTab.add(reservationsTableContainer);
		    
	        	
        		
        		
        		
        	// A tab
	        JPanel tableTab = new JPanel();
	        	tableTab.setLayout(new GridLayout(6,1));
	        		
	        		//Add table Button
	        		addTableBUTTON = new JButton();
	        		addTableBUTTON.setText("Add Table");
	        		tableTab.add(addTableBUTTON);
	        		addTableBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				addTableButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//Remove Table Button
	        		removeTableBUTTON = new JButton();
	        		removeTableBUTTON.setText("Remove Table");
	        		tableTab.add(removeTableBUTTON);
	        		removeTableBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				removeTableButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//Move Table Button
	        		moveTableBUTTON = new JButton();
	        		moveTableBUTTON.setText("Move Table");
	        		tableTab.add(moveTableBUTTON);
	        		moveTableBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				moveTableButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//Update table button
	        		updateTableBUTTON = new JButton();
	        		updateTableBUTTON.setText("Update Table");
	        		tableTab.add(updateTableBUTTON);
	        		updateTableBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				updateTableButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//Order tab
			        /*JPanel orderTab = new JPanel();
		        	orderTab.setLayout(new GridLayout(6,1));
		        	
	        		//Start Order button
	        		startOrderBUTTON = new JButton();
	        		startOrderBUTTON.setText("Start Order");
	        		orderTab.add(startOrderBUTTON);
	        		startOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				startOrderButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//End order button
	        		endOrderBUTTON = new JButton();
	        		endOrderBUTTON.setText("End Order");
	        		orderTab.add(endOrderBUTTON);
	        		endOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				endOrderButtonActionPerformed(evt);
	        			}
	        		});
	        		
	        		//OrderMenuItem button
	    	        orderMenuItemBUTTON = new JButton();
	        			orderMenuItemBUTTON.setText("Order Menu Item");
	        			orderTab.add(orderMenuItemBUTTON);
	        			orderMenuItemBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				orderMenuItemButtonActionPerformed(evt);
	        			}
	        		});*/
	        		
	        		
      		// A tab		
	        JPanel seatTab = new JPanel();
	        
	        // A tab
	        menuTab = new JPanel();
	        menuTab.setLayout(new GridLayout(4,1));
	        		
	        	addMenuItemBUTTON = new JButton();
	        	addMenuItemBUTTON.setText("Add Menu Item");
	        	menuTab.add(addMenuItemBUTTON);
	        	addMenuItemBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			addMenuItemButtonActionPerformed(evt);
	        		}
	        	});
	        	
	        	removeMenuItemBUTTON = new JButton();
	        	removeMenuItemBUTTON.setText("Remove Menu Item");
	        	menuTab.add(removeMenuItemBUTTON);
	        	removeMenuItemBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			removeMenuItemButtonActionPerformed(evt);
	        		}
	        	});
	        	
	        	updateMenuItemBUTTON = new JButton();
	        	updateMenuItemBUTTON.setText("Update Menu Item");
	        	menuTab.add(updateMenuItemBUTTON);
	        	updateMenuItemBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			updateMenuItemButtonActionPerformed(evt);
	        		}
	        	});
	        	
	        	menuTableContainer = displayMenu();

	        	// add scroll pane
	        	menuTab.add(menuTableContainer);
	        
	        
	        // A tab
	        JPanel billTab = new JPanel();

	        	billTab.setLayout(new GridLayout(3,1));
	        
	        		issueBillBUTTON = new JButton();
	        		issueBillBUTTON.setText("Issue Bill");
	        		billTab.add(issueBillBUTTON);
	        		issueBillBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				issueBillButtonActionPerformed(evt);
	        			}
	        		});
		
				viewBillsBUTTON = new JButton();
	        		viewBillsBUTTON.setText("View Bills");
	        		billTab.add(viewBillsBUTTON);
	        		viewBillsBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        			public void actionPerformed(java.awt.event.ActionEvent evt) {
	        				viewBillsButtonActionPerformed(evt);
	        			}
	        		});
	        
	        // A tab
	        
	        // A tab
	        JPanel orderTab = new JPanel();
	        	orderTab.setLayout(new GridLayout(5,1));
    		
        	//Add table Button
	        	startOrderBUTTON = new JButton();
	        	startOrderBUTTON.setText("Start Order");
	        	orderTab.add(startOrderBUTTON);
	        	startOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			startOrderButtonActionPerformed(evt);
	        		}
	        	});
    		
	        	endOrderBUTTON = new JButton();
	        	endOrderBUTTON.setText("End Order");
	        	orderTab.add(endOrderBUTTON);
	        	endOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			endOrderButtonActionPerformed(evt);
	        		}
	        	});
    		
	        	orderMenuItemBUTTON = new JButton();
	        	orderMenuItemBUTTON.setText("Order Menu Item");
	        	orderTab.add(orderMenuItemBUTTON);
	        	orderMenuItemBUTTON.addActionListener(new java.awt.event.ActionListener() {
	        		public void actionPerformed(java.awt.event.ActionEvent evt) {
	        			orderMenuItemButtonActionPerformed(evt);
	        		}
	        	});
	        	
	        	cancelOrderBUTTON = new JButton();
	    		cancelOrderBUTTON.setText("Cancel Order");
	    		orderTab.add(cancelOrderBUTTON);
	    		cancelOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	    			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    				cancelOrderButtonActionPerformed(evt);
	    			}
	    		});
	    		
	    		viewOrderBUTTON = new JButton();
	    		viewOrderBUTTON.setText("View Order");
	    		orderTab.add(viewOrderBUTTON);
	    		viewOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	    			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    				viewOrderButtonActionPerformed(evt);
	    			}
	    		});
	    		
    		JPanel takeOutTab = new JPanel();
        		takeOutTab.setLayout(new GridLayout(5,1));
        		
        		setTakeOutTableBUTTON = new JButton();
        		setTakeOutTableBUTTON.setText("Set Takeout Table");
        		takeOutTab.add(setTakeOutTableBUTTON);
	    		setTakeOutTableBUTTON.addActionListener(new java.awt.event.ActionListener() {
	    			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    				setTakeOutTableButtonActionPerformed(evt);
	    			}
	    		});
	    		
	    		newCustomerBUTTON = new JButton();
	    		newCustomerBUTTON.setText("Create New Customer & Assign Order");
	    		takeOutTab.add(newCustomerBUTTON);
	    		newCustomerBUTTON.addActionListener(new java.awt.event.ActionListener() {
	    			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    				newCustomerButtonActionPerformed(evt);
	    			}
	    		});
	    		
	    		/*
	    		setTakeOutOrderBUTTON = new JButton();
        		setTakeOutOrderBUTTON.setText("Set Takeout Order");
        		takeOutTab.add(setTakeOutOrderBUTTON);
	    		setTakeOutOrderBUTTON.addActionListener(new java.awt.event.ActionListener() {
	    			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    				setTakeOutOrderButtonActionPerformed(evt);
	    			}
	    		});
	    		*/
	    		
	    		
	        
	        
        // Adding the tabs to the right side tabbed pane
        rightSideTabbedPane.addTab("Reservation",reservationTab);
        rightSideTabbedPane.addTab("Table",tableTab);
        rightSideTabbedPane.addTab("Seat",seatTab);
        rightSideTabbedPane.addTab("Menu",menuTab);
        rightSideTabbedPane.addTab("Bill",billTab);
        rightSideTabbedPane.addTab("Order", orderTab);
        rightSideTabbedPane.addTab("Takeout", takeOutTab);
    
        //Add the tabbed pane to this panel.
        add(rightSideTabbedPane);
				
	}
	
	private JScrollPane displayMenu() {
		
		 //Creating JTable
        tableModelMenu = new DefaultTableModel(100,0);
        JTable menuTable = new JTable(tableModelMenu); 
        JScrollPane menuTableContainer = new JScrollPane(menuTable);
        
       
        List<ItemCategory> categoriesList;
        categoriesList = RestoController.getItemCategories();
        int numItemCategories = categoriesList.size();
        //System.out.println(numItemCategories);

        
        // iterates through categories list to create columns
        for(int i=0 ; i<numItemCategories; i++) {
        	ItemCategory currentCat = categoriesList.get(i);
        	String value = String.valueOf(currentCat);
        	tableModelMenu.addColumn(value);
        	//System.out.println(value);
        }
        
        
        // GETTING ALL THE LISTS SO WE CAN THEN DISPLAY THEM
        List<MenuItem> appetizersList = new ArrayList<MenuItem>();
        try {
			appetizersList = RestoController.getMenuItems(ItemCategory.Appetizer);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
        
        List<MenuItem> mainsList = new ArrayList<MenuItem>();
        try {
			mainsList = RestoController.getMenuItems(ItemCategory.Main);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
        
        List<MenuItem> dessertsList = new ArrayList<MenuItem>();
        try {
			dessertsList = RestoController.getMenuItems(ItemCategory.Dessert);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
        
        List<MenuItem> alcBevList = new ArrayList<MenuItem>();
        try {
			alcBevList = RestoController.getMenuItems(ItemCategory.AlcoholicBeverage);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
        
        List<MenuItem> nonAlcBevList = new ArrayList<MenuItem>();
        try {
			nonAlcBevList = RestoController.getMenuItems(ItemCategory.NonAlcoholicBeverage);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
        
        // PRINTING THE LISTS TO THE TABLE
        int numAppetizers = appetizersList.size();
        //System.out.println(numAppetizers);  //debugging
        for(int i=0; i<numAppetizers ; i++)
        {
        	MenuItem current = appetizersList.get(i);
        	String currentName = current.getName();
        	PricedMenuItem pricedItem= current.getCurrentPricedMenuItem();
        	String price = Double.toString(pricedItem.getPrice());
        	currentName+= " - $" + price;
        	
        	//System.out.println(currentName);
        	//menuTable.addRow(currentName);
        	menuTable.setValueAt(currentName, i, 0);
        }
        
        int numMains = mainsList.size();
        for(int i=0; i<numMains ; i++)
        {
        	MenuItem current = mainsList.get(i);
        	String currentName = current.getName();
        	PricedMenuItem pricedItem= current.getCurrentPricedMenuItem();
        	String price = Double.toString(pricedItem.getPrice());
        	currentName+= " - $" + price;
        	//System.out.println(currentName);
        	//menuTable.addRow(currentName);
        	menuTable.setValueAt(currentName, i, 1);
        }
        
        int numDesserts = dessertsList.size();
        for(int i=0; i<numDesserts ; i++)
        {
        	MenuItem current = dessertsList.get(i);
        	String currentName = current.getName();
        	PricedMenuItem pricedItem= current.getCurrentPricedMenuItem();
        	String price = Double.toString(pricedItem.getPrice());
        	currentName+= " - $" + price;
        	//System.out.println(currentName);
        	//menuTable.addRow(currentName);
        	menuTable.setValueAt(currentName, i, 2);
        }
        
        int numAlcBev = alcBevList.size();
        for(int i=0; i<numAlcBev ; i++)
        {
        	MenuItem current = alcBevList.get(i);
        	String currentName = current.getName();
        	PricedMenuItem pricedItem= current.getCurrentPricedMenuItem();
        	String price = Double.toString(pricedItem.getPrice());
        	currentName+= " - $" + price;
        	//System.out.println(currentName);
        	//menuTable.addRow(currentName);
        	menuTable.setValueAt(currentName, i, 3);
        }
        
        int numNonAlcBev = nonAlcBevList.size();
        for(int i=0; i<numNonAlcBev ; i++)
        {
        	MenuItem current = nonAlcBevList.get(i);
        	String currentName = current.getName();
        	PricedMenuItem pricedItem= current.getCurrentPricedMenuItem();
        	String price = Double.toString(pricedItem.getPrice());
        	currentName+= " - $" + price;
        	//System.out.println(currentName);
        	//menuTable.addRow(currentName);
        	menuTable.setValueAt(currentName, i, 4);
        }
        
        return menuTableContainer;
		
	}
	
	public void refreshMenu() {
        
		menuTab.remove(menuTableContainer);
		menuTableContainer = displayMenu();
		menuTab.add(menuTableContainer);
		
		this.repaint();
	}

	public JScrollPane displayTableLayout()
	{
		List<Table> allCurrentTables = new ArrayList<Table>();
		allCurrentTables = RestoController.getCurrentTables();
		
		System.out.println(allCurrentTables.size());
		
		JPanel tablePanel;
		tablePanel = new JPanel();
		//tablePanel.setSize(new Dimension(200,200));
		//getContentPane();
		//tablePanel.setLayout(null);
		
		JScrollPane tableScrollPane = new JScrollPane();
		
		int width;
		int length;
		int xPos;
		int yPos;
		int tabNum;
		
		int sizeMult = 100;
		
		for(Table table: allCurrentTables)
		{
			width = table.getWidth()*sizeMult;
			length = table.getLength()*sizeMult;
			xPos = table.getX()*sizeMult;
			yPos = table.getY()*sizeMult;
			tabNum = table.getNumber();
			
			
			JLabel tableLabel = new JLabel();
			tablePanel.add(tableLabel);	
			
			tableLabel.setText("Table " +tabNum);
			
			tableLabel.setMinimumSize(new Dimension(length,width));
			tableLabel.setPreferredSize(new Dimension(length,width));
			tableLabel.setMaximumSize(new Dimension(length,width));
			
			tableLabel.setText("Table " +tabNum);
			
			tableLabel.setLocation(xPos, yPos);
			
			Status status = table.getStatus();
			if(status == Status.Available)
			{
				tableLabel.setBackground(Color.GREEN);
				tableLabel.setOpaque(true);
			}
			else if (status == Status.NothingOrdered)
			{
				tableLabel.setBackground(Color.YELLOW);
				tableLabel.setOpaque(true);
			}
			else
			{
				tableLabel.setBackground(Color.RED);
				tableLabel.setOpaque(true);
			}		
		}
		tableScrollPane.add(tablePanel);

		return tableScrollPane;
	}


	private JScrollPane displayList() {
		List<Reservation> allReservationsList = new ArrayList<Reservation>();
		allReservationsList = RestoController.getCurrentReservations();
		int numReservations = allReservationsList.size();
		System.out.println("Current num of reservations: " +numReservations);
		
		tableModelReservations = new DefaultTableModel(numReservations,0); //////////set how many rows by how many reservations exist, 0 columns for now
		JTable reservationsTable = new JTable(tableModelReservations); 
		JScrollPane reservationsTableContainer = new JScrollPane(reservationsTable);
		
		//add columns
		tableModelReservations.addColumn("Date");
		tableModelReservations.addColumn("Time");
		tableModelReservations.addColumn("Table");
		tableModelReservations.addColumn("Name");
		tableModelReservations.addColumn("# People");
		tableModelReservations.addColumn("Phone");
		tableModelReservations.addColumn("Email");
		
		List<String> datesList = new ArrayList<String>();
		List<String> timesList = new ArrayList<String>();
		List<String> tablesList = new ArrayList<String>();
		List<String> namesList = new ArrayList<String>();
		List<String> numInPartyList = new ArrayList<String>();
		List<String> phonesList = new ArrayList<String>();
		List<String> emailsList = new ArrayList<String>();
		
		for(Reservation reservation: allReservationsList)
		{
			String date = reservation.getDate().toString();
			String time = reservation.getTime().toString();
			
			List<Table> thisReservationTables = new ArrayList<>();
			thisReservationTables = reservation.getTables();
			String tableStringToDisplay = "";
			for (Table table: thisReservationTables)
			{
				int thisNum = table.getNumber();
				tableStringToDisplay += +thisNum +",";
			}
			
			String name = reservation.getContactName();
			String numInParty = "" +reservation.getNumberInParty();
			String phone = reservation.getContactPhoneNumber();
			String email = reservation.getContactEmailAddress();
			
			//add strings to lists above
			datesList.add(date);
			timesList.add(time);
			tablesList.add(tableStringToDisplay);
			namesList.add(name);
			numInPartyList.add(numInParty);
			phonesList.add(phone);
			emailsList.add(email);
		
		}
		
		//from lists, put into table
		for(int i=0; i<numReservations ; i++)
		{
				String currentDate = datesList.get(i);
		    	String currentTime = timesList.get(i);
		    	String currentTable = tablesList.get(i);
		    	String currentName = namesList.get(i);
		    	String currentNumInParty = numInPartyList.get(i);
		    	String currentPhone = phonesList.get(i);
		    	String currentEmail = emailsList.get(i);
		    	
		    	reservationsTable.setValueAt(currentDate, i, 0);
		    	reservationsTable.setValueAt(currentTime, i, 1);
		    	reservationsTable.setValueAt(currentTable, i, 2);
		    	reservationsTable.setValueAt(currentName, i, 3);
		    	reservationsTable.setValueAt(currentNumInParty, i, 4);
		    	reservationsTable.setValueAt(currentPhone, i, 5);
		    	reservationsTable.setValueAt(currentEmail, i, 6);
		}
		return reservationsTableContainer;
	}
	
	public void refreshReservation() {
        //JPanel reservationTab = new JPanel();
        //reservationTab.setLayout(new GridLayout(3,1));
		reservationTab.remove(reservationsTableContainer);
		reservationsTableContainer = displayList();
		reservationTab.add(reservationsTableContainer);
		//pack();
		this.repaint();
	}
	
	
	private void refreshData()
	{
		errorMessage.setText(error);
		if (error == null || error.length() == 0)
		{
			
			
		}
	}
	
	
	// METHODS WHEN BUTTONS ARE PRESSED
	
	public void addTableButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("add table button was pressed");
		//code goes here
		
		createMyTable.refreshData(); 
		updateTable.setVisible(false);
	    moveTable.setVisible(false);
	    removeTable.setVisible(false);
		createMyTable.setVisible(true); 
		
		// update visuals
		refreshData();
	}
	
	
	public void removeTableButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("remove table button was pressed");		
		//code goes here
		
		removeTable.refreshData(); 
		updateTable.setVisible(false);
	    moveTable.setVisible(false);
	    removeTable.setVisible(true);
		createMyTable.setVisible(false); 

		// update visuals
		refreshData();
	}
	
	
	public void moveTableButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("move table button was pressed");
		//code goes here
		
		moveTable.refreshData();
		updateTable.setVisible(false);
	    moveTable.setVisible(true);
	    removeTable.setVisible(false);
		createMyTable.setVisible(false);

		// update visuals
		refreshData();
	}
	
	
	public void updateTableButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("update table button was pressed");
		//code goes here
		
		updateTable.refreshData(); 
		updateTable.setVisible(true); // Change back
	    moveTable.setVisible(false);
	    removeTable.setVisible(false);
		createMyTable.setVisible(false); 
		//startOrder.refreshData(); 
		//startOrder.setVisible(true); 
		//viewOrder.reset();
        //viewOrder.setVisible(true);
		// update visuals
		refreshData();
		
	}
	
	
	
	public void addReservationButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("add reservation button was pressed");
		
		List<Reservation> allReservationsList = new ArrayList<Reservation>();
		allReservationsList = RestoController.getCurrentReservations();
		int numReservations = allReservationsList.size();
		System.out.println("num of reservations: " +numReservations);
		
		//code goes here
		addReservation.refreshData(); 
		addReservation.setVisible(true);

		// update visuals
		refreshData(); 
	}
	public void startOrderButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("start order button was pressed");		
		//code goes here
		
		startOrder.refreshData();
		orderMenuItem.setVisible(false); 
		startOrder.setVisible(true);
		endOrder.setVisible(false);
		cancelOrder.setVisible(false);
		viewOrder.setVisible(false);

		// update visuals
		refreshData();
	}
	public void endOrderButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("end order button was pressed");		
		//code goes here
		
		endOrder.refreshData();
		orderMenuItem.setVisible(false);
		startOrder.setVisible(false);
		endOrder.setVisible(true);
		cancelOrder.setVisible(false);
		viewOrder.setVisible(false);

		// update visuals
		refreshData();
	}
	
	public void orderMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.out.println("order menu item button was pressed");		
		//code goes here
		
		orderMenuItem.refreshData();
		startOrder.setVisible(false);
		endOrder.setVisible(false);
		orderMenuItem.setVisible(true);
		cancelOrder.setVisible(false);
		viewOrder.setVisible(false);

		// update visuals
		refreshData();
	}
	
	public void viewOrderButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		viewOrder.reset();
        	viewOrder.setVisible(true);
        	startOrder.setVisible(false);
		endOrder.setVisible(false);
		orderMenuItem.setVisible(false);
		cancelOrder.setVisible(false);
		
		refreshData();
	}
	
	public void cancelOrderButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		
		cancelOrder.refreshData();
		viewOrder.setVisible(false);
        	startOrder.setVisible(false);
		endOrder.setVisible(false);
		cancelOrder.setVisible(true);
		orderMenuItem.setVisible(false);
		
		refreshData();
		
		
	}

	public void removeReservationButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		
		
		
		
	}
	
  public void issueBillButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		System.out.println("issue bill button was pressed");
		
		issueBill.refreshData();
		issueBill.setVisible(true);
	  	viewBills.setVisible(false);
		
	}
	
  public void viewBillsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		System.out.println("view bills button was pressed");
		
		viewBills.refreshData();
		issueBill.setVisible(false);
		viewBills.setVisible(true);
		
	}	
  

  public void setTakeOutTableButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
	  System.out.println("set takeout table button was pressed");
	  
	  setTakeOutTable.refreshData();
	  setTakeOutTable.setVisible(true);
	  refreshData();
	  
  }
  
  public void newCustomerButtonActionPerformed(java.awt.event.ActionEvent evt)
  {
	  System.out.println("new customer button was pressed");
	  
	  newCustomer.refreshData();
	  newCustomer.setVisible(true);
	  refreshData();
	  
  }
 

  public void addMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
	  
	  addMenuItem.refreshData();
	  addMenuItem.setVisible(true);
	  updateMenuItem.setVisible(false);
	  removeMenuItem.setVisible(false);

	// update visuals
	  refreshData();
	  
	  
  }
  
  public void removeMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
	  
	  removeMenuItem.refreshData();
	  addMenuItem.setVisible(false);
	  updateMenuItem.setVisible(false);
	  removeMenuItem.setVisible(true);

	// update visuals
	  refreshData();

  }
  
  
  public void updateMenuItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
	  
	  updateMenuItem.refreshData();
	  addMenuItem.setVisible(false);
	  updateMenuItem.setVisible(true);
	  removeMenuItem.setVisible(false);

	// update visuals
	  refreshData();
  
  }

	

	
}
