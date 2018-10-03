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



public class UpdateTable extends JFrame{
	
	private static final long serialVersionUID = -6426310869335015542L;
	
	// UI elements
	//Current table information
	private JLabel currentTable; 
	private JComboBox<String> tableList;
	
	//New table number
	private JLabel newTableNumberLabel; 
	private JTextField newNumberText; 
	
	//New Number of seat
	private JLabel newNumberOfSeatLabel; 
	private JTextField newNumberSeatsText; 

	private JButton update;
    private JButton cancel;
	
	private JLabel errorMessage; 
	private String error = null;
	
	//Current tables
	private Integer selectedTable = -1;
	private HashMap<Integer, Table> currentTables;
	
	
	public UpdateTable(){
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
        //Elements for updating table
		currentTable = new JLabel();
        
		newTableNumberLabel = new JLabel();
		newNumberText = new JTextField();
        
		newNumberOfSeatLabel = new JLabel();
		newNumberSeatsText = new JTextField();
        
		update = new JButton();
        cancel = new JButton();
        
		currentTable.setText("CurrentTable: "); 
		tableList = new JComboBox<String>(new String[0]);
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		
		newTableNumberLabel.setText("New Table Number: "); 
		newNumberOfSeatLabel.setText("Number of Seats: ");
		
		// global settings and listeners
	    setTitle("Update Table Information");
		setPreferredSize(new Dimension(500, 200));
		setResizable(false); 
		
		update.setText("Update");
		update.addActionListener(new java.awt.event.ActionListener() {
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
								.addComponent(currentTable)
								.addComponent(tableList,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newTableNumberLabel)
								.addComponent(newNumberText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(newNumberOfSeatLabel)
								.addComponent(newNumberSeatsText,20,20,450))
						.addGroup(layout.createSequentialGroup()
								.addComponent(update)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancel)))
							     
		);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(currentTable)
				         .addComponent(tableList,20,20,450))
				.addGroup(layout.createParallelGroup()
				         .addComponent(newTableNumberLabel)
				         .addComponent(newNumberText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(newNumberOfSeatLabel)
						.addComponent(newNumberSeatsText,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(update)
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
			
			currentTables = new HashMap<Integer, Table>();
			tableList.removeAllItems();
			Integer index = 0;
			for (Table table : RestoController.getCurrentTables()) { //Fixed display currentTables.?????
				currentTables.put(index, table);
				tableList.addItem("Table:" + table.getNumber() + " " + "Seats:" +  table.numberOfCurrentSeats());
				index++;
			};
			selectedTable = -1;
			tableList.setSelectedIndex(selectedTable);

		}

    }

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {
    	// clear error message and basic input validation
    			error = "";	
    			if (selectedTable < 0) {
    				error = "Table needs to be selected for update!";
    			}
    			if (newNumberText.getText().equals(" ")) {
				String oldTableNumber = Integer.toString(currentTables.get(selectedTable).getNumber()); 
				newNumberText.setText(oldTableNumber);
		    }
		    if(newNumberSeatsText.getText().equals(" ")) {
				String oldNumberOfSeats = Integer.toString(currentTables.get(selectedTable).numberOfCurrentSeats()); 
				newNumberSeatsText.setText(oldNumberOfSeats);
		    }
    			error = error.trim();
    			
    			if (error.length() == 0) {
    				// call the controller
    				try {
    					
	    			    int newTableNumber = Integer.parseInt(newNumberText.getText()); 
	    				int newNumberOfSeats = Integer.parseInt(newNumberSeatsText.getText()); 
	    				Table table = currentTables.get(selectedTable); 
	    				RestoController.updateTable( table,newTableNumber, newNumberOfSeats);
    					
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
