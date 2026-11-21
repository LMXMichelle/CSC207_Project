package Framework_and_Driver.DataAccess;

import Entity.User;
import UseCase.SignUp.SignUpUserDataAccessInterface;
import java.util.ArrayList;

public class UserDAO implements SignUpUserDataAccessInterface {
    private final ArrayList<User> users = new ArrayList<>();
    //Overridden from signupuserdataaccesinterface
    @Override
    public boolean existsByName(String username) {
        boolean exist;
        for (User user : users) {
            exist = user.getUserName().equals(username);
            if (exist){
                return true;
            }
        }
        return false;
    }
    @Override
    public void save(User user){
        users.add(user);
    }
}
