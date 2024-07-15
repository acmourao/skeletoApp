package br.jus.stj.skeletoApp.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import br.jus.stj.skeletoApp.service.AeroportoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the sigla value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = AeroportoSiglaUnique.AeroportoSiglaUniqueValidator.class
)
public @interface AeroportoSiglaUnique {

    String message() default "{Exists.aeroporto.sigla}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class AeroportoSiglaUniqueValidator implements ConstraintValidator<AeroportoSiglaUnique, String> {

        private final AeroportoService aeroportoService;
        private final HttpServletRequest request;

        public AeroportoSiglaUniqueValidator(final AeroportoService aeroportoService,
                                             final HttpServletRequest request) {
            this.aeroportoService = aeroportoService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(aeroportoService.get(Integer.parseInt(currentId)).getSigla())) {
                // value hasn't changed
                return true;
            }
            return !aeroportoService.siglaExists(value);
        }

    }

}
