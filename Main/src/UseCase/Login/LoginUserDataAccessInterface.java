package UseCase.Login;

import Entity.User;

public interface LoginUserDataAccessInterface {

    boolean existsByName(String username);

    void save(User user);

    User get(String username);

    void setCurrentUsername(String name);

    String getCurrentUsername();
}
