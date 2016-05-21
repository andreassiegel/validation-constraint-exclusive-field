package de.andreassiegel.constraint.validation;

import de.andreassiegel.Entity;
import de.andreassiegel.constraint.IdOrName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EntityValidator implements ConstraintValidator<IdOrName, Entity> {


    @Override
    public void initialize(IdOrName constraintAnnotation) {

    }

    @Override
    public boolean isValid(Entity entity, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (isNullOrEmpty(entity.getId()) && isNullOrEmpty(entity.getName())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("id and name may not be null").addConstraintViolation();
            return false;
        }

        if (entity.getId() != null && entity.getName() != null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("either id or name may be defined").addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
