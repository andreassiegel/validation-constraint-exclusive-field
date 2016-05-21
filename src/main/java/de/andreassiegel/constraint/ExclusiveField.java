package de.andreassiegel.constraint;

import de.andreassiegel.constraint.validation.FieldListValidator;

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
@Constraint(validatedBy = { FieldListValidator.class} )
public @interface ExclusiveField
{
    String message() default "only a single field may be defined";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();
}
