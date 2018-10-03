package ca.mcgill.ecse223.resto.view;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import ca.mcgill.ecse223.resto.controller.RestoController;
import ca.mcgill.ecse223.resto.application.RestoApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;

public class ViewBills extends JFrame{
	
	private static final long serialVersionUID = -6426310869335015542L;
	
	//Current tables
	private Integer selectedBill = -1;
	private HashMap<Integer, Bill> bills;
		
	private JButton view; 
		
	private JLabel bill; 
	private JComboBox<String> billList;
		
		
	private JLabel errorMessage; 
	private String error = null;
	
	//View
	private static final int HEIGHT_OVERVIEW_TABLE = 200;
	private DefaultTableModel overviewDtm;
	private String overviewColumnNames[] = {"price"};
	private JTable viewBill;
	private JScrollPane overviewScrollPane;
		
	public ViewBills(){
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		// Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
	    //Elements for updating table
		bill = new JLabel();
	
		bill.setText("Bill: "); 
		billList = new JComboBox<String>(new String[0]);
		billList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
		        JComboBox<String> cb = (JComboBox<String>) evt.getSource();
		        selectedBill = cb.getSelectedIndex();
			}
		});
		
		view = new JButton();
		view.setText("view");
		view.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewActionPerformed(evt);
			}
		});
			
		viewBill = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {		
				Component c = super.prepareRenderer(renderer, row, column);
			    c.setBackground(Color.WHITE);
				return c;
			}
		};
			
		overviewScrollPane = new JScrollPane(viewBill);
		this.add(overviewScrollPane);
		Dimension d = viewBill.getPreferredSize();
		overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
		overviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			
		setTitle("View Bills");
		setPreferredSize(new Dimension(500, 200));
		setResizable(false); 
			
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(bill)
								.addComponent(billList,20,20,450)
								.addComponent(view)) 
						.addGroup(layout.createSequentialGroup()
								//.addComponent(viewItemTable)
								.addComponent(overviewScrollPane)))	
		);	
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				         .addComponent(bill)
				         .addComponent(billList,20,20,450)
				         .addComponent(view))
				.addGroup(layout.createParallelGroup()
						//.addComponent(viewItemTable)
						.addComponent(overviewScrollPane))
		);		
		pack();	
	}
	protected void reset() {
		error = null; 
		selectedBill = -1;
		refreshData(); 
	}
	protected void refreshData() {
		
		// error
		errorMessage.setText(error);
		if (error == null || error.length() == 0) {
				
			bills = new HashMap<Integer, Bill>();
			billList.removeAllItems();
			Integer index = 0;
			RestoApp r = RestoApplication.getRestoApp();
			
			for (Bill bill : r.getBills()) { 
				bills.put(index, bill);
				billList.addItem("Bill #" + index);					
				index++;
			};
			selectedBill = -1;
			billList.setSelectedIndex(selectedBill);
			
		}
		
		/*error = "";	
		if (selectedTable < 0) {
			error = "Please select a table!";
		}
		error = error.trim();
		// daily overview
			overviewDtm = new DefaultTableModel(0, 0);
			overviewDtm.setColumnIdentifiers(overviewColumnNames);
			viewItemTable.setModel(overviewDtm);
			if (error.length() == 0) {
				try {
					for (OrderItem orderItem : RestoController.getOrderItems(currentTables.get(selectedTable))) {
						String itemName = orderItem.getPricedMenuItem().getMenuItem().getName();
						Double itemPrice = orderItem.getPricedMenuItem().getPrice(); 
							
									
						if(orderItem.numberOfSeats() > 1) {
							Object[] obj = {itemName, "$" + itemPrice, "Yes(" + orderItem.numberOfSeats() +")"};
							overviewDtm.addRow(obj);
						}
						else {
							Object[] obj = {itemName, "$" + itemPrice, "No"};
							overviewDtm.addRow(obj);
						}
					}
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					error = e.getMessage(); 
				}
				Dimension d = viewItemTable.getPreferredSize();
				overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
			}*/
			
			//daily overview
			overviewDtm = new DefaultTableModel(0, 0);
			overviewDtm.setColumnIdentifiers(overviewColumnNames);
			viewBill.setModel(overviewDtm);
			
		pack();
    }
	

		
	private void viewActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";	
		if (selectedBill < 0) {
			error = "Please select a table!";
		}
		error = error.trim();
		// daily overview
			overviewDtm = new DefaultTableModel(0, 0);
			overviewDtm.setColumnIdentifiers(overviewColumnNames);
			viewBill.setModel(overviewDtm);
			
			Double total = 0.0;
			
			if (error.length() == 0) {
				
				for (Seat seat : bills.get(selectedBill).getIssuedForSeats()) {
					
					List<OrderItem> items = seat.getOrderItems();
				
					for (OrderItem orderItem : items) {
	
						Double itemPrice = orderItem.getPricedMenuItem().getPrice(); 
						
						Double nSeats = (double) orderItem.numberOfSeats();
						Double quantity = (double) orderItem.getQuantity();
	
						double multiply = itemPrice*quantity/nSeats;
						
						total += multiply;
									
					}
					
				}
				
				Object[] lastRow = {"Total: " + total};
				overviewDtm.addRow(lastRow);
				Dimension d = viewBill.getPreferredSize();
				overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
				if(error == "") {
					errorMessage.setText(error);
				}
			}
			if(!(error.length() == 0)) {
				refreshData(); 
			}
	}
	
}
