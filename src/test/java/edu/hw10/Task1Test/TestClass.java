package edu.hw10.Task1Test;

public class TestClass {

    private int age;
    private String name;

    public static TestClass create(int age, String name) {
        return new TestClass(age, name);
    }

    private TestClass(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
