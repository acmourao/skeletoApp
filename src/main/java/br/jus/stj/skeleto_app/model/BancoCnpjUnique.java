package br.jus.stj.skeleto_app.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import br.jus.stj.skeleto_app.service.BancoService;
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
 * Validate that the cnpj value isn't taken yet.
 */
@Target({FIELD, METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = BancoCnpjUnique.BancoCnpjUniqueValidator.class
)
public @interface BancoCnpjUnique {

    String message() default "{Exists.banco.cnpj}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BancoCnpjUniqueValidator implements ConstraintValidator<BancoCnpjUnique, String> {

        private final BancoService bancoService;
        private final HttpServletRequest request;

        public BancoCnpjUniqueValidator(final BancoService bancoService,
                                        final HttpServletRequest request) {
            this.bancoService = bancoService;
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
            if (currentId != null && value.equalsIgnoreCase(bancoService.get(Integer.parseInt(currentId)).getCnpj())) {
                // value hasn't changed
                return true;
            }
            return !bancoService.cnpjExists(value);
        }

    }

}
