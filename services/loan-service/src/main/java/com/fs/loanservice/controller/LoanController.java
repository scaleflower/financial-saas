package com.fs.loanservice.controller;

import com.fs.common.result.Result;
import com.fs.loanservice.entity.Loan;
import com.fs.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public Result<Loan> create(@RequestBody Loan loan) {
        Loan created = loanService.createLoan(loan);
        return Result.success(created);
    }

    @GetMapping("/{loanId}")
    public Result<Loan> getById(@PathVariable Long loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return Result.success(loan);
    }

    @GetMapping
    public Result<List<Loan>> list(@RequestParam Long tenantId) {
        List<Loan> loans = loanService.listLoans(tenantId);
        return Result.success(loans);
    }
}
