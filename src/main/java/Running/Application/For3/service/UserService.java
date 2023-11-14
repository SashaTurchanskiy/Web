package Running.Application.For3.service;

import Running.Application.For3.dto.RegistrationDto;
import Running.Application.For3.models.UserEntity;

public interface UserService {
    void userSave(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
