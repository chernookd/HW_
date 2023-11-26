package edu.hw7.Task3_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database implements PersonDatabase {
    private static Map<Integer, Person> idMap = new HashMap<>();
    private static Map<String, List<Person>> nameMap = new HashMap<>();
    private static Map<String, List<Person>> addressMap = new HashMap<>();
    private static Map<String, List<Person>> phoneMap = new HashMap<>();

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        readWriteLock.writeLock().lock();
        try {
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
                    phoneMap.put(person.phoneNumber(), new ArrayList<>(List.of(person)));
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        readWriteLock.writeLock().lock();
        try {
            Person removePerson = idMap.get(id);

            idMap.remove(removePerson.id());
            phoneMap.get(removePerson.phoneNumber()).remove(removePerson);
            addressMap.get(removePerson.address()).remove(removePerson);
            nameMap.get(removePerson.name()).remove(removePerson);
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    @Override
    public List<Person> findByName(String name) {
        readWriteLock.readLock().lock();
        try {
            return new ArrayList<>(nameMap.getOrDefault(name, new ArrayList<>()));
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        readWriteLock.readLock().lock();
        try {
            return new ArrayList<>(addressMap.getOrDefault(address, new ArrayList<>()));
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        readWriteLock.readLock().lock();
        try {
            return new ArrayList<>(phoneMap.getOrDefault(phone, new ArrayList<>()));
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}
