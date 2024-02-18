package unl.soc;

import unl.soc.items.*;
import unl.soc.person.Manager;
import unl.soc.person.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Utils {

    /**
     * Reads data from a CSV file containing information about items and converts it into a Map with item codes as keys
     * and corresponding Item objects as values.
     *
     * @param path The path to the CSV file.
     * @return A Map<String, Item> where keys are item codes and values are Item objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static Map<String, Item> readItemsCSVtoMap(String path) {
        try {
            Scanner s = new Scanner(new File(path));

            Map<String, Item> codeItemMap = new HashMap<>();

            s.nextLine();
            while (s.hasNext()) {
                String line = s.nextLine();
                List<String> itemsInfo = Arrays.asList(line.split(","));
                Item item = new Item(itemsInfo.get(0), itemsInfo.get(1), itemsInfo.get(2), Double.parseDouble(itemsInfo.get(3)));
                codeItemMap.put(itemsInfo.get(0), item);
            }
            s.close();
            return codeItemMap;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads data from a CSV file containing information about items and converts it into a List of Item objects.
     *
     * @param path The path to the CSV file.
     * @return A List of Item objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static List<Item> readItemsCSVtoList(String path){
        return new ArrayList<>(readItemsCSVtoMap(path).values());
    }

    /**
     * Converts a Map of items, where keys are item codes and values are Item objects,
     * into a List containing all the Item objects from the map.
     *
     * @param itemMap A Map<String, Item> where keys are item codes and values are Item objects.
     * @return A List of Item objects created from the values in the provided itemMap.
     */
    public static List<Item> readItemsCSVtoList(Map<String, Item> itemMap){
        return new ArrayList<>(itemMap.values());
    }

    /**
     * Parses a list of Item objects into separate lists based on their types and constructs a map
     * containing these lists categorized as product purchases, product leases, voice plans, data plans, and services.
     *
     * @param itemsList The list of Item objects to be parsed.
     * @return A map containing categorized lists of ProductPurchase, ProductLease, VoicePlan, DataPlan, and Service objects.
     */
    public static Map<String, Object> itemsDictParse(List<Item> itemsList) {
        List<ProductPurchase> productPurchaseList = new ArrayList<>();
        List<VoicePlan> voicePlanList = new ArrayList<>();
        List<DataPlan> dataPlanList = new ArrayList<>();
        List<ProductLease> productLeaseList = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();

        Map<String, Object> result = new HashMap<>(Map.of(
                "purchase", productPurchaseList,
                "lease", productLeaseList,
                "voicePlan", voicePlanList,
                "dataPlan", dataPlanList,
                "service", serviceList));

        for (Item item : itemsList) {
            if (item.getType().compareTo("P") == 0) {
                ProductPurchase purchase = new ProductPurchase(item.getUniqueCode(), item.getType(), item.getName(), item.getBasePrice());
                productPurchaseList.add(purchase);
                result.put("purchase", productPurchaseList);
            } else if (item.getType().compareTo("S") == 0) {
                Service service = new Service(item.getUniqueCode(), item.getType(), item.getName(), item.getBasePrice());
                serviceList.add(service);
                result.put("service", serviceList);
            } else if (item.getType().compareTo("V") == 0) {
                VoicePlan voicePlan = new VoicePlan(item.getUniqueCode(), item.getType(), item.getName(), item.getBasePrice());
                voicePlanList.add(voicePlan);
                result.put("voicePlan", voicePlanList);
            } else if (item.getType().compareTo("D") == 0) {
                DataPlan dataPlan = new DataPlan(item.getUniqueCode(), item.getType(), item.getName(), item.getBasePrice());
                dataPlanList.add(dataPlan);
                result.put("dataPlan", dataPlanList);
            }
        }
        return result;
    }

    /**
     * Reads data from a CSV file containing information about persons and converts it into a Map with UUIDs as keys
     * and corresponding Person objects as values.
     *
     * @param path The path to the CSV file.
     * @return A Map<String, Person> where keys are UUIDs and values are Person objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static Map<String,Person> readPersonCSVtoMap(String path) {
        try {
            Scanner s = new Scanner(new File(path));
            Map<String,Person> uuidPersonMap = new HashMap<>();

            s.nextLine();
            while (s.hasNext()) {
                String line = s.nextLine();
                List<String> personData = Arrays.asList(line.split(","));
                List<String> emailList = new ArrayList<>();
                for (int i = 7; i < personData.size(); i++) {
                    emailList.add(personData.get(i));
                }

                Person person = new Person(personData.get(0),
                        personData.get(1),
                        personData.get(2),
                        new Address(personData.get(3), personData.get(4), personData.get(5), Integer.parseInt(personData.get(6))),
                        emailList);

                uuidPersonMap.put(person.getUuid(), person);
            }
            s.close();
            return uuidPersonMap;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads data from a CSV file containing information about persons and converts it into a List of Person objects.
     *
     * @param path The path to the CSV file.
     * @return A List of Person objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static List<Person> readPersonCSVtoList(String path) {
        return new ArrayList<>(readPersonCSVtoMap(path).values());
    }

    /**
     * Converts a Map of persons, where keys are UUIDs and values are Person objects,
     * into a List containing all the Person objects from the map.
     *
     * @param personMap A Map<String, Person> where keys are UUIDs and values are Person objects.
     * @return A List of Person objects created from the values in the provided personMap.
     */
    public static List<Person> readPersonCSVtoList(Map<String, Person> personMap) {
        return new ArrayList<>(personMap.values());
    }

    /**
     * Reads data from a CSV file containing information about stores and converts it into a Map with store codes as keys
     * and corresponding Store objects as values. Additionally, it associates each store with a manager from the provided
     * Persons CSV file.
     *
     * @param path The path to the CSV file containing store information.
     * @return A Map<String, Store> where keys are store codes and values are Store objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static Map<String, Store> readStoreCSVtoMap(String path) {
        try {
            Scanner s = new Scanner(new File(path));
            Map<String, Store> codeStoreMap = new HashMap<>();
            Map<String, Person> personMap = readPersonCSVtoMap("data/Persons.csv");

            s.nextLine();
            while (s.hasNext()) {
                String line = s.nextLine();
                List<String> storeData = Arrays.asList(line.split(","));
                Person personManager = personMap.get(storeData.get(1));

                Store store = new Store(storeData.get(0),
                        new Address(storeData.get(2), storeData.get(3), storeData.get(4), Integer.parseInt(storeData.get(5))),
                        new Manager(personManager));

                codeStoreMap.put(storeData.get(0), store);
            }
            s.close();
            return codeStoreMap;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads data from a CSV file containing information about stores and converts it into a List of Store objects.
     *
     * @param path The path to the CSV file.
     * @return A List of Store objects created from the data in the CSV file.
     * @throws RuntimeException if there is an issue reading the file or parsing the data.
     */
    public static List<Store> readStoreCSVtoList(String path){
        return new ArrayList<>(readStoreCSVtoMap(path).values());
    }

    /**
     * Converts a Map of stores, where keys are store codes and values are Store objects,
     * into a List containing all the Store objects from the map.
     *
     * @param storeMap A Map<String, Store> where keys are store codes and values are Store objects.
     * @return A List of Store objects created from the values in the provided storeMap.
     */
    public static List<Store> readStoreCSVtoList(Map<String, Store> storeMap){
        return new ArrayList<>(storeMap.values());
    }


}