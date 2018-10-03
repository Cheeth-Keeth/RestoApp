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

import java.awt.Color;
import java.awt.Dimension;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.*;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.view.RestoPage;

public class RemoveMenuItem extends JFrame{
	
	private static final long serialVersionUID = -642631086933549830L;

	private JLabel menuItemLabel;
	private JComboBox<String> menuItemList;
	
	private JButton removeButton;
	private JButton cancelButton;
	
	private JLabel errorMessage;
	private String error = null;
	
	private Integer selectedMenuItem = -1;
	private HashMap<Integer, MenuItem> menuItems;

	private RestoPage restoPage;
	
	

	public RemoveMenuItem(RestoPage restoPage) {
		this.restoPage = restoPage;
		initComponents();
		refreshData();
		
	}
	
	private void initComponents() {
		
		menuItemLabel = new JLabel();
		
		removeButton = new JButton();
		cancelButton = new JButton();
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		setTitle("Remove Menu Item");
		setPreferredSize(new Dimension(500, 230));
		setResizable(false);
		
		menuItemLabel.setText("Menu Item:");
		
		menuItemList = new JComboBox<String>(new String [0]);
		
		menuItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedMenuItem = cb.getSelectedIndex();
			}
		});
		
		removeButton.setText("Remove");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeActionPerformed(evt);
			}
		});
		
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		JSeparator horizontalLineMiddle = new JSeparator();
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(menuItemLabel,100,100,100)
								.addComponent(menuItemList,100,100,450))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(removeButton)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelButton)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(menuItemLabel,100,100,100)
				         .addComponent(menuItemList,100,100,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(removeButton)
						.addComponent(horizontalLineMiddle,100,100,350)
						.addComponent(cancelButton))
						
		);	
		
		pack();
		
	}
	
	protected void refreshData() {
		
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

		}

    }

	private void removeActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		if(selectedMenuItem < 0) {
			error = "A menu item must be selected";
		}
		
		error = error.trim();
		if(error.length() == 0) {
			try {
				MenuItem menuItem = menuItems.get(selectedMenuItem);
				RestoController.removeMenuItem(menuItem);
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
