package com.egs.bankservice.repository;

import com.egs.bankservice.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        boolean existsByPersonalId(String personalId);

        User getUserByPersonalId(String personalId);
}
