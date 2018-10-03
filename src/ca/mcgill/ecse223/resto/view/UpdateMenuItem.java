package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class UpdateMenuItem extends JFrame {

	private static final long serialVersionUID = -642561086933549830L;
	
	private JLabel menuItemLabel;
	private JComboBox<String> menuItemList;
	
	private JLabel newNameLabel;
	private JTextField newNameField;
	
	private JLabel newCategoryLabel;
	private JComboBox<String> itemCategoryList;
	
	private JLabel priceLabel;
	private JTextField priceField;
	
	private JButton updateButton;
	private JButton cancelButton;
	
	private JLabel errorMessage;
	private String error = null;
	
	private Integer selectedMenuItem = -1;
	private HashMap<Integer, MenuItem> menuItems;
	
	private Integer selectedItemCategory = -1;
	private HashMap<Integer, ItemCategory> itemCategories;

	private RestoPage restoPage;
	
	
	public UpdateMenuItem(RestoPage restoPage) {
		this.restoPage = restoPage;
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		setTitle("Update Menu Item");
		setPreferredSize(new Dimension(500,350));
		setResizable(false);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		menuItemLabel = new JLabel();
		menuItemLabel.setText("Menu Item:");
		
		menuItemList = new JComboBox<String>(new String [0]);
		menuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedMenuItem = cb.getSelectedIndex();
			}
		});
		
		newNameLabel = new JLabel();
		newNameLabel.setText("New name:");
		
		newNameField = new JTextField();
		
		newCategoryLabel = new JLabel();
		newCategoryLabel.setText("New category:");
		
		itemCategoryList = new JComboBox<String>(new String [0]);
		itemCategoryList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedItemCategory = cb.getSelectedIndex();
			}
		});
		
		priceLabel = new JLabel();
		priceLabel.setText("Price");
		
		priceField = new JTextField();
		
		updateButton = new JButton();
		updateButton.setText("Update");
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateButtonActionPerformed(evt);
			}
		});
		
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		
		// horizontal line element
		JSeparator horizontalLineMiddle = new JSeparator();
		
		//Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup
		(
			layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(menuItemLabel)
								.addComponent(menuItemList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newNameLabel)
								.addComponent(newNameField,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newCategoryLabel)
								.addComponent(itemCategoryList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(priceLabel)
								.addComponent(priceField,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(updateButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelButton)))					     
		);
		
		layout.setVerticalGroup
		(
			layout.createSequentialGroup()
				.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
								.addComponent(menuItemLabel)
								.addComponent(menuItemList,20,20,450))
						.addGroup(layout.createParallelGroup()
								.addComponent(newNameLabel)
								.addComponent(newNameField,20,20,450))
						.addGroup(layout.createParallelGroup()
								.addComponent(newCategoryLabel)
								.addComponent(itemCategoryList,20,20,450))
						.addGroup(layout.createParallelGroup()
								.addComponent(priceLabel)
								.addComponent(priceField,20,20,450))
						.addGroup(layout.createParallelGroup()
								.addComponent(updateButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelButton))				     
		);
		
		pack();
		
	}	
		
		
	public void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
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
			
			List<ItemCategory> itemCatList = RestoController.getItemCategories();
			
			itemCategories = new HashMap<Integer, ItemCategory>();
			itemCategoryList.removeAllItems();
			Integer itemCatIndex = 0;
			
			for(ItemCategory cat: itemCatList)
			{
				itemCategories.put(itemCatIndex, cat);
				itemCategoryList.addItem(cat.name());
				itemCatIndex++;
			}	
			
			selectedItemCategory = -1;
			itemCategoryList.setSelectedIndex(selectedItemCategory);
			
			newNameField.setText("");
			priceField.setText("");
			
			}

    	}
	
	private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
			
		error = "";
		
		if(selectedMenuItem < 0) {
			error = "A menu item must be selected";
		}
		
		if(newNameField.getText().trim().equals("")) {
			error = "Must input a new name";
		}
		
		if(selectedItemCategory < 0) {
			error = "An item category must be selected";
		}
		
		if(priceField.getText().trim().equals("")) {
			error = "Must input a price";
		}
		
		if(error.length() == 0) {
			//call controller
			try {
				MenuItem menuItem = menuItems.get(selectedMenuItem);
				String newName = newNameField.getText();
				ItemCategory newItemCategory = itemCategories.get(selectedItemCategory);
				String priceString = priceField.getText();
				Double price = Double.parseDouble(priceString);
				RestoController.updateMenuItem(menuItem, newName, newItemCategory, price);
				restoPage.refreshMenu();
			}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
				
		refreshData();
			
		}
		
	private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
			
			error= "";
			errorMessage.setText(error);
			refreshData();
			pack();
			this.setVisible(false);
		}
		
		
}		
		

