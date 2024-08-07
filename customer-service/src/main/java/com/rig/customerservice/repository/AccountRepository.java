package com.rig.customerservice.repository;

import com.rig.customerservice.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
    
}
