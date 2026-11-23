package UseCase.SignUp;

import Entity.User;

public interface SignUpUserDataAccessInterface {

    boolean existsByName(String username);

    void save(User user);
}