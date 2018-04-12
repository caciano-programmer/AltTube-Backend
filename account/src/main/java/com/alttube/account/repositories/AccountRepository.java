package com.alttube.account.repositories;


import com.alttube.account.models.AccountModel;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountModel, Long> {}
