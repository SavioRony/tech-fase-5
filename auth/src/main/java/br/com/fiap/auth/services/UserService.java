package br.com.fiap.auth.services;


import br.com.fiap.auth.feignClients.UserFeignClient;
import br.com.fiap.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email){
        User body = userFeignClient.findByEmail(email).getBody();
        if(body == null){
            throw new IllegalArgumentException("User not found");
        }
        return body;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userFeignClient.findByEmail(username).getBody();
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return user;
    }
}
