package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class StartOrder extends JFrame {

	private static final long serialVersionUID = -6426310869335015542L;
	
	private JLabel table;
	private JComboBox<String> tableList;
	
	private JButton cancel;
	private JButton addTable;
	private JButton startOrder;
	
	private JLabel errorMessage;
	private String error = null;
	
	private Integer selectedTable = -1;
	private HashMap<Integer, Table> tables;
	
	List<Table> tablesForOrder;
	
	public StartOrder(){
		
		initComponents();
		refreshData();
		
	}
	
	protected void initComponents() {
		
		table = new JLabel();
		
		cancel = new JButton();
		addTable = new JButton();
		startOrder = new JButton();
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		tablesForOrder = new ArrayList<Table>();
		
		setTitle("Start Order");
		setPreferredSize(new Dimension(500, 190));
		setResizable(true);
		
		table.setText("Table: ");
		
		tableList = new JComboBox<String>(new String[0]);
		
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		
		startOrder.setText("Start Order");
		startOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startOrderActionPerformed(evt);
			}
		});
		
		cancel.setText("Cancel");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		addTable.setText("Add Table");
		addTable.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addTableActionPerformed(evt);
			}
		});
		
		JSeparator horizontalLineMiddleOne = new JSeparator();
		JSeparator horizontalLineMiddleTwo = new JSeparator();
		
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
								.addComponent(table,100,100,100)
								.addComponent(tableList,100,100,450))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(startOrder)
								.addComponent(horizontalLineMiddleOne)
								.addComponent(addTable)
								.addComponent(horizontalLineMiddleTwo)
								.addComponent(cancel)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(table,100,100,100)
				         .addComponent(tableList,100,100,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(startOrder)
						.addComponent(horizontalLineMiddleOne)
						.addComponent(addTable)
						.addComponent(horizontalLineMiddleTwo,100,100,350)
						.addComponent(cancel))
		        
						
		);	
		
		pack();
		
	}
	
	protected void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
			tables = new HashMap<Integer, Table>();
			tablesForOrder = new ArrayList<Table>();
			tableList.removeAllItems();
			Integer index = 0;
			for (Table table : RestoController.getCurrentTables()) { 
				tables.put(index, table);
				tableList.addItem("Table:" + table.getNumber());					
				index++;
			};
			selectedTable = -1;
			tableList.setSelectedIndex(selectedTable);
			
			
		}
	}
	
	private void startOrderActionPerformed(java.awt.event.ActionEvent evt) {
    	
		error = "";	
			
		if (selectedTable < 0) {
				
			error = "Must select table to start order";
				
		}
			
		error = error.trim();
			
		if (error.length() == 0) {
				
				try {
					
					RestoController.startOrder(tablesForOrder);
				
					
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
	
	private void addTableActionPerformed(java.awt.event.ActionEvent evt) {
		
		error = "";
		
		if (selectedTable < 0) {
			
			error = "A table must be selected";
			
		}
		
		error = error.trim();
		
		if (error.length() == 0) {

			Table currentTable = tables.get(selectedTable);
			tablesForOrder.add(currentTable);
			
		}
	
	}

}