import de.andreassiegel.Entity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityValidationTest
{
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void thatValidationPassesIfIdIsSet() {

        Entity update = Entity.builder()
                .id("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatValidationPassesIfNameIsSet() {

        Entity update = Entity.builder()
                .name("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    public void thatMissingIdOrNameFailValidation() {
        Entity update = Entity.builder()
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatSettingBothIdAndNameFailValidation() {
        Entity update = Entity.builder()
                .id("test")
                .name("test")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyIdFailsValidation() {

        Entity update = Entity.builder()
                .id("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyNameFailsValidation() {

        Entity update = Entity.builder()
                .name("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    public void thatEmptyIdAndNameFailValidation() {

        Entity update = Entity.builder()
                .id("")
                .name("")
                .build();

        Set<ConstraintViolation<Entity>> constraintViolations =
                validator.validate(update);

        assertFalse(constraintViolations.isEmpty());
    }
}
