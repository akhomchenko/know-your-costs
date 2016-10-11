package edu.costs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.costs.domain.Spending;

public interface SpendingRepository extends JpaRepository<Spending, Long> {

}
