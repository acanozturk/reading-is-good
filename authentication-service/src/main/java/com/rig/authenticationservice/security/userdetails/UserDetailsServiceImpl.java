package com.rig.authenticationservice.security.userdetails;

import com.rig.authenticationservice.data.constant.AccountStatus;
import com.rig.authenticationservice.data.entity.Account;
import com.rig.authenticationservice.exception.LoginException;
import com.rig.authenticationservice.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String userCode) {
        final Account account = accountRepository.findByEmailAndStatus(userCode, AccountStatus.ACTIVE)
                .orElseThrow(LoginException::new);

        return UserDetailsImpl.builder()
                .withUsername(account.getEmail())
                .withPassword(account.getPassword())
                .withAuthorities(new ArrayList<>())
                .build();
    }
    
}
