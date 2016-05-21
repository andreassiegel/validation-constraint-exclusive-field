package de.andreassiegel.constraint.validation;

import de.andreassiegel.constraint.ExclusiveField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;


public class FieldListValidator implements ConstraintValidator<ExclusiveField, Object> {

    private String[] fields;

    @Override
    public void initialize(ExclusiveField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        if (fields.length > 1) {

            Boolean fieldSet = false;

            for (String fieldName : fields) {
                try {
                    Field field = object.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object value = field.get(object);

                    if (!isNullOrEmpty(value)) {
                        if (fieldSet) {
                            return false;
                        } else {
                            fieldSet = true;
                        }
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println("The field '" + fieldName + "' declared for validation does not exist in class '" + object.getClass().getName() + "'");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            return fieldSet;
        }

        return true;
    }

    private boolean isNullOrEmpty(Object value) {
        return value == null
                || (value instanceof String && ((String) value).isEmpty())
                || value.toString().isEmpty();
    }
}
