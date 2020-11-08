package am.itspace.projectscope.security;

import am.itspace.projectscope.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
private User user;



    public CurrentUser(am.itspace.projectscope.model.User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUsertype().name()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

