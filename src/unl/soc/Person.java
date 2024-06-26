package unl.soc;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Person class represents a person.
 * It contains information about the person including UUID, first name,
 * last name, address, and email list.
 * It includes Getters, ToString, HashCode and Equals methods
 */
@XStreamAlias("person")
public class Person {
    @XStreamAlias("emails")
    private final List<String> emailList;
    private int id;
    @Expose
    private String uuid;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Address address;

    public Person(int id, String uuid, String firstName, String lastName, Address address, List<String> emailList) {
        this.id = id;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailList = emailList;
    }

    public Person(int id, String uuid, String firstName, String lastName, Address address) {
        this.id = id;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailList = new ArrayList<>();
    }

    public Person(String uuid, String firstName, String lastName, Address address, List<String> emailList) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailList = emailList;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getEmailList() {
        return new ArrayList<>(emailList);
    }

    public void addEmail(String emailAddress) {
        this.emailList.add(emailAddress);
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName() + " (" + getUuid() + ")\n" +
                "\t  " + getEmailList() + "\n" +
                "\t  " + getAddress() + "\n" +
                "\t  " + getAddress().getCity() + " " + getAddress().getState() + " " + getAddress().getZipCode() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(uuid, person.uuid) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address) && Objects.equals(emailList, person.emailList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, firstName, lastName, address, emailList);
    }
}