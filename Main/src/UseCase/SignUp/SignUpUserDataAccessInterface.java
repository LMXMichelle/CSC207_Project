package UseCase.SignUp;

import Entity.User;

public interface SignUpUserDataAccessInterface {
    //Note to self: both are overridden in UserDAO
    boolean existsByName(String username);

    void save(User user);
}