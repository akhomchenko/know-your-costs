package edu.costs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.costs.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
