package ca.mcgill.ecse223.resto.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class OrderMenuItem extends JFrame {

	private static final long serialVersionUID = -6426310869335015542L;
	
	private JLabel seat;
	private JComboBox<String> seatList;
	
	/*private JLabel itemCategory;
	private JComboBox<String> itemCategoryList;*/
	
	private JLabel menuItem;
	private JComboBox<String> menuItemList;
	
	private JLabel quantity;
	private JTextField quantityText;
	
	private JButton cancel;
	private JButton addSeat;
	//private JButton addItemCategory;
	private JButton orderMenuItem;
	
	private JLabel errorMessage;
	private String error = null;
	

	
	private Integer selectedMenuItem = -1;
	private HashMap<Integer, MenuItem> menuItems;
	
	private Integer selectedSeat = -1;
	private HashMap<Integer, Seat> seats;
	
	/*private Integer selectedItemCategory = -1;
	private HashMap<Integer, ItemCategory> itemCategories;*/
	
	List<Seat> seatsForOrder;
	//ItemCategory itemCategoryForOrder;
	
	public OrderMenuItem(){
		
		initComponents();
		refreshData();
		
	}
	
	protected void initComponents() {
		
		seat = new JLabel();
		menuItem = new JLabel();
		//itemCategory = new JLabel();
		
		quantity = new JLabel();
		quantityText = new JTextField();
		
		
		cancel = new JButton();
		addSeat = new JButton();
		orderMenuItem = new JButton();
		//addItemCategory = new JButton();
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		seatsForOrder = new ArrayList<Seat>();
		
		
		setTitle("Order Menu Item");
		setPreferredSize(new Dimension(450, 200));
		setResizable(true);
		
		seat.setText("Seat: ");
		seatList = new JComboBox<String>(new String[0]);
		seatList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedSeat = cb.getSelectedIndex();
			}
		});
		
		menuItem.setText("Menu Item: ");
		menuItemList = new JComboBox<String>(new String[0]);
		menuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedMenuItem = cb.getSelectedIndex();
			}
		});
	
		
		quantity.setText("Quantity: ");
		
		
		orderMenuItem.setText("Order Menu Item");
		orderMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				orderMenuItemActionPerformed(evt);
			}
		});
	
		cancel.setText("Cancel");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		addSeat.setText("Add Seat");
		addSeat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addSeatActionPerformed(evt);
			}
		});
		
		
		
		JSeparator horizontalLineMiddleOne = new JSeparator();
		JSeparator horizontalLineMiddleTwo = new JSeparator();
		JSeparator horizontalLineMiddleThree = new JSeparator();
		
		//Layout
		
	/*	GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(seat)
								.addComponent(seatList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(menuItem)
								.addComponent(menuItemList, 20, 20, 450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(quantity)
								.addComponent(quantityText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(orderMenuItem)
								.addComponent(horizontalLineMiddleOne)
								.addComponent(addSeat)
								.addComponent(horizontalLineMiddleTwo)
								.addComponent(cancel))));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(seat)
				         .addComponent(seatList,20,20,450))
				.addGroup(layout.createParallelGroup()
						.addComponent(menuItem)
						.addComponent(menuItemList, 20, 20, 450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(quantity)
						.addComponent(quantityText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(orderMenuItem)
						.addComponent(horizontalLineMiddleOne,20,20,350)
						.addComponent(addSeat)
						.addComponent(horizontalLineMiddleTwo,20,20,350)
						.addComponent(cancel)));	
		
		
		pack();*/
		//Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(seat)
								.addComponent(seatList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(menuItem)
								.addComponent(menuItemList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(quantity)
								.addComponent(quantityText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(orderMenuItem)
								.addComponent(horizontalLineMiddleTwo)
								.addComponent(addSeat)
								.addComponent(horizontalLineMiddleOne)
								.addComponent(cancel))));
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(seat)
				         .addComponent(seatList,20,20,450))
				.addGroup(layout.createParallelGroup()
				         .addComponent(menuItem)
				         .addComponent(menuItemList,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(quantity)
						.addComponent(quantityText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(orderMenuItem)
						.addComponent(horizontalLineMiddleTwo, 20, 20, 350)
						.addComponent(addSeat)
						.addComponent(horizontalLineMiddleOne,20,20,350)
						.addComponent(cancel)));		
		pack();	
	}
		
	
	
	protected void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
			quantityText.setText("");
			
			
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
	     
	     
	    
	        
	        menuItems = new HashMap<Integer, MenuItem>();
			menuItemList.removeAllItems();
			Integer menuItemIndex = 0;
			
		
			
			for(MenuItem appetizer: appetizersList)
			{
				
				menuItems.put(menuItemIndex, appetizer);
				menuItemList.addItem(appetizer.getItemCategory().name() +  ":  " + appetizer.getName());
				menuItemIndex++;
			}
			for(MenuItem main: mainsList)
			{
				menuItems.put(menuItemIndex, main);
				menuItemList.addItem(main.getItemCategory().name() +  ":  " + main.getName());
				menuItemIndex++;
			}
			for(MenuItem dessert: dessertsList)
			{
				menuItems.put(menuItemIndex, dessert);
				menuItemList.addItem(dessert.getItemCategory().name() +  ":  " + dessert.getName());
				menuItemIndex++;
			}
			for(MenuItem alc: alcBevList)
			{
				menuItems.put(menuItemIndex, alc);
				menuItemList.addItem(alc.getItemCategory().name() +  ":  " + alc.getName());
				menuItemIndex++;
			}
			for(MenuItem nonAlc: nonAlcBevList)
			{
				menuItems.put(menuItemIndex, nonAlc);
				menuItemList.addItem(nonAlc.getItemCategory().name() +  ":  " + nonAlc.getName());
				menuItemIndex++;
			}
			
			
			selectedMenuItem = -1;
			menuItemList.setSelectedIndex(selectedMenuItem);
			
	
			
			seats = new HashMap<Integer, Seat>();
			seatsForOrder = new ArrayList<Seat>();
			seatList.removeAllItems();
			Integer seatIndex = 0;
			
			for (Table table : RestoController.getCurrentTables()) {
				for (int i = 0; i < table.getSeats().size(); i++) {
					
					Seat seat = table.getSeat(i);
					seats.put(seatIndex, seat);
					seatList.addItem("Table: #" + table.getNumber() + " " + "Seat: #" + i);
					seatIndex++;
					
				}
			}
			
			selectedSeat = -1;
			seatList.setSelectedIndex(selectedSeat);

		}

    }
	
	private void orderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
    	
		error = "";	
			
		if (seatsForOrder.size() <= 0) {
				
			error = "Must select seats to place order";
				
		}
		
		String regex = "[0-9]+";
		if(quantityText.getText().matches(regex)==false||Integer.parseInt(quantityText.getText())<=0)
		{
			error = "Quantity must be a number greater than 0";
		}
		
		if(selectedMenuItem<0)
		{
			error = "Must select menu item";
		}
			
		error = error.trim();
			
		if (error.length() == 0) {
				
				try {
					
					int quantity = Integer.parseInt(quantityText.getText());
					MenuItem menuItem = menuItems.get(selectedMenuItem);
					RestoController.orderMenuItem(menuItem, quantity, seatsForOrder);
					
				} catch (InvalidInputException e) {
					
					error = e.getMessage();
					
				}
				
			}
	
		
		refreshData();
	
	}


	private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
	
		error = ""; 
		errorMessage.setText(error);
		refreshData();	
		pack(); 
		this.setVisible(false);
	}
	
	private void addSeatActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		
		if (selectedSeat < 0) {
			
			error = "A seat must be selected";
			
		}
		
		error = error.trim();
		
		if (error.length() == 0) {

			Seat seat = seats.get(selectedSeat);
			seatsForOrder.add(seat);
			
		}
	
	}


}