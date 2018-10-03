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
import ca.mcgill.ecse223.resto.model.Order;

public class EndOrder extends JFrame {
	
	private static final long serialVersionUID = -6426310869335015542L;
	
	//Update table
	private JLabel currentOrder; 
	private JComboBox<String> orderList;
	
	private JButton end;
    private JButton cancel;
    
    private JLabel errorMessage; 
	private String error = null;
	
	private Integer selectedOrder = -1;
	private HashMap<Integer, Order> currentOrders;
	
	public EndOrder(){
	
		initComponents();
		refreshData();
		
	}
	
	private void initComponents() {
		
		currentOrder = new JLabel();
		
		end = new JButton();
        cancel = new JButton();
        
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);
        
        setTitle("End Order");
        setPreferredSize(new Dimension(500, 190));
        setResizable(false);
        
        currentOrder.setText("Current Order: "); 
        
		orderList = new JComboBox<String>(new String[0]);
		
		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedOrder = cb.getSelectedIndex();
			}
		});
		
		end.setText("Remove");
		end.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				endOrderActionPerformed(evt);
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
								.addComponent(currentOrder,100,100,100)
								.addComponent(orderList,100,100,450))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(end)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancel)))
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(currentOrder,100,100,100)
				         .addComponent(orderList,100,100,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(end)
						.addComponent(horizontalLineMiddle,100,100,350)
						.addComponent(cancel))
						
		);	
		
		pack();
		
	}
	
	protected void refreshData() {
		
		errorMessage.setText(error);
		
		if (error == null || error.length() == 0) {
			
			String tablesOfOrder = "";
			
			currentOrders = new HashMap<Integer, Order>();
			orderList.removeAllItems();
			Integer index = 0;
			
			for (Order order : RestoController.getCurrentOrders()) {
				for (int i = 0; i < order.getTables().size(); i++) {
					
					Table table = order.getTable(i);
					tablesOfOrder = tablesOfOrder + "Table #: " + String.valueOf(table.getNumber()) + ", ";
					
				}
				currentOrders.put(index, order);
				orderList.addItem("Order: # " + order.getNumber() + " " + tablesOfOrder);
				index++;
				tablesOfOrder = "";
			}
			
			selectedOrder = -1;
			orderList.setSelectedIndex(selectedOrder);

		}

    }
	

	
	
	private void endOrderActionPerformed(java.awt.event.ActionEvent evt) {
    	
    			error = "";	
    			
    			if (selectedOrder < 0) {
    				
    				error = "An order must be selected";
    				
    			}
    			
    			error = error.trim();
    			
    			if (error.length() == 0) {
    				
    				try {
    					 
    					Order order = currentOrders.get(selectedOrder); 
    					RestoController.endOrder(order);
    					
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
