
package service;

import model.annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T> {

    private final HashMap<String, T> data = new HashMap<>();

    public void save(T item) {
        try {
            Field idField = null;

            for (Field field : item.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                    break;
                }
            }

            if (idField == null) {
                throw new IllegalArgumentException(
                        "No field marked with @Id");
            }

            idField.setAccessible(true);
            String id = String.valueOf(idField.get(item));
            data.put(id, item);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T findById(String id) {
        return data.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public void delete(String id) {
        data.remove(id);
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public <N extends Number> double average(N... numbers) {
        if (numbers.length == 0) {
            return 0.0;
        }

        double sum = 0;

        for (N number : numbers) {
            sum += number.doubleValue();
        }

        return sum / numbers.length;
    }
}
