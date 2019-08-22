package com.artifact.service;

import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserDetailsDao userDao;
    private MailSender mailSender;


    public CustomUserDetailsService(UserDetailsDao userDao, MailSender mailSender) {
        this.mailSender = mailSender;
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    public void sendMessage(User user) {
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Please, verify your email: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "ActivationCode", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userDao.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userDao.save(user);

        return true;
    }


}
