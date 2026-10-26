
package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {

    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.isAnnotationPresent(Id.class)) {
                    if (value == null || value.toString().isBlank()) {
                        errors.add(field.getName() + ": ID cannot be empty");
                    }
                }

                if (field.isAnnotationPresent(Positive.class)
                        && value instanceof Number) {
                    Number number = (Number) value;

                    if (number.longValue() <= 0) {
                        Positive annotation =
                                field.getAnnotation(Positive.class);

                        errors.add(field.getName() + ": "
                                + annotation.message());
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)
                        && value instanceof String) {
                    String text = (String) value;

                    MaxLength annotation =
                            field.getAnnotation(MaxLength.class);

                    if (text.length() > annotation.value()) {
                        errors.add(field.getName()
                                + ": maximum length exceeded");
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Cannot validate " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}
