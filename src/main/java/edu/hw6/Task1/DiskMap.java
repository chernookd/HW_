package edu.hw6.Task1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class DiskMap implements Map<String, String> {

    Map<String, String> map;
    Path path;

    public DiskMap(Path path) {
        this.path = path;
        this.map = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return map.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        return map.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return map.entrySet();
    }

    @SuppressWarnings({"ImportOrder", "PackageName"})
    public void write() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            for (Entry<String, String> keyValue : map.entrySet()) {
                String keyValueString = keyValue.getKey() + ":" + keyValue.getValue() + "\n";
                byte[] buffer = keyValueString.getBytes();
                fos.write(buffer, 0, buffer.length);
            }
        }
    }

    public void read() throws IOException {
        try (Scanner scanner = new Scanner(path.toFile())) {
            while (scanner.hasNext()) {
                String keyValueString = scanner.nextLine();
                String key = keyValueString.split(":")[0];
                String value = keyValueString.split(":")[1];
                this.map.put(key, value);
            }
        }
    }
}
