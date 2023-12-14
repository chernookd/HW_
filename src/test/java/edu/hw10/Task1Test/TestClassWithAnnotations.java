package edu.hw10.Task1Test;

import edu.hw10.Task1.Max;
import edu.hw10.Task1.Min;
import edu.hw10.Task1.NotNull;

public class TestClassWithAnnotations {

    private int age;
    private int weight;
    private String name;

    public TestClassWithAnnotations(@Max(10) int age,  @NotNull String name, @Min(25) int weight) {
        this.age = age;
        this.name = name;
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}
