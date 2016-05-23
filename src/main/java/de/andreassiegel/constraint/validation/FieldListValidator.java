package de.andreassiegel.constraint.validation;

import de.andreassiegel.constraint.ExclusiveField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FieldListValidator implements ConstraintValidator<ExclusiveField, Object> {

    private String[] fields;

    @Override
    public void initialize(ExclusiveField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (fields.length > 1) {

            List<String> setFields = definedFields(object);

            if (setFields.size() == 1) {
                return true;
            } else {
                String message = constraintViolationMessage(setFields);

                for (String fieldName : setFields) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                            .addPropertyNode(fieldName)
                            .addConstraintViolation();
                }

                return false;
            }
        }

        return true;
    }

    private String constraintViolationMessage(List<String> setFields) {
        String message;

        if (setFields.isEmpty()) {
            message = "one of " + Arrays.toString(fields) + " may not be undefined";
        } else {
            message = "only a single field of " + Arrays.toString(fields) + " may be defined";
        }

        return message;
    }

    private List<String> definedFields(Object object) {
        List<String> setFields = new ArrayList<String>();

        for (String fieldName : fields) {
            try {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(object);

                if (!isNullOrEmpty(value)) {
                    setFields.add(fieldName);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("The field '" + fieldName + "' declared for validation does not exist in class '" + object.getClass().getName() + "'");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return setFields;
    }

    private boolean isNullOrEmpty(Object value) {
        return value == null
                || (value instanceof String && ((String) value).isEmpty())
                || value.toString().isEmpty();
    }
}
