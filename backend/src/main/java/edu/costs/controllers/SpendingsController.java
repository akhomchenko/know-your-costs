package edu.costs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.costs.domain.Spending;
import edu.costs.repositories.SpendingRepository;

@RestController
@RequestMapping("/spendings")
public class SpendingsController extends CRUDController<Spending>{
	private SpendingRepository repo;

	@Autowired
	public SpendingsController(SpendingRepository repo) {
		this.repo = repo;
	}

	@Override
	protected JpaRepository<Spending, Long> getRepository() {
		return repo;
	}
}
