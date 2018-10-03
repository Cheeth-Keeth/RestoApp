package ca.mcgill.ecse223.resto.view;
import java.awt.Color;

import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Dimension;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Table;

public class MoveTable extends JFrame {
	
	private static final long serialVersionUID = -6426310869335015542L;
	
		// UI elements
		//Current table information
		private JLabel currentTable; 
		private JComboBox<String> tableList;
		
		//New x coordinate
		private JLabel newXCoordinateLabel; 
		private JTextField newXCoordinateText; 
		
		//New y coordinate
		private JLabel newYCoordinateLabel; 
		private JTextField newYCoordinateText; 
		
		private JButton move;
		private JButton cancel;
		
		private JLabel errorMessage;
		private String error = null;
		
		//Current tables
		private Integer selectedTable = -1;
		private HashMap<Integer, Table> currentTables;
		
		public MoveTable()
		{
			initComponents();
			refreshData();
		}
		
		private void initComponents()
		{
			//Elements for error message
			errorMessage = new JLabel();
			errorMessage.setForeground(Color.RED);
			
			//Elements for moving table
			currentTable = new JLabel();
			
			newXCoordinateLabel = new JLabel();
			newXCoordinateText = new JTextField();
			
			newYCoordinateLabel = new JLabel();
			newYCoordinateText = new JTextField();
			
			move = new JButton();
			cancel = new JButton();
			
			currentTable.setText("CurrentTable: ");
			tableList = new JComboBox<String>(new String[0]);
			tableList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
			        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
			        selectedTable = cb.getSelectedIndex();
				}
			});
			newXCoordinateLabel.setText("New X Coordinate: ");
			newYCoordinateLabel.setText("New Y Coordinate: ");
			
			//global settings and listeners
			setTitle("Move Table Information");
			setPreferredSize(new Dimension(500,200));
			setResizable(false);
			
			move.setText("Move");
			move.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					updateActionPerformed(evt);
				}
			});
			
			cancel.setText("Cancel");
			cancel.addActionListener(new java.awt.event.ActionListener() {
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
			layout.setHorizontalGroup(
					layout.createParallelGroup()
					.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup()
							.addGroup(layout.createSequentialGroup()
									.addComponent(currentTable)
									.addComponent(tableList,20,20,450))
							.addGroup(layout.createSequentialGroup()
									.addComponent(newXCoordinateLabel)
									.addComponent(newXCoordinateText,20,20,450))
							.addGroup(layout.createSequentialGroup()
									.addComponent(newYCoordinateLabel)
									.addComponent(newYCoordinateText,20,20,450))
							.addGroup(layout.createSequentialGroup()
									.addComponent(move)
									.addComponent(horizontalLineMiddle)
									.addComponent(cancel))));
			layout.setVerticalGroup(
					layout.createSequentialGroup()
					.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup()
					         .addComponent(currentTable)
					         .addComponent(tableList,20,20,450))
					.addGroup(layout.createParallelGroup()
					         .addComponent(newXCoordinateLabel)
					         .addComponent(newXCoordinateText,20,20,450))
			        .addGroup(layout.createParallelGroup()
							.addComponent(newYCoordinateLabel)
							.addComponent(newYCoordinateText,20,20,450))
			        .addGroup(layout.createParallelGroup()
							.addComponent(move)
							.addComponent(horizontalLineMiddle,20,20,350)
							.addComponent(cancel)));		
			pack();	
		}
		
		protected void refreshData() 
		{
			//error
			errorMessage.setText(error);
			if(error == null  || error.length() == 0) 
			{
				//populate page with data
				newXCoordinateText.setText("");
				newYCoordinateText.setText("");
				
				currentTables = new HashMap<Integer, Table>();
				tableList.removeAllItems();
				Integer index = 0;
				for (Table currentTable : RestoController.getCurrentTables()) {
					currentTables.put(index, currentTable);
					tableList.addItem("#" + currentTable.getNumber() + "(" +  currentTable.getX() + "," + currentTable.getY() + ")");
					index++;
				};
				selectedTable = -1;
				tableList.setSelectedIndex(selectedTable);
				
			}
		}
		 private void updateActionPerformed(java.awt.event.ActionEvent evt) {
		    	// clear error message and basic input validation
		    			error = "";	
		    			String regex = "[0-9]+";
		    			if (selectedTable < 0) {
		    				error = "Table needs to be selected for update";
		    			}
		    			if(newXCoordinateText.getText().matches(regex)==false)
		    			{
		    				error = "Coordinate must contain only numbers";
		    			}
		    			if(newYCoordinateText.getText().matches(regex)==false)
		    			{
		    				error = "Coordinate must contain only numbers";
		    			}
		    			if (newXCoordinateText.getText() == null) {
		    				String oldXCoordinate = Integer.toString(currentTables.get(selectedTable).getX()); 
		    				newXCoordinateText.setText(oldXCoordinate);
		    			}
		    			if(newYCoordinateText.getText() == null) {
		    				String oldYCoordinate = Integer.toString(currentTables.get(selectedTable).getY()); 
		    				newYCoordinateText.setText(oldYCoordinate);
		    			}
		    			error = error.trim();
		    			
		    			if (error.length() == 0) {
		    				// call the controller
		    				try {
		    					int newXCoordinate = Integer.parseInt(newXCoordinateText.getText()); 
		    					int newYCoordinate = Integer.parseInt(newYCoordinateText.getText()); 
		    					Table table = currentTables.get(selectedTable); 
		    					RestoController.moveTable( table,newXCoordinate, newYCoordinate);
		    				} catch (InvalidInputException e) {
		    					error = e.getMessage();
		    				}
		    			}

		    		// update visuals
		    		refreshData();			
		    }

		   
		    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
		        //Refresh data and exit page
		       	error = ""; 
		        errorMessage.setText(error);
		        refreshData();
		        pack(); 
		        this.setVisible(false);
		    }
		    
}

