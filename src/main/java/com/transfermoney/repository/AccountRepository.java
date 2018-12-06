package com.transfermoney.repository;

import com.transfermoney.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(long accountNumber);

}
