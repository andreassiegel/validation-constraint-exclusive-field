package de.andreassiegel.constraint;

import de.andreassiegel.constraint.validation.ReflectionValidator;
import de.andreassiegel.constraint.validation.EntityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



@Documented
@Target({ANNOTATION_TYPE, TYPE})@Retention(RUNTIME)
@Constraint(validatedBy = { EntityValidator.class, ReflectionValidator.class} )
public @interface IdOrName
{
    String message() default "only a single identifier field may be defined";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
