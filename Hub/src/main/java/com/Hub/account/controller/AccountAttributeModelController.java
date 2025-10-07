package com.Hub.account.controller;

import com.Hub.account.dto.AccountAttributeModelCreateDTO;
import com.Hub.account.dto.AccountAttributeModelResponseDTO;
import com.Hub.account.service.AccountAttributeModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountAttributeModel")
public class AccountAttributeModelController {

    private final AccountAttributeModelService accountAttributeModelService;

    public AccountAttributeModelController (AccountAttributeModelService accountAttributeModelService) {
        this.accountAttributeModelService = accountAttributeModelService;
    }

    @PostMapping()
    public AccountAttributeModelResponseDTO addAccountAttribute (@RequestBody AccountAttributeModelCreateDTO accountAttributeModelCreateDTO) {
        return accountAttributeModelService.addNew(accountAttributeModelCreateDTO);
    }
}
