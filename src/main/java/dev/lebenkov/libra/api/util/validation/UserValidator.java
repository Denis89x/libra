package dev.lebenkov.libra.api.util.validation;

import dev.lebenkov.libra.api.service.AccountDetailsService;
import dev.lebenkov.libra.storage.dto.UserRegistrationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserValidator implements Validator {

    AccountDetailsService accountDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationRequest person = (UserRegistrationRequest) target;

        try {
            accountDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "This account already exists!");
    }
}
