package com.Hub.system.service;

import com.Hub.account.model.AccountModel;
import com.Hub.account.repository.IAccountRepository;
import com.Hub.system.enums.CollectorType;
import com.Hub.system.enums.EntityType;
import com.Hub.system.model.CollectorSource;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.SystemModel;
import com.Hub.system.repository.SystemRepository;
import com.Hub.system.strategy.CollectorStrategy;
import com.Hub.system.strategy.CsvCollectorStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CollectorService {

    private final Map<CollectorType, CollectorStrategy> strategies;
    private final CsvExtractor csvExtractor;
    private final MappingExpressionEngine mappingEngine;
    private final IAccountRepository accountRepository;
    private final SystemRepository systemRepository;


    public CollectorService(Map<CollectorType, CollectorStrategy> strategies, CsvExtractor csvExtractor, MappingExpressionEngine mappingEngine, IAccountRepository accountRepository, SystemRepository systemRepository) {
        this.strategies = Map.of(CollectorType.CSV, new CsvCollectorStrategy(csvExtractor));
        this.csvExtractor = csvExtractor;
        this.mappingEngine = mappingEngine;
        this.accountRepository = accountRepository;
        this.systemRepository = systemRepository;
    }

    public void run(CollectorSource source, List<MappingConfigModel> mappings) throws Exception {
        CollectorStrategy strategy = strategies.get(source.getCollectorType());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported collector type: " + source.getCollectorType());
        }

        if (source.getEntityType() == EntityType.ACCOUNT) {
            try (Stream<Map<String, String>> rows = strategy.collect(source)) {
                rows.forEach(row -> {
                    String accountName = row.get("accountName");
                    AccountModel account = accountRepository.findByAccountName(accountName)
                            .orElseGet(() -> {
                                SystemModel system = systemRepository.findBySystemId(source.getSystemId())
                                        .orElseThrow(() -> new RuntimeException("Cannot find system")
                                        );
                                AccountModel newAccount = new AccountModel();
                                newAccount.setAccountName(accountName);
                                newAccount.setAccountId(row.get("accountId"));
                                newAccount.setSystem(system); // assign the system if needed
                                return accountRepository.save(newAccount);
                            });
                    mappingEngine.evaluateRow(row, mappings, account);
                });

            }
        }
    }
}
    //Create service that that starts by extracting data from csv file -> csvExtractor.
    //Then we need to send all the data through the MappingExpressionEngine
    //Then we need to match rows to an existing account and compare to-be with as-is.
    //Then we need to build our JoinRules so that we can join accounts to identities.
    //Keep in mind that this service should be usable for entitlements as well (if possible)
