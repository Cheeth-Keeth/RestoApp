package ca.mcgill.ecse223.resto.view;

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


public class CreateTable extends JFrame {
	private static final long serialVersionUID = -6426310869335015542L;
	
	//UI elements 
	
	private JLabel newTableNumberLabel; 
	private JTextField newNumberText; 
	
	// New Number of seats 
	private JLabel newNumberOfSeatLabel; 
	private JTextField newNumberSeatsText; 
	
	// New X position 
	private JLabel newXPositionLabel; 
	private JTextField newXPositionText; 
	
	// New Y position
		private JLabel newYPositionLabel; 
		private JTextField newYPositionText; 
	
	// New Width 
		private JLabel newWidthLabel; 
		private JTextField newWidthText; 
	
	// New Length
		private JLabel newLengthLabel; 
		private JTextField newLengthText; 
	
	//Current tables	
	//	private HashMap<Integer, Table> currentTables;
	
		private JButton create;
	    private JButton cancel;
		
		private JLabel errorMessage; 
		private String error = null;
	
	public CreateTable() {
		initComponents();
		refreshData();
	}
	
private void initComponents() {
		
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//Elements for updating table
		newTableNumberLabel = new JLabel();
		newNumberText = new JTextField();
        
		newNumberOfSeatLabel = new JLabel();
		newNumberSeatsText = new JTextField();
		
		newXPositionLabel = new JLabel();
		newXPositionText = new JTextField();
		
		newYPositionLabel = new JLabel();
		newYPositionText = new JTextField();
		
		newWidthLabel = new JLabel();
		newWidthText = new JTextField();
		
		newLengthLabel = new JLabel();
		newLengthText = new JTextField();
        
		create = new JButton();
        cancel = new JButton();
		
		newTableNumberLabel.setText("New Table Number: "); 
		newNumberOfSeatLabel.setText("Number of Seats: ");
		newXPositionLabel.setText("X Position");
		newYPositionLabel.setText("Y Position");
		newWidthLabel.setText("Width of Table");
		newLengthLabel.setText("Length of Table");
	
		// global settings and listeners
	    setTitle("Creat Table");
		setPreferredSize(new Dimension(500, 275));
		setResizable(false); 
		
		create.setText("create");
		create.addActionListener(new java.awt.event.ActionListener() {
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
		
		// horizontal line element
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
								.addComponent(newTableNumberLabel)
								.addComponent(newNumberText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newNumberOfSeatLabel)
								.addComponent(newNumberSeatsText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newXPositionLabel)
								.addComponent(newXPositionText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newYPositionLabel)
								.addComponent(newYPositionText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newWidthLabel)
								.addComponent(newWidthText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newNumberOfSeatLabel)
								.addComponent(newNumberSeatsText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newLengthLabel)
								.addComponent(newLengthText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(create)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancel)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(newTableNumberLabel)
				         .addComponent(newNumberText,20,20,450))
				.addGroup(layout.createParallelGroup()
				         .addComponent(newNumberOfSeatLabel)
				         .addComponent(newNumberSeatsText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(newXPositionLabel)
						.addComponent(newXPositionText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(newYPositionLabel)
						.addComponent(newYPositionText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(newWidthLabel)
						.addComponent(newWidthText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(newLengthLabel)
						.addComponent(newLengthText,20,20,450)) 
		        .addGroup(layout.createParallelGroup()
						.addComponent(create)
						.addComponent(horizontalLineMiddle,20,20,350)
						.addComponent(cancel))
						
		);		
		pack();		
	}


		protected void refreshData() {
			// error
			errorMessage.setText(error);
			if (error == null || error.length() == 0) {
		
				// populate page with data
				newNumberText.setText("");
				newNumberSeatsText.setText("");
				newLengthText.setText("");
				newWidthText.setText("");
				newXPositionText.setText("");
				newYPositionText.setText("");
			}
		}

	    private void updateActionPerformed(java.awt.event.ActionEvent evt) {
	    			//int result = Integer.parseInt(newXPositionText.getText());
	    			String regex = "[0-9]+";
	    			String regex1 = "[1-9]+";
	    		
	    			error = "";
	    
	    			
	    			if (newXPositionText.getText().matches(regex)== false)
	    			{
	    				error = "X position must contain only numbers" + error; 
	    			}
	    			if (newYPositionText.getText().matches(regex) == false)
	    			{
	    				error = "Y position must contain only numbers" +error; 
	    			}
	    			if (newNumberSeatsText.getText().matches(regex) == false)
	    			{
	    				error = "Number of seats must be positive" + error; 
	    			}
	    			if (newNumberText.getText().matches(regex) == false)
	    			{
	    				error = "Table number must be positive" + error; 
	    			}
	    			if (newWidthText.getText().matches(regex) == false)
	    			{
	    				error = " Width must be positive" + error; 
	    			}
	    			if (newLengthText.getText().matches(regex) == false)
	    			{
	    				error = "Length must be positive" + error; 
	    			}
	    			
	    			
	    
	    			error = error.trim();
	    			
	    			if (error.length() == 0) {
	    				// call the controller
	    				try {
	    					int newTableNumber = Integer.parseInt(newNumberText.getText()); 
	    					int newNumberOfSeats = Integer.parseInt(newNumberSeatsText.getText()); 
	    					int newXPosition = Integer.parseInt(newXPositionText.getText()); 
	    					int newYPosition = Integer.parseInt(newYPositionText.getText());
	    					int newWidth = Integer.parseInt(newWidthText.getText());
	    					int newLength = Integer.parseInt(newLengthText.getText());
	    					
	    					RestoController.createTable(newTableNumber, newXPosition, newYPosition, newWidth, newLength, newNumberOfSeats);
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
