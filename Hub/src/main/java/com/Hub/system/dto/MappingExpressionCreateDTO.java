package com.Hub.system.dto;

import com.Hub.system.model.MappingConfigModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record MappingExpressionCreateDTO(
        //UUID id,
        String expression,
        String description
) {}
