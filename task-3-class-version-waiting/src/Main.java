import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    //    Declaring length for fuel queue
    static int queueLength = 6;

    //    Declare fuelStock
    static int fuelStock = 6600;

    //    Object list for store all pump objects
    static Pump[] pumpArray = new Pump[5];

    static File logFile = new File("fuel-queue-log.txt");

    static WaitingList waitingList = new WaitingList(queueLength);

    //    Integer list for store income of each pump.
    static Integer[] pumpIncome = new Integer[5];

    //    main method
    public static void main(String[] args) {

//        creating and adding Pump objects in to pumpArray
        for (int i = 0; i < pumpArray.length; i++) {
            pumpArray[i]=new Pump(queueLength);
        }

//        Set starting value of every pump income to zero
        Arrays.fill(pumpIncome, 0);

//        Scanner to get input from user
        Scanner sc = new Scanner(System.in);

//        Main while loop to make main menu repetitive
        while(true){
            System.out.println("\n-----------------Main Menu--------------------");
            System.out.println("100 or VFQ: View all Fuel Queues.");
            System.out.println("101 or VEQ: View all Empty Queues.");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
            System.out.println("104 or PCQ: Remove a served customer.");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file.");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining Fuel Stock.");
            System.out.println("109 or AFS: Add Fuel Stock.");
            System.out.println("110 or IFQ: Income from each queue.");
            System.out.println("999 or EXT: Exit the Program.");
            System.out.println("-------------------------------------");

//            Getting user input and check whether user input matchers with any operation given above
            System.out.print("Enter your choice : ");
            String choice = sc.next();

            if (choice.equals("100") || choice.equalsIgnoreCase("VFQ")){
                System.out.println("100/VFQ option Selected\n");
                viewFuelQueue();
            } else if (choice.equals("101") || choice.equalsIgnoreCase("VEQ")) {
                System.out.println("101/VEQ option Selected\n");
                viewEmptyQueue();
            }else if (choice.equals("102") || choice.equalsIgnoreCase("VCQ")) {
                System.out.println("102/VCQ option Selected\n");
                addCustomer();

            } else if (choice.equals("103") || choice.equalsIgnoreCase("RCQ")) {
                System.out.println("103/RCQ option Selected\n");
                removeSpecificCus();
            }else if (choice.equals("104") || choice.equalsIgnoreCase("PCQ")) {
                System.out.println("104/PCQ option Selected\n");
                removeServedCus();
            }else if (choice.equals("105") || choice.equalsIgnoreCase("VCS")) {
                System.out.println("105/VCS option Selected\n");
                sortPumpQueue();

            }else if (choice.equals("106") || choice.equalsIgnoreCase("SPD")) {
                System.out.println("106/SPD option Selected\n");
                saveDataToFile();

            }
            else if (choice.equals("107") || choice.equalsIgnoreCase("LPD")) {
                System.out.println("107/LPD option Selected\n");
                loadDataFromFile();

            }
            else if (choice.equals("108") || choice.equalsIgnoreCase("STK")) {
                System.out.println("108/STK option Selected\n");
                currentFuelStock();
            }
            else if (choice.equals("109") || choice.equalsIgnoreCase("AFS")) {
                System.out.println("109/AFS option Selected\n");
                addFuelStock();

            }
            else if (choice.equals("110") || choice.equalsIgnoreCase("IFQ")) {
                System.out.println("110/IFQ option Selected\n");
                showQueueIncome();

            }
            else if (choice.equals("999") || choice.equalsIgnoreCase("EXT")) {
                System.out.println("Program terminated.");
                break;
            }else{
                System.out.println("Invalid Input");
            }

//            Check fuel stock status and show warning everytime menu loops.
            checkFuelStock();

        }

    }

    //    Show every fuel queue entry.
    private static void viewFuelQueue(){
        for (int i = 0; i < pumpArray.length; i++) {
            showQueueNames(i);
            System.out.println();
        }
    }

    //    Show every fuel queue entry with empty space\spaces.
    private static void viewEmptyQueue(){
        for (int i = 0; i < pumpArray.length; i++) {
            if (pumpArray[i].size()<queueLength){
                showQueueNames(i);
                System.out.println("No. of empty queues : "+pumpArray[i].getNoOfEmpty());
                System.out.println();
            }
        }
    }

    //    Format and show queue of every pump in a table
    private static void showQueueNames(int pumpNum){
        System.out.println("---------------------------- PUMP "+(pumpNum+1)+" -----------------------------");
        System.out.printf("%15s | %15s | %15s| %10s\n", "First Name", "Last Name", "Vehicle No.","Litres");
        System.out.println("-----------------------------------------------------------------");
        for (int j = 0; j < pumpArray[pumpNum].size(); j++) {
            System.out.printf("%15s | %15s | %15s | %10s\n", pumpArray[pumpNum].getPassenger(j).getPassengerFirstName(), pumpArray[pumpNum].getPassenger(j).getPassengerLastName(), pumpArray[pumpNum].getPassenger(j).getVehicleNumber(), pumpArray[pumpNum].getPassenger(j).getNoOfLiters());
        }
        if (pumpArray[pumpNum].size()<queueLength){
            int emptyLength = queueLength - pumpArray[pumpNum].size();
            for (int k = 0; k < emptyLength; k++) {
                System.out.printf("%15s | %15s | %15s | %10s\n", "empty","empty","-","-");
            }
        }
    }

    //    Check availability and add customer to the shortest fuel queue
    private static void addCustomer(){
        int maxEmpty=-1;
        int index = 0;
        for (int i = pumpArray.length-1; i >= 0; i--) {
            if (pumpArray[i].getNoOfEmpty()>=maxEmpty){
                maxEmpty = pumpArray[i].getNoOfEmpty();
                index = i;
            }
        }
        if (pumpArray[index].size()<6){
            System.out.println("Customer will queue to PUMP "+(index+1));
            addCustomerToPump(pumpArray[index]);
        }else{
            if (waitingList.isFull()) {
                System.out.println("NOTICE!! No fuel queue or waiting list space available to assign customer.");
            }else {
                System.out.println("Customer will assign to waiting list.");
                addCustomerToPump(pumpArray[index]);
            }
        }

    }

    //    Getting customer information and create object from that data
    private static void addCustomerToPump(Pump pump){
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String vehicleNum;
        int noOfLitres;
        while (true) {
            System.out.print("Enter customer first name : ");
            firstName = scanner.nextLine();
            if (firstName.length()!=0){
                break;
            }else {
                System.out.println("WARNING!! First name cannot be empty\n");
            }
        }

        while (true) {
            System.out.print("Enter customer last name : ");
            lastName = scanner.nextLine();
            if (lastName.length()!=0){
                break;
            }else {
                System.out.println("WARNING!! Last name cannot be empty\n");
            }
        }
        while (true) {
            System.out.print("Enter vehicle number : ");
            vehicleNum = scanner.nextLine();
            if (vehicleNum.length()!=0){
                break;
            }else {
                System.out.println("WARNING!! Vehicle number cannot be empty\n");
            }
        }
        while (true) {
            try {
                System.out.print("No. of litres : ");
                noOfLitres = scanner.nextInt();
                if (noOfLitres != 0) {
                    break;
                } else {
                    System.out.println("WARNING!! No. of litres cannot be zero\n");
                }
            } catch (Exception e) {
                System.out.println("WARNING!! No. of litres must be integer");
                scanner.nextLine();
            }
        }
        if (fuelStock-noOfLitres<0){
            System.out.println("Not enough fuel to serve entered customer. Restock and try again.");
        }else {
            if (pump.size() < 6) {
                pump.addPassenger(firstName, lastName, vehicleNum, noOfLitres);
            } else {
                waitingList.enQueue(firstName, lastName, vehicleNum, noOfLitres);
            }
            fuelStock -= noOfLitres;
        }

    }

    //    Removing customer from selected pump
    public static void removeSpecificCus(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter \n1 - 1st pump\n2 - 2nd pump\n3 - 3rd pump\n4 - 4th pump\n5 - 5th pump\n");
        try{
            System.out.print("What pump customer do you want to remove : ");
            int pumpNum = scanner.nextInt();
            for (int i = 0; i < pumpArray.length; i++) {
                if (pumpNum == i+1) {
                    if (pumpArray[i].getNoOfEmpty() < queueLength) {
                        System.out.println("Pump " + (i + 1) + " selected");
                        showCustomerIndex(pumpArray[i],i);
                    }
                    else {
                        System.out.println("Pump " + (i + 1) + " is empty");
                    }
                }
            }

        }catch (Exception e){
            System.out.println("Only integers are allowed");
        }
    }

    //    Show customer name and vehicle number to user with index to remove any customer within the queue
    public static void showCustomerIndex(Pump pump,int pumpNum){
        Scanner scanner = new Scanner(System.in);
        int index = 0;
        System.out.printf("%2s | %15s | %15s | %15s\n","Index", "First Name", "Last Name", "Vehicle No.");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < pump.size(); i++) {
            System.out.printf("%5s | %15s | %15s | %15s\n",i+1, pump.getPassenger(i).getPassengerFirstName(), pump.getPassenger(i).getPassengerLastName(), pump.getPassenger(i).getVehicleNumber());
            index++;
        }
        System.out.printf("\n%5s | %15s |", "999", "Exit");
        while (true) {
            try {
                System.out.print("\nSelect customer by Index : ");
                int selectedIndex = scanner.nextInt();

                if (selectedIndex <= index) {
                    fuelStock+=pump.getPassenger(selectedIndex-1).getNoOfLiters();
                    pump.remove(selectedIndex-1);
                    if (!waitingList.isEmpty()){
                        Passenger firstWaitedPassenger = waitingList.removeCus();
                        System.out.println(firstWaitedPassenger.getPassengerFirstName()+" "+firstWaitedPassenger.getPassengerLastName()+" added to PUMP "+(pumpNum+1)+" (from waiting queue)");                        pump.addPassenger(firstWaitedPassenger.getPassengerFirstName(), firstWaitedPassenger.getPassengerLastName(), firstWaitedPassenger.getVehicleNumber(), firstWaitedPassenger.getNoOfLiters());
                    }
                    break;
                } else if (selectedIndex == 999) {
                    break;
                } else {
                    System.out.println("Selected index doesn't exist");
                }
            } catch (Exception e) {
                System.out.println("Only integers are allowed.");
                scanner.nextLine();
            }
        }

    }

    //    Remove served customer from selected pump
    public static void removeServedCus(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter \n1 - 1st pump\n2 - 2nd pump\n3 - 3rd pump\n4 - 4th pump\n5 - 5th pump\n");
        try{
            System.out.print("What pump did serve : ");
            int pumpNum = scanner.nextInt();
            for (int i = 0; i < pumpArray.length; i++) {
                if (pumpNum == i+1) {
                    if (pumpArray[i].getNoOfEmpty() < queueLength) {
                        System.out.println("Pump " + (i + 1) + " served");
                        pumpArray[i].remove(0);
                        changePumpIncome(i,pumpArray[i].getPassenger(0).getNoOfLiters());
                        if (!waitingList.isEmpty()){
                            Passenger firstWaitedPassenger = waitingList.removeCus();
                            System.out.println(firstWaitedPassenger.getPassengerFirstName()+" "+firstWaitedPassenger.getPassengerLastName()+" added to PUMP "+(i+1)+" (from waiting queue)");
                            pumpArray[i].addPassenger(firstWaitedPassenger.getPassengerFirstName(), firstWaitedPassenger.getPassengerLastName(), firstWaitedPassenger.getVehicleNumber(), firstWaitedPassenger.getNoOfLiters());
                        }
                    }
                    else {
                        System.out.println("Pump " + (i + 1) + " is empty");
                    }
                }
            }

        }catch (Exception e){
            System.out.println("Only integers are allowed");
        }
    }

    //    Sort every queue first name to alphabetical order
    private static void sortPumpQueue() {

        for (int i = 0; i < pumpArray.length; i++) {
            ArrayList<Passenger> tempQueue = new ArrayList<>();
            for (int j = 0; j < pumpArray[i].size(); j++) {
                tempQueue.add(pumpArray[i].getPassenger(j));
            }
            tempQueue.sort(Comparator.comparing(Passenger::getPassengerFirstName));
            if (tempQueue.size() > 0) {
                System.out.println("----------------------- PUMP "+(i+1)+" [SORTED] -------------------------");
                System.out.printf("%15s | %15s | %15s| %10s\n", "First Name", "Last Name", "Vehicle No.","Litres");
                System.out.println("-----------------------------------------------------------------");

                for (Passenger passenger:tempQueue
                ) {
                    System.out.printf("%15s | %15s | %15s | %10s\n", passenger.getPassengerFirstName(), passenger.getPassengerLastName(), passenger.getVehicleNumber(), passenger.getNoOfLiters());

                }
                System.out.println();
            }

        }
    }

    //    Save necessary data to text file
    private static void saveDataToFile(){
        try{
            if (logFile.createNewFile()){
                System.out.println("Log file created");
                writeToFile();
            }else {
                System.out.println("File already exists.");
                Scanner sc = new Scanner(System.in);

                while (true){
                    System.out.println("Do you want to overwrite?(Y/N)");
                    String overwriteChoice = sc.nextLine();
                    if (overwriteChoice.equalsIgnoreCase("y")){
                        writeToFile();
                        System.out.println("Data stored successfully!!");
                        break;
                    } else if (overwriteChoice.equalsIgnoreCase("n")) {
                        break;
                    }else {
                        System.out.println("Invalid input.");
                    }
                }
            }

        }catch (IOException e){
            System.out.println("Error occurred.");
            e.printStackTrace();
        }
    }

    //    Write necessary data to fuel-queue-log.txt
    private static void  writeToFile(){
        try {
            FileWriter logWrite = new FileWriter("fuel-queue-log.txt");
            for (int i = 0; i < pumpArray.length; i++) {
                for (int j = 0; j < pumpArray[i].size(); j++) {
                    logWrite.write(pumpArray[i].getPassenger(j).toString());
                }
                if (pumpArray[i].size() < queueLength) {
                    int emptyLength = queueLength - pumpArray[i].size();
                    for (int k = 0; k < emptyLength; k++) {
                        logWrite.write("\n");
                    }
                }
            }
            logWrite.write(String.valueOf(fuelStock)+"\n");
            logWrite.write(Arrays.toString(pumpIncome).replaceAll("\\[", "").replaceAll("\\]","").replaceAll(" ","")+"\n");
            for (int j = 0; j < waitingList.getSize(); j++) {
                logWrite.write(waitingList.getPassenger(j).toString());
            }
            if (waitingList.getSize()<queueLength){
                for (int i = 0; i < queueLength- waitingList.getSize(); i++) {
                    logWrite.write("\n");
                }
            }


            logWrite.close();
        }catch (IOException e) {
            System.out.println("Error occurred.");
        }

    }

    //    Load saved data from fuel-queue-log.txt
    private static void loadDataFromFile(){
        try {
            if (logFile.exists()) {
                System.out.println("This action cannot be undone!!!");
                Scanner sc  = new Scanner(System.in);
                while (true) {
                    System.out.println("Do you want to overwrite?(Y/N)");
                    String overwriteChoice = sc.nextLine();
                    if (overwriteChoice.equalsIgnoreCase("y")) {
                        Path file = Path.of("fuel-queue-log.txt");
//                        Load and replace relevant data from file
                        int lineNumber = 0;
                        for (int i = 0; i < pumpArray.length; i++) {
                            pumpArray[i].reset();
                            for (int j = lineNumber; j < queueLength+lineNumber; j++) {
                                String[] lineData = Files.readAllLines(file).get(j).split(",");
                                if (lineData.length>1) {

                                    pumpArray[i].addPassenger(lineData[0], lineData[1], lineData[2], Integer.parseInt(lineData[3]));
                                }
                            }
                            lineNumber+=queueLength;

                        }
//                        Load data about fuel stock
                        fuelStock = Integer.parseInt(Files.readAllLines(file).get(pumpArray.length*queueLength));
//                        Load data about pump income
                        String[] pumpIncomeLoaded = Files.readAllLines(file).get(pumpArray.length*queueLength+1).split(",");
                        for (int i = 0; i < pumpIncome.length; i++) {
                            pumpIncome[i]=Integer.parseInt(pumpIncomeLoaded[i]);
                        }
                        lineNumber = pumpArray.length*queueLength+2;
//                        Load data about waiting queue
                        waitingList.reset();
                        for (int i = lineNumber; i < queueLength+lineNumber; i++) {
                            String[] lineData = Files.readAllLines(file).get(i).split(",");
                            if (lineData.length>1){
                                waitingList.enQueue(lineData[0],lineData[1],lineData[2],Integer.parseInt(lineData[3]));
                            }
                        }
                        System.out.println("Data loaded successfully.");
                        break;
                    } else if (overwriteChoice.equalsIgnoreCase("n")) {
                        break;
                    }else {
                        System.out.println("Invalid Input.");
                    }
                }

            } else {
                System.out.println("No log file to retrieve data from.");
            }
        }catch (IOException e){
            System.out.println("Error occurred.");
        }

    }

    //    Show current fuel stock
    private static void currentFuelStock(){
        System.out.println("Current Fuel stock is "+fuelStock+"L");
    }

    //    Check current fuel stock and add new fuel stock to existing
    private static void addFuelStock() {
        Scanner sc = new Scanner(System.in);
        if (fuelStock < 6600) {
            mainloop:
            while (true) {
                try {
                    while (true) {
                        System.out.print("Enter fuel amount in litres : ");
                        int addFuel = sc.nextInt();
                        if (addFuel + fuelStock > 6600) {
                            System.out.println("Fuel tank limit is 6600L.");
                            System.out.println((6600 - fuelStock) + "L space is available to fill.\n");
                        } else {
                            fuelStock += addFuel;
                            break mainloop;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Fuel amount must be integer.");
                    sc.nextLine();
                }
            }
        } else {
            System.out.println("Fuel stock is at maximum level.");
        }
    }

    //    Check whether fuel level is below warning level and show msg.
    private static void checkFuelStock(){
        if (fuelStock<=500){
            System.out.println("\nWARNING!!! FUEL STOCK IS LOW");
        }
    }

    //    show income of every queue
    private static void showQueueIncome(){
        for (int i = 0; i < pumpArray.length; i++) {
            int queueIncome = pumpIncome[i]*430;
            System.out.printf("Income of pump %d fuel queue : Rs.%d%n",i+1,queueIncome);
        }
    }

    //    Change income value when customer has been served
    private static void changePumpIncome(int index,int noOfLitres){
        for (int i = 0; i < pumpArray.length; i++) {
            if (index == i){
                pumpIncome[i]=pumpIncome[i]+noOfLitres;
            }
        }
    }
}