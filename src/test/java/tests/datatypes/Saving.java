package tests.datatypes; // Figyelj a package névre!

public class Saving {
    private String name;
    private String initialDeposit;

    public Saving(String name, String initialDeposit) {
        this.name = name;
        this.initialDeposit = initialDeposit;
    }

    public String getName() {
        return name;
    }

    public String getInitialDeposit() {
        return initialDeposit;
    }

    @Override
    public String toString() {
        return "Saving{name='" + name + "', initialDeposit='" + initialDeposit + "'}";
    }
}