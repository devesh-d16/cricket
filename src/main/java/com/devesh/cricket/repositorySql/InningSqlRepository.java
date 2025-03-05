package com.devesh.cricket.repositorySql;

import com.devesh.cricket.entity.Inning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InningSqlRepository extends JpaRepository<Inning, Long> {

}
