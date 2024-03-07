import java.util.*;

class Item
{
	private int itemId;
	private String itemDescription;
	private double quantityAvailable;
	private double price;
	static HashMap <Integer, Item> itemTable = new HashMap<Integer,Item>();
	static int counter = 1000;
	
	Item(String itemDescription, double quantityAvailable, double price)
	{
		this.itemId = ++counter;
		this.itemDescription = itemDescription;
		this.quantityAvailable = quantityAvailable;
		this.price = price;
		itemTable.put(this.itemId,this);
	}
	public int getItemId()
	{
		return this.itemId;
	}
	public double getQuantity()
	{
		return this.quantityAvailable;
	}

	public double getPrice()
	{
		return this.price;
	}

	public void updateQuantity(double quantity)
	{
		this.quantityAvailable-=quantity;
	}

	public static Item getItem(int itemId)
	{
		return itemTable.get(itemId);
	}
    public static void printItem()
    {
        for (Map.Entry <Integer,Item>item:itemTable.entrySet())
        {
            System.out.println("ItemId:"+item.getKey());
            Item showItem=item.getValue();
            System.out.println("ItemName:"+showItem.itemDescription);
            System.out.println("ItemQty:"+showItem.quantityAvailable);
            System.out.println("ItemPrice:"+showItem.price);
        }
    }
}
class Customer
{
    protected String mobileNumber;
    protected String name;
    protected int customerId;
    protected double points;
    static int counter=2000;
    static HashMap <Integer, Customer> customerTable = new HashMap<Integer,Customer>();


    Customer(String name,String mobileNumber)
    {
        this.name=name;
        this.mobileNumber=mobileNumber;
        this.customerId=++counter;
        this.points=0;
        customerTable.put(this.customerId,this);
    }
    public String getName()
    {
        return this.name;
    }
    public double getPoints()
    {
        return this.points;
    }
    public void updatePoints(double amount)
    {
        this.points+=amount*0.1;
    }
    public void redeemPoints(double amount)
    {
        this.points=this.points-amount;
    }
    public String getMobileNumber()
    {
        return this.mobileNumber;
    }
    public void updateMobileNumber(String mobileNumber)
    {
         this.mobileNumber=mobileNumber;
    }
    public static Customer getCustomer(int customerId)
    {
        return customerTable.get(customerId);
    }

}
class PremiumCustomer extends Customer
{
    private String type;
    private String address;
    private String mailId;
    PremiumCustomer(String name, String mobileNumber,String type,String address,String mailId)
    {
        super(name,mobileNumber);
        this.type=type;
        this.address=address;
        this.mailId=mailId;
    }
    public String getType()
    {
        return this.type;
    }
    public String getAddress()
    {
        return this.address;
    }
    public String getmailId()
    {
        return this.mailId;
    }
}

class Sale
{
	private int saleId;
	private double amount;
	private double gst;
	private double netAmount;
    private double discount;

	static int counter = 0;

	Sale()
	{
		this.saleId = ++counter;
		this.amount = 0;
	}
	public double getAmount()
	{
		return this.amount;
	}

	public void updateAmount(double netPrice)
	{
		this.amount += netPrice;
	}

	public double calculateGst()
	{
		this.gst = this.amount * 0.18;
		return this.gst;
	}

	public double calculateNetAmount()
	{
		this.netAmount = this.amount + this.gst - this.discount;
		return this.netAmount;
	}
    public double getDiscount(double amount,String type)
    {
        if(type=="")
        {
            return this.amount*0.05;
        }
        if(type=="silver")
        {
            return this.amount*0.1;
        }
        if(type=="gold")
        {
            return this.amount*0.15;
        }
        if(type=="platinum")
        {
            return this.amount*0.2;
        }
        return 0;
    }
}

class SaleDetail
{
		private int no;
		private Sale sale;
		private Item item;
		private double quantity;
		private double netPrice;

		SaleDetail(int no, Sale sale, Item item)
		{
			this.no = no;
			this.sale = sale;
			this.item = item;
		}

		public boolean addToSale(double quantity)
		{
			if(this.item.getQuantity() >= quantity)
			{
				this.netPrice = this.item.getPrice() * quantity;
				sale.updateAmount(this.netPrice);
				return true;
			}
			else
			{
				System.out.println("Required quantity not available");
				return false;
			}
		}	
}

class Driver
{
	public static void main(String args[])
	{	
		Scanner input = new Scanner(System.in);
		boolean flag = true;
		Item item=null;
		int itemId=0;
        Customer customer=null;
        PremiumCustomer premiumCustomer=null;
		while(flag)
		{ 
			System.out.println("1.Add the Products to the List");
			System.out.println("2.To make a Sale");
			System.out.println("3.To get the Quantity of the product");
			System.out.println("4.To see the product in the List");
            System.out.println("5.Add the Customer");
            System.out.println("6.show the Customer List");
            System.out.print("      Enter the option to perform:");

			int option = input.nextInt();
			
			switch(option)
			{
				case 1:
					System.out.print("Enter the name of the product:");
					String itemName=input.next();
					System.out.print("Enter the Quantity of the product:");
					double quantityAvailable = input.nextDouble();
					System.out.print("Enter the price of the product:");
					double price = input.nextDouble();
					item = new Item(itemName,quantityAvailable,price);
					break;

				case 2:
					Sale s = new Sale();
                    String type="";
                    System.out.print("Enter 1 for Old Customer and 0 for new Customer:");
                    int cus=input.nextInt();
                    if(cus==0)
                    {   
                        System.out.println("Enter 5");
                        break;

                    }
                    System.out.print("Enter the Customer Id:");
                    int customerId=input.nextInt();
                    customer=Customer.getCustomer(customerId);
                    if (customer instanceof PremiumCustomer) {
                         premiumCustomer = (PremiumCustomer) customer;
                         type=(premiumCustomer.getType());
                    }
					int no=0;
					while(true){
						System.out.println("Enter -1 to stop your sale");
						System.out.print("Enter the itemID:");
					     itemId= input.nextInt();
					    if (itemId ==-1){
					        break;
					    }
                        no+=1;
					     item = Item.getItem(itemId);
						 System.out.print("Enter the Quantity Required:");
					     double qty=input.nextDouble();
					     SaleDetail sd = new SaleDetail(no,s,item);
					     if(sd.addToSale(qty))
					     {
					         System.out.println("Added..");
					     }   
					}
                    
                    double amount=s.getAmount();
                    double discount=s.getDiscount(amount,type);
                    double totalAmount=amount-discount;
                    double GST=s.calculateGst();
                    double netAmount=s.calculateNetAmount();
                    customer.updatePoints(netAmount);
					double currentPoints=customer.getPoints();
                    System.out.println("Customer Name:"+customer.getName());
                    System.out.println("Customer Number:"+customer.getMobileNumber());
                    System.out.println("Amount:"+amount);
                    System.out.println("Discount:"+discount);
                    System.out.println("TotalAmount:"+totalAmount);
                    System.out.println("GST:"+GST);
                    System.out.println("Net Amount:"+netAmount);
                    System.out.println("Current Points:"+currentPoints);
                    
					
					break;
					

				case 3:
					 itemId = input.nextInt();
					 item= Item.getItem(itemId);
					 System.out.println(item.getQuantity());
					break;

                case 4:
                    Item.printItem();
                    break;
				case 5:
					System.out.print("Enter the Customer Name:");
                    String name=input.next();
                    System.out.print("Enter the Customer mobileNumber:");
                    String mobileNumber=input.next();
                    System.out.print("Enter 1 for PremiumCustomer and 0 for NormalCustomer:");
                    int check=input.nextInt();
                    if(check==1)
                    {
                        System.out.print("Enter the Type:");
                        String Customertype=input.next();
                        System.out.print("Enter the Address:");
                        String address=input.next();
                        System.out.print("Enter the mailId:");
                        String mailId=input.next();
                        premiumCustomer = new PremiumCustomer(name,mobileNumber,Customertype,address,mailId);

                    }
                    else
                    {
                        customer=new Customer(name,mobileNumber);
                    }
					break;
                
			}
		}
	}
}