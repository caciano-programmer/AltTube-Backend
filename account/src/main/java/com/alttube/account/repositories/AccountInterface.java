package com.alttube.account.repositories;


import com.alttube.account.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountInterface extends CrudRepository<Account, Long> {}
