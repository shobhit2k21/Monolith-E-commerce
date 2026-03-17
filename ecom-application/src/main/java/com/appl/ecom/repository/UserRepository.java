package com.appl.ecom.repository;

import com.appl.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Crud repository bhi use kr skte h

@Repository // Making it repository
public interface UserRepository extends JpaRepository<User, Long> {
}
