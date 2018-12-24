package com.excel.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.excel.demo.bean.Loan;
@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
	
	public List<Loan> findByProdId(String as_ProdId);
	
	@Query(value="{}", fields= "{'prodId' : 1,'_id' : 0}")
	public List<Loan> findAllprodId();
}
