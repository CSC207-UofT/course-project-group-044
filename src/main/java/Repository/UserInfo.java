package Repository;

import Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserInfo extends CrudRepository<User, String> {

}
