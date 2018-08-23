package com.gym.application.repository;

import com.gym.application.model.SecondBox;
import com.gym.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondBoxRepository extends JpaRepository<SecondBox,Long>{

    SecondBox findAllByUser(User user);
}
