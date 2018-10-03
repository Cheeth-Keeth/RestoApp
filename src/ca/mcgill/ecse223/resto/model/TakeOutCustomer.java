/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package ca.mcgill.ecse223.resto.model;
import java.io.Serializable;
import java.util.*;

// line 110 "../../../../../RestoAppPersistence.ump"
// line 111 "../../../../../RestoAppv3.ump"
public class TakeOutCustomer implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, TakeOutCustomer> takeoutcustomersByPhoneNumber = new HashMap<String, TakeOutCustomer>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TakeOutCustomer Attributes
  private String name;
  private String phoneNumber;

  //TakeOutCustomer Associations
  private Order order;
  private RestoApp restoApp;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TakeOutCustomer(String aName, String aPhoneNumber, Order aOrder, RestoApp aRestoApp)
  {
    name = aName;
    if (!setPhoneNumber(aPhoneNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate phoneNumber");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create takeOutCustomer due to order");
    }
    boolean didAddRestoApp = setRestoApp(aRestoApp);
    if (!didAddRestoApp)
    {
      throw new RuntimeException("Unable to create takeOutCustomer due to restoApp");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    String anOldPhoneNumber = getPhoneNumber();
    if (hasWithPhoneNumber(aPhoneNumber)) {
      return wasSet;
    }
    phoneNumber = aPhoneNumber;
    wasSet = true;
    if (anOldPhoneNumber != null) {
      takeoutcustomersByPhoneNumber.remove(anOldPhoneNumber);
    }
    takeoutcustomersByPhoneNumber.put(aPhoneNumber, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public static TakeOutCustomer getWithPhoneNumber(String aPhoneNumber)
  {
    return takeoutcustomersByPhoneNumber.get(aPhoneNumber);
  }

  public static boolean hasWithPhoneNumber(String aPhoneNumber)
  {
    return getWithPhoneNumber(aPhoneNumber) != null;
  }

  public Order getOrder()
  {
    return order;
  }

  public RestoApp getRestoApp()
  {
    return restoApp;
  }

  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (aNewOrder == null)
    {
      //Unable to setOrder to null, as takeOutCustomer must always be associated to a order
      return wasSet;
    }
    
    TakeOutCustomer existingTakeOutCustomer = aNewOrder.getTakeOutCustomer();
    if (existingTakeOutCustomer != null && !equals(existingTakeOutCustomer))
    {
      //Unable to setOrder, the current order already has a takeOutCustomer, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Order anOldOrder = order;
    order = aNewOrder;
    order.setTakeOutCustomer(this);

    if (anOldOrder != null)
    {
      anOldOrder.setTakeOutCustomer(null);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setRestoApp(RestoApp aRestoApp)
  {
    boolean wasSet = false;
    if (aRestoApp == null)
    {
      return wasSet;
    }

    RestoApp existingRestoApp = restoApp;
    restoApp = aRestoApp;
    if (existingRestoApp != null && !existingRestoApp.equals(aRestoApp))
    {
      existingRestoApp.removeTakeOutCustomer(this);
    }
    restoApp.addTakeOutCustomer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    takeoutcustomersByPhoneNumber.remove(getPhoneNumber());
    Order existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.setTakeOutCustomer(null);
    }
    RestoApp placeholderRestoApp = restoApp;
    this.restoApp = null;
    if(placeholderRestoApp != null)
    {
      placeholderRestoApp.removeTakeOutCustomer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "restoApp = "+(getRestoApp()!=null?Integer.toHexString(System.identityHashCode(getRestoApp())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 113 "../../../../../RestoAppPersistence.ump"
  private static final long serialVersionUID = 1651651896441652L ;

  
}