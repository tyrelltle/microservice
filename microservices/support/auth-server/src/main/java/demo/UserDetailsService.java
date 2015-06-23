package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);


    private static class user{
        public String uname;
        public String pswd;
        public user(String u, String p){
            uname=u;
            pswd=p;
        }
    }

    private static List<user> users;
    static{
        users=new ArrayList<user>();
        users.add(new user("admin","admin"));
        users.add(new user("admin1","admin1"));
        users.add(new user("admin2","admin2"));

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
     public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        return users.stream()
                .filter(u->u.uname.equals(login))
                .findAny()
                .map(u-> {
                    List<GrantedAuthority> authlis=new ArrayList<GrantedAuthority>();
                    authlis.add(new SimpleGrantedAuthority("ADMIN"));
                     return new org.springframework.security.core.userdetails.User(
                            login,
                            passwordEncoder.encode(u.pswd),
                             authlis);
                }).orElseThrow(()->new UsernameNotFoundException("sdf"));


    }
}
