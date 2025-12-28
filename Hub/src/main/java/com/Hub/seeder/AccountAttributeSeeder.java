package com.Hub.seeder;

import com.Hub.account.enums.DataType;
import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountAttributeSeeder implements ApplicationRunner {

    private final IAccountAttributeRepository accountAttributeRepository;

    public AccountAttributeSeeder (IAccountAttributeRepository accountAttributeRepository) {
        this.accountAttributeRepository = accountAttributeRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        addMandatoryAttributes();
    }
    public void addMandatoryAttributes () {
        AccountAttributeModel businessKey = new AccountAttributeModel("businessKey", "BusinessKey", DataType.STRING, true);
        AccountAttributeModel accountName = new AccountAttributeModel("AccountName", "AccountName", DataType.STRING, true);
        AccountAttributeModel status = new AccountAttributeModel("status", "status", DataType.STRING, true);

        accountAttributeRepository.save(businessKey);
        accountAttributeRepository.save(accountName);
        accountAttributeRepository.save(status);
    }

}
