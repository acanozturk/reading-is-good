package com.rig.authenticationservice.repository;

import com.rig.authenticationservice.data.constant.AccountStatus;
import com.rig.authenticationservice.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByEmailAndStatus(String email, AccountStatus status);
    
}
