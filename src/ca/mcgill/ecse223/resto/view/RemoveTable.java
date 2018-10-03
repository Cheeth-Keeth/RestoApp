package ca.mcgill.ecse223.resto.view;

import java.util.HashMap;

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
import ca.mcgill.ecse223.resto.model.Table;

public class RemoveTable extends JFrame {
	
	private static final long serialVersionUID = -6426310869335015542L;
	
	//Update table
	private JLabel currentTable; 
	private JComboBox<String> tableList;
	
	private JButton remove;
    private JButton cancel;
    
    private JLabel errorMessage; 
	private String error = null;
	
	private Integer selectedTable = -1;
	private HashMap<Integer, Table> currentTables;
	
	public RemoveTable(){
	
		initComponents();
		refreshData();
		
	}
	
	private void initComponents() {
		
		currentTable = new JLabel();
		
		remove = new JButton();
        cancel = new JButton();
        
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        setTitle("Remove Table");
        setPreferredSize(new Dimension(500, 190));
        setResizable(false);
        
        currentTable.setText("Current Table: "); 
        
		tableList = new JComboBox<String>(new String[0]);
		
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedTable = cb.getSelectedIndex();
			}
		});
		
		remove.setText("Remove");
		remove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeActionPerformed(evt);
			}
		});
		
		cancel.setText("Cancel");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
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
								.addComponent(currentTable,100,100,100)
								.addComponent(tableList,100,100,450))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(remove)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancel)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(currentTable,100,100,100)
				         .addComponent(tableList,100,100,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(remove)
						.addComponent(horizontalLineMiddle,100,100,350)
						.addComponent(cancel))
						
		);	
		
		pack();
		
	}
	
	protected void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
			currentTables = new HashMap<Integer, Table>();
			tableList.removeAllItems();
			Integer index = 0;
			
			for (Table table : RestoController.getCurrentTables()) {
				currentTables.put(index, table);
				tableList.addItem("#" + table.getNumber());
				index++;
			};
			
			selectedTable = -1;
			tableList.setSelectedIndex(selectedTable);

		}

    }
	
	private void removeActionPerformed(java.awt.event.ActionEvent evt) {
    	
    			error = "";	
    			
    			if (selectedTable < 0) {
    				
    				error = "A table must be selected";
    				
    			}
    			
    			error = error.trim();
    			
    			if (error.length() == 0) {
    				
    				try {
    					 
    					Table table = currentTables.get(selectedTable); 
    					RestoController.removeTable(table);
    					
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

}

