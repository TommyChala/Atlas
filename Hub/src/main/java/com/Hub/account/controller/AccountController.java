package com.Hub.account.controller;

import com.Hub.account.dto.AccountModelCreateDTO;
import com.Hub.account.model.AccountModel;
import com.Hub.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountModel>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @PostMapping
    public ResponseEntity<AccountModel> createNew(@RequestBody AccountModelCreateDTO accountModelCreateDTO) {
        return ResponseEntity.ok(accountService.createNew(accountModelCreateDTO));
    }
}
