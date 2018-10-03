package ca.mcgill.ecse223.resto.view;

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

import java.awt.Color;
import java.awt.Dimension;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.*;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class AddMenuItem extends JFrame {

	private static final long serialVersionUID = -6425610823455349830L;
	
	private JLabel newNameLabel;
	private JTextField newNameField;
	
	private JLabel newCategoryLabel;
	private JComboBox<String> itemCategoryList;
	
	private JLabel priceLabel;
	private JTextField priceField;
	
	private JButton addButton;
	private JButton cancelButton;
	
	private JLabel errorMessage;
	private String error = null;
	
	private Integer selectedItemCategory = -1;
	private HashMap<Integer, ItemCategory> itemCategories;

	private RestoPage restoPage;
	


	public AddMenuItem(RestoPage restoPage) {
		this.restoPage = restoPage;
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		setTitle("Add Menu Item");
		setPreferredSize(new Dimension(500,350));
		setResizable(false);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
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
		
		addButton = new JButton();
		addButton.setText("Add");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addButtonActionPerformed(evt);
			}
		});
		
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		//horizontal line element
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
								.addComponent(newNameLabel)
								.addComponent(newNameField,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newCategoryLabel)
								.addComponent(itemCategoryList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(priceLabel)
								.addComponent(priceField,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(addButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelButton)))					     
		);
		
		layout.setVerticalGroup
		(
			layout.createSequentialGroup()
				.addComponent(errorMessage)
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
								.addComponent(addButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelButton))				     
		);
		
		pack();
		
	}	
	
	
	public void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
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
	
	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		
		if(newNameField.getText().trim().equals("")) {
			error = "Must input a name";
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
				
				String newName = newNameField.getText();
				ItemCategory newItemCategory = itemCategories.get(selectedItemCategory);
				String priceString = priceField.getText();
				Double price = Double.parseDouble(priceString);
				RestoController.addMenuItem(newName, newItemCategory, price);
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
