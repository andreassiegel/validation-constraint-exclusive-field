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

        if (fields.length > 0) {

            List<String> setFields = definedFields(object);

            if (setFields.size() == 1) {
                return true;
            } else {
                if (setFields.isEmpty()) {
                    undefinedFieldsConstraintViolation(constraintValidatorContext);
                } else {
                    overspecifiedFieldsConstraintViolations(constraintValidatorContext, setFields);
                }

                return false;
            }
        }

        return true;
    }

    private void overspecifiedFieldsConstraintViolations(ConstraintValidatorContext constraintValidatorContext, List<String> setFields) {
        String message = "only a single field of " + Arrays.toString(fields) + " may be defined";

        for (String fieldName : setFields) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
    }

    private void undefinedFieldsConstraintViolation(ConstraintValidatorContext constraintValidatorContext) {
        String message;

        if (fields.length > 1) {
            message = "one of " + Arrays.toString(fields) + " may not be empty";
        } else {
            message = "may not be empty";
        }

        for (String fieldName : fields) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldName)
                    .addConstraintViolation();
        }
    }

    private List<String> definedFields(Object object) {
        List<String> setFields = new ArrayList<String>();

        for (String fieldName : fields) {
            try {
                if (!isNullOrEmpty(getValue(object, fieldName))) {
                    setFields.add(fieldName);
                }
            } catch (NoSuchFieldException e) {
                System.out.println("The field '" + fieldName + "' declared for validation does not exist in class '" + object.getClass().getName() + "'");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("The field '" + fieldName + "' in class '" + object.getClass().getName() + "' is not accessible");
                e.printStackTrace();
            }
        }

        return setFields;
    }

    private Object getValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private boolean isNullOrEmpty(Object value) {
        return value == null
                || (value instanceof String && ((String) value).isEmpty())
                || value.toString().isEmpty();
    }
}
