package am.itspace.projectscope.repository;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByUserType(UserType userType);

    List<User> findAllByName(String name);

}
