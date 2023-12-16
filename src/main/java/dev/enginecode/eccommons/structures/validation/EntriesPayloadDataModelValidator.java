package dev.enginecode.eccommons.structures.validation;

import dev.enginecode.eccommons.structures.model.DataModel;
import dev.enginecode.eccommons.structures.validation.strategies.EntriesPayloadValidationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Abstract validator for {@link EntriesPayload} objects, providing a flexible mechanism
 * for validating {@link EntriesPayload} instances based on various validation strategies.
 * Implementation of this abstract class should be registered as a bean in the
 * Spring application context.
 *
 * <p>To use this abstract validator, extend it in a class that is also a bean in the
 * application. In the constructor, provide the {@code ApplicationContext}, and implement
 * the {@code getDataModel()} method to supply the {@link DataModel} instance to be
 * used for validation.
 *
 * @see EntriesPayload
 * @see EntriesPayloadValidationStrategy
 * @see DataModel
 * @see Validator
 */
public abstract class EntriesPayloadDataModelValidator implements Validator {

    private final List<EntriesPayloadValidationStrategy> strategyList;

    /**
     * Constructs a new {@link EntriesPayloadDataModelValidator} with the specified
     * {@link ApplicationContext} to retrieve {@link EntriesPayloadValidationStrategy} beans.
     *
     * @param applicationContext the application context to obtain strategy beans
     */
    public EntriesPayloadDataModelValidator(ApplicationContext applicationContext) {
        strategyList = applicationContext.getBeansOfType(EntriesPayloadValidationStrategy.class).values().stream().toList();
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return EntriesPayload.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        DataModel dataModel = getDataModel();
        EntriesPayload payload = (EntriesPayload) target;

        strategyList.stream()
                .map(strategy -> strategy.validate(payload, dataModel))
                .flatMap(List::stream)
                .forEach(validationError -> errors.rejectValue(
                        validationError.field(), validationError.errorCode(), validationError.defaultMessage()
                ));
    }

    /**
     * Retrieves the {@link DataModel} instance to be used for validation. This method
     * should be implemented by subclasses to provide the appropriate {@link DataModel} object.
     *
     * @return the {@link DataModel} instance for validation
     */
    public abstract DataModel getDataModel();

}
