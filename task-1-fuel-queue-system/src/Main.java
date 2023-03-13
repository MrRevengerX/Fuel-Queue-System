import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    static int queueLength = 6;

    static int fuelStock = 6600;

    static File logFile = new File("fuel-queue-log.txt");

    public static void main(String[] args) {

//   --------Declaring variables--------

        String[] pump1 = new String[queueLength];
        String[] pump2 = new String[queueLength];
        String[] pump3 = new String[queueLength];
        Scanner sc = new Scanner(System.in);


//        ------ Creating menu --------
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
            System.out.println("999 or EXT: Exit the Program.");
            System.out.println("-------------------------------------");

            System.out.print("Enter your choice : ");
            String choice = sc.next();

            if (choice.equals("100") || choice.equalsIgnoreCase("VFQ")){
                System.out.println("100/VFQ option Selected");
                vfq(pump1,pump2,pump3);
            } else if (choice.equals("101") || choice.equalsIgnoreCase("VEQ")) {
                System.out.println("101/VEQ option Selected");
                veq(pump1,pump2,pump3);
            }else if (choice.equals("102") || choice.equalsIgnoreCase("VCQ")) {
                System.out.println("102/VCQ option Selected");
                addCustomer(pump1,pump2,pump3);
            } else if (choice.equals("103") || choice.equalsIgnoreCase("RCQ")) {
                System.out.println("103/RCQ option Selected");
                removeSpecificCus(pump1,pump2,pump3);
            }else if (choice.equals("104") || choice.equalsIgnoreCase("PCQ")) {
                System.out.println("104/PCQ option Selected");
                removeServedCus(pump1,pump2,pump3);
            }else if (choice.equals("105") || choice.equalsIgnoreCase("VCS")) {
                System.out.println("105/VCS option Selected");
                sortPumpQueues(pump1,pump2,pump3);
            }else if (choice.equals("106") || choice.equalsIgnoreCase("SPD")) {
                System.out.println("106/SPD option Selected");
                saveDataToFile(pump1,pump2,pump3);
            }
            else if (choice.equals("107") || choice.equalsIgnoreCase("LPD")) {
                System.out.println("107/LPD option Selected");
                loadDataFromFile(pump1,pump2,pump3);
            }
            else if (choice.equals("108") || choice.equalsIgnoreCase("STK")) {
                System.out.println("108/STK option Selected");
                currentFuelStock();
            }
            else if (choice.equals("109") || choice.equalsIgnoreCase("AFS")) {
                System.out.println("109/AFS option Selected");
                addFuelStock();
            }
            else if (choice.equals("999") || choice.equalsIgnoreCase("EXT")) {
                System.out.println("Program Terminated");
                break;
            }else{
                System.out.println("Invalid Input");
            }

            checkFuelStock();
        }
    }


    private static void vfq(String[] pump1, String[] pump2, String[] pump3){
        showPumpDetails("Fuel pump 1 : ",pump1);
        System.out.println(" ");

        showPumpDetails("Fuel pump 2 : ",pump2);
        System.out.println(" ");

        showPumpDetails("Fuel pump 3 : ",pump3);
        System.out.println(" ");

    }

    private static void veq(String[] pump1, String[] pump2, String[] pump3){
        if (numOfEmptyQueues(pump1)>0) {
            showPumpDetails("Fuel pump 1 : ", pump1);
            System.out.println(numOfEmptyQueues(pump1) + " empty queues");
            System.out.println(" ");
        }
        if (numOfEmptyQueues(pump2)>0) {
            showPumpDetails("Fuel pump 2 : ", pump2);
            System.out.println(numOfEmptyQueues(pump2) + " empty queues");
            System.out.println(" ");
        }
        if (numOfEmptyQueues(pump3)>0) {
            showPumpDetails("Fuel pump 3 : ", pump3);
            System.out.println(numOfEmptyQueues(pump3) + " empty queues");
            System.out.println(" ");
        }
    }


    private static int numOfEmptyQueues(String[] pump){
        int numOfEmptyQueues = 0;
        for (String queueItem:pump) {
            if (queueItem == null) {
                numOfEmptyQueues++;
            }
        }
        return numOfEmptyQueues;
    }

    private static void addCustomer(String[] pump1, String[] pump2, String[] pump3){
        if (numOfEmptyQueues(pump3)>numOfEmptyQueues((pump2)) && numOfEmptyQueues(pump3)>numOfEmptyQueues((pump1))){
            if (addNameToQueue(pump3)){
                System.out.println("Customer added to PUMP 3 queue.");
            }
        } else if (numOfEmptyQueues(pump2)>numOfEmptyQueues((pump1))) {
            if (addNameToQueue(pump2)){
                System.out.println("Customer added to PUMP 2 queue.");
            }
        }else {
            if (addNameToQueue(pump1)){
                System.out.println("Customer added to PUMP 1 queue.");
            }
        }


    }

    private static boolean addNameToQueue(String[] pump){
        Scanner sc = new Scanner(System.in);
        if (numOfEmptyQueues(pump)==0) {
            System.out.println("All fuel queues are full");
            return false;
        }else{
            System.out.print("Enter customer name : ");
            String customerName = sc.nextLine();
            pump[queueLength-numOfEmptyQueues(pump)] = customerName;
            showPumpDetails("Fuel pump : ",pump);
            fuelStock -= 10;
            return true;
        }
    }
    private static void showPumpDetails(String label, String[] pump){
        System.out.print(label);
        System.out.println(String.join(",",pump));
    }

    private static void removeSpecificCus(String[] pump1, String[] pump2, String[] pump3){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter \n1 - 1st pump\n2 - 2nd pump\n3 - 3rd pump\n");
        try{
            System.out.print("What pump customer do you want to remove : ");
            int pumpNum = sc.nextInt();

            if (pumpNum==1){
                if (numOfEmptyQueues(pump1)<queueLength) {
                    System.out.println("Pump 1 selected");
                    showCustomerIndex(pump1);
                }else {
                    System.out.println("Pump 1 is empty");
                }
            } else if (pumpNum==2) {
                if (numOfEmptyQueues(pump2)<queueLength) {
                    System.out.println("Pump 2 selected");
                    showCustomerIndex(pump2);
                }else {
                    System.out.println("Pump 2 is empty");
                }
            } else if (pumpNum==3) {
                if (numOfEmptyQueues(pump3)<queueLength) {
                    System.out.println("Pump 3 selected");
                    showCustomerIndex(pump3);
                }else {
                    System.out.println("Pump 3 is empty");
                }
            }else {
                System.out.println("Pump number is incorrect.");
            }
        }catch (Exception e){
            System.out.println("Only integers are allowed");
        }




    }

    private static void showCustomerIndex(String[] pump){
        Scanner sc = new Scanner(System.in);
        int index =0;
        if (numOfEmptyQueues(pump)==6){
            System.out.println("Selected pump queue is empty");
        }else {
            System.out.println("Index | Customer Name\n-------------------------------");
            for (String cusName :pump) {
                if (cusName!=null){
                    System.out.println((index+1)+"     | "+cusName);
                    index++;
                }
            }
            System.out.println("\n999   | Exit");
            while (true) {
                try {
                    System.out.print("\nSelect customer by Index : ");
                    int selectedIndex = sc.nextInt();

                    if (selectedIndex <= index) {
                        removeCustomer(pump, selectedIndex - 1);
                        break;
                    } else if (selectedIndex == 999) {
                        break;
                    } else {
                        System.out.println("Selected index doesn't exist");
                    }
                } catch (Exception e) {
                    System.out.println("Only integers are allowed.");
                    sc.nextLine();
                }
            }
        }
    }

    private static void removeCustomer(String[] pump, int index){
        int numOfNull = numOfEmptyQueues(pump)+1;
        for (int i = index; i < (queueLength - numOfEmptyQueues(pump)); i++) {
            if (i+1 < queueLength){
                pump[i] = pump[i + 1];
            }


            if (i+numOfNull==queueLength){
                pump[i]=null;
            }

        }
    }

    private static void removeServedCus(String[] pump1, String[] pump2, String[] pump3){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter \n1 - 1st pump\n2 - 2nd pump\n3 - 3rd pump\n");
        try {
            System.out.print("What pump did serve : ");
            int pumpNum = sc.nextInt();

            if (pumpNum==1){
                if (numOfEmptyQueues(pump1)==6){
                    System.out.println("Pump 1 queue is empty");
                }else {
                    System.out.println("Fuel served for "+pump1[0]);
                    removeCustomer(pump1,0);
                }
            } else if (pumpNum==2) {
                if (numOfEmptyQueues(pump2)==6){
                    System.out.println("Pump 2 queue is empty");
                }else {
                    System.out.println("Fuel served for "+pump2[0]);
                    removeCustomer(pump2,0);
                }
            } else if (pumpNum==3) {
                if (numOfEmptyQueues(pump3)==6){
                    System.out.println("Pump 3 queue is empty");
                }else {
                    System.out.println("Fuel served for "+pump3[0]);
                    removeCustomer(pump3,0);
                }
            }else {
                System.out.println("Pump number is incorrect.");
            }
        }catch (Exception e){
            System.out.println("Only integers are allowed.");
        }


    }

    private static void sortPumpQueues(String[] pump1, String[] pump2, String[] pump3){
        vfq(sortPumpQueue(pump1),sortPumpQueue(pump2),sortPumpQueue(pump3));

    }

    private static String[] sortPumpQueue(String[] pump) {
        int loopLength = queueLength-numOfEmptyQueues(pump);
        String[] sortedPump = Arrays.copyOf(pump,queueLength);
        if (loopLength>1) {
            for (int i = 0; i < loopLength - 1; i++) {
                for (int j = i + 1; j < loopLength; j++) {
                    if (sortedPump[i].compareTo(sortedPump[j]) > 0) {
                        String temp = sortedPump[i];
                        sortedPump[i] = sortedPump[j];
                        sortedPump[j] = temp;
                    }
                }
            }
        }
        return sortedPump;
    }

    private static void checkFuelStock(){
        if (fuelStock<=500){
            System.out.println("\nWARNING!!! FUEL STOCK IS LOW");
        }
    }

    private static void currentFuelStock(){
        System.out.println("Current Fuel stock is "+fuelStock+"L");
    }

    private static void addFuelStock() {
        Scanner sc = new Scanner(System.in);
        if (fuelStock<6600) {
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
        }
        else {
            System.out.println("Fuel stock is at maximum level.");
        }

    }

    private static void saveDataToFile(String[] pump1,String[] pump2,String[] pump3){
        try{
            if (logFile.createNewFile()){
                System.out.println("Log file created");
                writeToFile(pump1, pump2, pump3);
            }else {
                System.out.println("File already exists.");
                Scanner sc = new Scanner(System.in);

                while (true){
                    System.out.println("Do you want to overwrite?(Y/N)");
                    String overwriteChoice = sc.nextLine();
                    if (overwriteChoice.equalsIgnoreCase("y")){
                        writeToFile(pump1, pump2, pump3);
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

    private static void writeToFile(String[] pump1,String[] pump2,String[] pump3){
        try {
            FileWriter logWrite = new FileWriter("fuel-queue-log.txt");
            logWrite.write(String.join(",",pump1)+'\n');
            logWrite.write(String.join(",",pump2)+'\n');
            logWrite.write(String.join(",",pump3)+'\n');
            logWrite.write(String.valueOf(fuelStock));
            logWrite.close();
        }catch (IOException e) {
            System.out.println("Error occurred.");
        }

    }

    private static void loadDataFromFile(String[] pump1,String[] pump2,String[] pump3){
        try {
            if (logFile.exists()) {
                System.out.println("This action cannot be undone!!!");
                Scanner sc  = new Scanner(System.in);
                while (true) {
                    System.out.println("Do you want to overwrite?(Y/N)");
                    String overwriteChoice = sc.nextLine();
                    if (overwriteChoice.equalsIgnoreCase("y")) {
                        Path file = Path.of("fuel-queue-log.txt");
                        String[] pump1Loaded = Files.readAllLines(file).get(0).split(",");
                        String[] pump2Loaded = Files.readAllLines(file).get(1).split(",");
                        String[] pump3Loaded = Files.readAllLines(file).get(2).split(",");
                        fuelStock = Integer.parseInt(Files.readAllLines(file).get(3));
                        System.arraycopy(pump1Loaded, 0, pump1, 0, pump1.length);
                        System.arraycopy(pump2Loaded, 0, pump2, 0, pump2.length);
                        System.arraycopy(pump3Loaded, 0, pump3, 0, pump3.length);
                        System.out.println("Data successfully loaded.");
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
}