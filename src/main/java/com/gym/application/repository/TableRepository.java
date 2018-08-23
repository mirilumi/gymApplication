package com.gym.application.repository;

import com.gym.application.model.Table;
import com.gym.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table,Long>{

    List<Table> findAllByUser(User user);
}
