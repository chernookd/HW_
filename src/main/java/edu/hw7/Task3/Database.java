package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements PersonDatabase {
    private static Map<Integer, Person> idMap = new HashMap<>();
    private static Map<String, List<Person>> nameMap = new HashMap<>();
    private static Map<String, List<Person>> addressMap = new HashMap<>();
    private static Map<String, List<Person>> phoneMap = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        if (person != null) {
            idMap.put(person.id(), person);

            if (nameMap.containsKey(person.name())) {
                List<Person> listPerson = nameMap.get(person.name());
                if (!listPerson.contains(person)) {
                    listPerson.add(person);
                }
                nameMap.put(person.name(), listPerson);
            } else {
                nameMap.put(person.name(), new ArrayList<>(List.of(person)));
            }

            if (addressMap.containsKey(person.address())) {
                List<Person> listPerson = addressMap.get(person.address());
                if (!listPerson.contains(person)) {
                    listPerson.add(person);
                }
                addressMap.put(person.address(), listPerson);
            } else {
                addressMap.put(person.address(), new ArrayList<>(List.of(person)));
            }

            if (phoneMap.containsKey(person.phoneNumber())) {
                List<Person> listPerson = phoneMap.get(person.phoneNumber());
                if (!listPerson.contains(person)) {
                    listPerson.add(person);
                }
                phoneMap.put(person.phoneNumber(), listPerson);
            } else {
                phoneMap.put(person.phoneNumber(),  new ArrayList<>(List.of(person)));
            }
        }
    }


    @Override
    public synchronized void delete(int id) {
        Person removePerson = idMap.get(id);

        idMap.remove(removePerson.id());
        phoneMap.get(removePerson.phoneNumber()).remove(removePerson);
        addressMap.get(removePerson.address()).remove(removePerson);
        nameMap.get(removePerson.name()).remove(removePerson);

    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return new ArrayList<>(nameMap.getOrDefault(name, new ArrayList<>()));
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return new ArrayList<>(addressMap.getOrDefault(address, new ArrayList<>()));
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return new ArrayList<>(phoneMap.getOrDefault(phone, new ArrayList<>()));
    }
}
