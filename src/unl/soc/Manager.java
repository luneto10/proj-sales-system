package unl.soc;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * The Manager class represents a manager.
 * It contains information about the manager as a Person object.
 * It includes Getters, ToString, HashCode and Equals methods
 */
public class Manager {
    @Expose
    Person manager;

    public Manager(Person manager) {
        this.manager = manager;
    }

    public Person getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Manager: " + manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager1 = (Manager) o;
        return Objects.equals(manager, manager1.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager);
    }
}