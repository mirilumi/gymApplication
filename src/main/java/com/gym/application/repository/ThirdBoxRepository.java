package com.gym.application.repository;

import com.gym.application.model.ThirdBox;
import com.gym.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdBoxRepository extends JpaRepository<ThirdBox,Long> {
    ThirdBox findAllByUser(User user);
}
