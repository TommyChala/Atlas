package com.Hub.system.service;

import com.Hub.account.model.AccountAttributeModel;
import com.Hub.account.model.AccountAttributeValueModel;
import com.Hub.account.model.AccountModel;
import com.Hub.account.repository.IAccountAttributeRepository;
import com.Hub.account.repository.IAccountAttributeValueRepository;
import com.Hub.system.exception.AccountAttributeNotFoundException;
import com.Hub.system.model.MappingConfigModel;
import com.Hub.system.model.MappingExpressionModel;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MappingExpressionEngine {

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final IAccountAttributeValueRepository iAccountAttributeValueRepository;
    private final IAccountAttributeRepository iAccountAttributeRepository;

    public MappingExpressionEngine(IAccountAttributeValueRepository iAccountAttributeValueRepository, IAccountAttributeRepository iAccountAttributeRepository) {
        this.iAccountAttributeValueRepository = iAccountAttributeValueRepository;
        this.iAccountAttributeRepository = iAccountAttributeRepository;
    }

    public void evaluateRow(
            Map<String, String> sourceRow,
            List<MappingConfigModel> mappings,
            AccountModel account
    ) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        sourceRow.forEach(context::setVariable);

        for (MappingConfigModel mapping : mappings) {
            Object transformedValue;

            // pick active expression (if any)
            MappingExpressionModel activeExpr = mapping.getExpressions().stream()
                    .filter(MappingExpressionModel::isActive)
                    .findFirst()
                    .orElse(null);

            try {
                if (activeExpr != null) {
                    transformedValue = expressionParser.parseExpression(activeExpr.getExpression())
                            .getValue(context);
                } else {
                    // no expression = pass-through
                    transformedValue = sourceRow.get(mapping.getSourceAttribute());
                }
            } catch (Exception e) {
                // fallback if expression fails
                transformedValue = sourceRow.get(mapping.getSourceAttribute());
                // TODO: log the error
            }

            if (transformedValue == null) continue;

            String targetAttr = mapping.getTargetAttribute().getName();
            if ("accountId".equalsIgnoreCase(targetAttr)) {
                account.setAccountId(transformedValue.toString());
                continue;
            } else if ("accountName".equalsIgnoreCase(targetAttr)) {
              //  account.setAccountName(transformedValue.toString());
                continue;
            }

            // persist (upsert-like behavior)
            AccountAttributeValueModel attributeValue =
                    iAccountAttributeValueRepository.findByAccountAndAttribute_Name(account, mapping.getTargetAttribute().getName())
                            .orElseGet(AccountAttributeValueModel::new);

            attributeValue.setAccount(account);

            AccountAttributeModel attribute = iAccountAttributeRepository.findByName(mapping.getTargetAttribute().getName())
                    .orElseThrow(() -> new AccountAttributeNotFoundException("Attribute not found"));
            attributeValue.setAttribute(attribute);

            if (transformedValue != null) {
                switch (mapping.getDataType()) {
                    case STRING:
                        attributeValue.setStringValue(transformedValue.toString());
                        break;
                    case INTEGER:
                        attributeValue.setIntValue(Integer.valueOf(transformedValue.toString()));
                        break;
                    case DATETIME:
                        attributeValue.setDatetimeValue(LocalDateTime.parse(transformedValue.toString()));
                        break;
                    case DOUBLE:
                        attributeValue.setDoubleValue(Double.valueOf(transformedValue.toString()));
                        break;
                }
            }

            iAccountAttributeValueRepository.save(attributeValue);
        }
    }
}


























