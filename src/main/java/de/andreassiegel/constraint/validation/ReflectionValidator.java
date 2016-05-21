package de.andreassiegel.constraint.validation;

import de.andreassiegel.constraint.IdOrName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;


public class ReflectionValidator implements ConstraintValidator<IdOrName, Object> {


    @Override
    public void initialize(IdOrName constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        try {
            Field id = object.getClass().getDeclaredField("id");
            Field name = object.getClass().getDeclaredField("name");

            id.setAccessible(true);
            name.setAccessible(true);

            Object idValue = id.get(object);
            Object nameValue = name.get(object);

            if (isNullOrEmptyString(idValue) && isNullOrEmptyString(nameValue)) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("id and name may not be null").addConstraintViolation();
                return false;
            }

            if (idValue != null && nameValue != null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("either id or name may be defined").addConstraintViolation();
                return false;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return true;
    }

    private boolean isNullOrEmptyString(Object object) {
        return object == null || (object instanceof String && ((String) object).isEmpty());
    }
}
