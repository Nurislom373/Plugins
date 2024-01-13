package uz.devops.observability.annotation;

import uz.devops.observability.validator.ObserveTypesConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Nurislom
 * @see uz.devops.observability.annotation
 * @since 12/19/2023 11:20 AM
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ObserveTypesConstraintValidator.class)
@Documented
public @interface ObserveTypesConstraint {

    String message() default "Invalid Observe Types!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
