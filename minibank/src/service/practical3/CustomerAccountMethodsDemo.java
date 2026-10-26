package service.practical3;

import java.util.Scanner;

class Customer implements Cloneable {
    private static long counter = 100;
    private final String customerId, name, email, mobile;
    private Address address;
    Customer(String name, String email, String mobile) {
        customerId = "CUST" + (++counter); this.name=name; this.email=email; this.mobile=mobile;
    }
    public String getCustomerId(){return customerId;} public String getName(){return name;}
    public String getEmail(){return email;} public String getMobile(){return mobile;}
    public Address getAddress(){return address;} public void setAddress(Address address){this.address=address;}
    public static class Address {
        private final String line, city, pincode;
        public Address(String line,String city,String pincode){this.line=line;this.city=city;this.pincode=pincode;}
        public String getLine(){return line;} public String getCity(){return city;} public String getPincode(){return pincode;}
        public String toString(){return line+", "+city+" - "+pincode;}
    }
    public Customer clone(){try{return (Customer)super.clone();}catch(CloneNotSupportedException e){throw new AssertionError(e);}}
}
class Account {
    private final String accountNumber; private final String ownerName; private final long balance;
    Account(String ownerName,long balance,String accountNumber){this.ownerName=ownerName;this.balance=balance;this.accountNumber=accountNumber;}
    public String getAccountNumber(){return accountNumber;} public String getOwnerName(){return ownerName;} public long getBalance(){return balance;}
    public String toString(){return accountNumber+" | "+ownerName+" | "+balance;}
    public boolean equals(Object obj){if(this==obj)return true;if(!(obj instanceof Account other))return false;return accountNumber.equals(other.accountNumber);}
    public int hashCode(){return accountNumber.hashCode();}
}
public class CustomerAccountMethodsDemo {
    private static Scanner sc;
    private static String ask(String prompt){System.out.print(prompt);return sc.nextLine().trim();}
    private static long amount(String prompt){while(true){try{long n=Long.parseLong(ask(prompt));if(n>=0)return n;}catch(NumberFormatException ignored){}System.out.println("Enter a non-negative whole number.");}}
    public static void main(String[] args){ run(new Scanner(System.in)); }
    public static void run(Scanner scanner){
        sc = scanner;
        String name=ask("Customer name: "), email=ask("Email: "), mobile=ask("Mobile: ");
        Customer c1=new Customer(name,email,mobile);
        Customer.Address address=new Customer.Address(ask("Street/address: "),ask("City: "),ask("Pincode: "));
        c1.setAddress(address);
        System.out.println("\n--- Customer Details ---");System.out.println("ID: "+c1.getCustomerId());System.out.println("Name: "+c1.getName());System.out.println("Email: "+c1.getEmail());System.out.println("Mobile: "+c1.getMobile());System.out.println("Address: "+c1.getAddress());
        Customer c2=c1.clone();
        System.out.println("\n--- Cloned Customer ---");System.out.println("Name: "+c2.getName());System.out.println("ID: "+c2.getCustomerId());System.out.println("Clone is a different object: "+(c1!=c2));
        String number=ask("Account number for first object: "), owner=ask("Account holder: ");long balance=amount("Account balance (Rs.): ");
        String compareNumber=ask("Account number for comparison object: ");
        Account a1=new Account(owner,balance,number), a2=new Account(owner,balance,compareNumber);
        System.out.println("\n--- Account Details ---");System.out.println(a1);System.out.println("Accounts equal: "+a1.equals(a2));System.out.println("Same hash code: "+(a1.hashCode()==a2.hashCode()));System.out.println("Is Account object: "+(a1 instanceof Account));
    }
}
