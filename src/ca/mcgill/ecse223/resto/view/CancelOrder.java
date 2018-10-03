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

//import com.sun.tools.javac.jvm.Items;

import java.awt.Color;
import java.awt.Dimension;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;

public class CancelOrder extends JFrame {

	private static final long serialVersionUID = -6426310869335015542L;
	
	private JLabel currentTable; 
	private JLabel currentOrderItem; 
	private JLabel currentOrder; 
	private JLabel currentSeat; 
	private JComboBox<String> tableList; 
	private JComboBox<String> orderItemList; 
	private JComboBox<String> orderList;
	private JComboBox<String> seatList; 
 	
	private JButton cancelTableOrder; 
	private JButton cancelOrder; 
	private JButton cancelOrderItem; 
	private JButton cancelSeat;
	private JButton cancel; 
	
	private JLabel errorMessage; 
	private String error = null;
	
	private Integer selectedTable = -1;
	private HashMap<Integer, Table> currentTables;
	
	private Integer selectedOrderItem = -1;
	private HashMap<Integer, OrderItem> currentOrderItems;
	
	private Integer selectedOrder = -1;
	private HashMap<Integer, Order> currentOrders;
	
	private Integer selectedSeat = -1 ; 
	private HashMap <Integer, Seat> currentSeats; 
	
	public CancelOrder() {
		
		initComponents();
		refreshData(); 
	}
	
	private void initComponents() { 
		currentTable = new JLabel();
		currentOrderItem = new JLabel();
		currentOrder = new JLabel();
		currentSeat = new JLabel();
		
		cancelTableOrder = new JButton();
		cancelOrderItem = new JButton();
		cancelOrder = new JButton();
		cancelSeat = new JButton();
		cancel = new JButton();
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		setTitle("Cancel Order");
		setPreferredSize(new Dimension(1000, 300)); //500,190
		setResizable(false);
		
		currentTable.setText("Table:");
		currentOrderItem.setText("Order Item:");
		currentOrder.setText("Order:");
		currentSeat.setText("Seat:");
		
		tableList = new JComboBox<String>(new String [0]);
		orderItemList = new JComboBox<String>(new String [0]);
		orderList = new JComboBox<String>(new String [0]);
		seatList = new JComboBox<String>(new String [0]);
		
		tableList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> ab = (JComboBox<String>) evt.getSource();
		        selectedTable = ab.getSelectedIndex();
			}
		});
		
		orderItemList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> bb = (JComboBox<String>) evt.getSource();
		        selectedOrderItem = bb.getSelectedIndex();
			}
		});
		 
		orderList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedOrder = cb.getSelectedIndex();
			}
		});
		
		seatList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> db = (JComboBox<String>) evt.getSource();
		        selectedSeat = db.getSelectedIndex();
			}
		});
		
		cancelTableOrder.setText("Cancel Table Order");
		cancelTableOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelTableOrderActionPerformed(evt);
			}
		});
		
		cancelOrderItem.setText("Cancel Order Item");
		cancelOrderItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelOrderItemActionPerformed(evt);
			}
		});
		
		cancelOrder.setText("Cancel Order for tables");
		cancelOrder.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelOrderActionPerformed(evt);
			}
		});
		

		cancelSeat.setText("Cancel order Items for seat");
		cancelSeat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelSeatActionPerformed(evt);
			}
		});
		
		
		cancel.setText("Cancel");
		cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});
		
		JSeparator horizontalLineMiddle = new JSeparator();
		JSeparator horizontalLineMiddleOne = new JSeparator();
		JSeparator horizontalLineMiddleTwo = new JSeparator();
		JSeparator horizontalLineMiddleThree = new JSeparator();
		
		//layout 
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
								.addComponent(tableList,20,20,350))
						.addGroup(layout.createSequentialGroup()
								.addComponent(currentOrderItem)
								.addComponent(orderItemList,20,20,350))
						.addGroup(layout.createSequentialGroup()
								.addComponent(currentOrder)
								.addComponent(orderList,20,20,350))
						.addGroup(layout.createSequentialGroup()
								.addComponent(currentSeat)
								.addComponent(seatList,20,20,350))
					
						.addGroup(layout.createSequentialGroup()
								.addComponent(cancelTableOrder)
								.addComponent(horizontalLineMiddle)
								.addComponent(cancelOrderItem)
								.addComponent(horizontalLineMiddleOne)
								.addComponent(cancelOrder)
								.addComponent(horizontalLineMiddleTwo)
								.addComponent(cancelSeat)
								.addComponent(horizontalLineMiddleThree)
								.addComponent(cancel)))
							
							     
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(currentTable)
				         .addComponent(tableList,20,20,250))
				.addGroup(layout.createParallelGroup()
				         .addComponent(currentOrderItem)
				         .addComponent(orderItemList,20,20,250))
		        .addGroup(layout.createParallelGroup()
						.addComponent(currentOrder)
						.addComponent(orderList,20,20,450))
		        .addGroup(layout.createParallelGroup()
						.addComponent(currentSeat)
						.addComponent(seatList,20,20,450))
		       
		        .addGroup(layout.createParallelGroup()
						.addComponent(cancelTableOrder)
						.addComponent(horizontalLineMiddle)
						.addComponent(cancelOrderItem)
						.addComponent(horizontalLineMiddleOne)
						.addComponent(cancelOrder)
						.addComponent(horizontalLineMiddleTwo)
						.addComponent(cancelSeat)
						.addComponent(horizontalLineMiddleThree)
						.addComponent(cancel))
						
		);	
		
		pack();
		
	}
		
		protected void refreshData() {
			
			errorMessage.setText(error);
			
			if (error == null || error.length() == 0) {
				
				currentTables = new HashMap<Integer, Table>();
				currentOrderItems = new HashMap<Integer, OrderItem>();
				currentOrders = new HashMap<Integer, Order>();
				currentSeats = new HashMap<Integer, Seat>();
				tableList.removeAllItems();
				orderItemList.removeAllItems();
				orderList.removeAllItems();
				seatList.removeAllItems();
				
				Integer indexZero = 0;
				Integer indexOne = 0; 
				Integer indexTwo = 0; 
				Integer indexThree = 0; 
				
				
				
				for (Order order : RestoController.getCurrentOrders()) {
					List <Table> tables = order.getTables();
					for (Table table: tables) {
						int number = table.getNumber(); 
						currentTables.put(indexZero, table);
						tableList.addItem("#" + number + table.getStatus() );
						indexZero++;
					}
				}
				selectedTable = -1;
				tableList.setSelectedIndex(selectedTable);
				
				
			
					for (Order order : RestoController.getCurrentOrders()) {
					
					List <Table> tables = order.getTables();
					for (Table table : tables) {
						List <Seat> seats = table.getSeats();
						
						for (int i = 0; i<seats.size(); i++) {
							if (seats.get(i).hasOrderItems()) {
								List <OrderItem> items = seats.get(i).getOrderItems();
								
								for (OrderItem item:items) {
									currentOrderItems.put(indexOne, item);
									String itemName = item.getPricedMenuItem().getMenuItem().getName();
									orderItemList.addItem("Table #" + table.getNumber() + "Seat #"+i +"" + itemName + table.getStatus());
									indexOne++;
								}
								
							}
						}
					}
					
					
				} 	
			
					selectedOrderItem = -1;
					orderItemList.setSelectedIndex(selectedOrderItem);
					
					for (Order order : RestoController.getCurrentOrders()) {
						
						
						int number = order.getNumber();
						
						currentOrders.put(indexTwo, order);
						
						orderList.addItem("Order #" + number );
						
						indexTwo++;
					
	
			}
					

							
				selectedOrder = -1;
				orderList.setSelectedIndex(selectedOrder);
				
	
			for (Order order : RestoController.getCurrentOrders()) {
					
					List <Table> tables = order.getTables();
					for (Table table : tables) {
						List <Seat> seats = table.getSeats();
						
						for (int i = 0; i<seats.size(); i++) {
							if (seats.get(i).hasOrderItems()) {
								
									
		
									currentSeats.put(indexThree, table.getSeat(i));
									seatList.addItem("Table #" + table.getNumber() + "Seat #"+i);
									indexThree++;

								
							}
						}
					}
	
				}
			
			selectedSeat = -1;
			seatList.setSelectedIndex(selectedSeat);
			}

	    
		}

		private void cancelTableOrderActionPerformed(java.awt.event.ActionEvent evt) {
	    	
	    			error = "";	
	    			
	    			if (selectedTable < 0) {
	    				error = "A table must be selected";
	    			}
	    			
	    			error = error.trim();
	    			
	    			if (error.length() == 0) {
	    				
	    				try {
	    					 
	    					Table table = currentTables.get(selectedTable); 
	    					RestoController.cancelOrder(table);
	    	
	    					//Order order = currentOrders.get(selectedOrder); 
	    					//RestoController.cancelOrder(order);
	    					
	    				} 
	    				catch (InvalidInputException e) {
	    					
	    					error = e.getMessage();
	    					
	    				}
	    				
	    			}
	
	    		refreshData();
	    		
	    }
		
		private void cancelOrderItemActionPerformed (java.awt.event.ActionEvent evt) {
			error = "";
			
			if (selectedOrderItem <0)
			{
				error = "A Order Item must be selected";
			}
			
			error = error.trim();
			
			if (error.length() == 0) {
				
				try {
					 
					
					OrderItem orderItem = currentOrderItems.get(selectedOrderItem); 
					RestoController.cancelOrderItem(orderItem);
					
					
				} 
				catch (InvalidInputException e) {
					
					error = e.getMessage();
					
				}
			}
				refreshData();
				
		}
		
		private void cancelOrderActionPerformed (java.awt.event.ActionEvent evt) {
			error = "";
			
			if (selectedOrder<0)
			{
				error = "A Order must be selected";
			}
			
			error = error.trim();
			
			if (error.length() == 0) {
				
				try {
					Order order = currentOrders.get(selectedOrder); 
					RestoController.cancelOrder(order);
				} 
				catch (InvalidInputException e) {
					
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
		 
		 private void cancelSeatActionPerformed(java.awt.event.ActionEvent evt) {
		        
			 error = "";
				
				if (selectedSeat<0)
				{
					error = "A seat must be selected";
				}
				
				error = error.trim();
				
				if (error.length() == 0) {
					
					try {
						 
						/*List<Table> tables = order.getTables();
						
						List<Table> tablesForOrder = new ArrayList<>();
				 		for(Table table: tables)
						{
							tablesForOrder.add(table);
						}*/
						
						Seat seat = currentSeats.get(selectedSeat);
						
						List<OrderItem> items = seat.getOrderItems();
						
						List<OrderItem> itemsToBeCancelled = new ArrayList<>();
						
						for(OrderItem item: items)
						{
							itemsToBeCancelled.add(item);
						}
						
						
						for (OrderItem item : itemsToBeCancelled) {
						 
							RestoController.cancelOrderItem(item);
							
						}
						
					} 
					catch (InvalidInputException e) {
						
						error = e.getMessage();
						
					}
				}
					refreshData();
	        
	    }

	
	}
