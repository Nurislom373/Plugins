package uz.devops.observability.validator;

import uz.devops.observability.annotation.ObserveTypesConstraint;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.utils.GlobalConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static uz.devops.observability.utils.BaseUtils.anyMatch;

/**
 * @author Nurislom
 * @see uz.devops.observability.validator
 * @since 12/19/2023 11:21 AM
 */
public class ObserveTypesConstraintValidator implements ConstraintValidator<ObserveTypesConstraint, Set<ObserveType>> {

    @Override
    public boolean isValid(Set<ObserveType> types, ConstraintValidatorContext context) {
        return !(Objects.isNull(types) || types.isEmpty());
    }

}
