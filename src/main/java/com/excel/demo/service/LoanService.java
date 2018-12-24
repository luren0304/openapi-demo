package com.excel.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.demo.bean.Loan;
import com.excel.demo.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;
	
	public void  createLoan(Loan Loan) {
		loanRepository.save(Loan);
		
	}
	public void  createLoan(List<Loan> LoanLst) {
		loanRepository.saveAll(LoanLst);
	}
	
	
	public List<Loan> findByProdId(String as_ProdId) {
		return loanRepository.findByProdId(as_ProdId);
	}
	public List<Loan> findAllProdId(){
		return loanRepository.findAllprodId();
	}
	public List<Loan> findAll(){
		return loanRepository.findAll();
	}
	
}
