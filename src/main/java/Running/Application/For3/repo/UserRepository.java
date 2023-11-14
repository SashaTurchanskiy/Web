package Running.Application.For3.repo;

import Running.Application.For3.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String userName);

    UserEntity findFirstByUsername(String username);
}

