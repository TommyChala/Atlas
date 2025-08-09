package com.Hub.seeder;

import com.Hub.organization.model.FunctionTypeAttributeModel;
import com.Hub.organization.model.FunctionTypeModel;
import com.Hub.organization.repository.FunctionTypeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FunctionTypeSeeder implements ApplicationRunner {

    private final FunctionTypeRepository functionTypeRepository;

    public FunctionTypeSeeder (FunctionTypeRepository functionTypeRepository) {
        this.functionTypeRepository = functionTypeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new Constructor(FunctionTypeListWrapper.class, options);

        TypeDescription wrapperDescription = new TypeDescription(FunctionTypeListWrapper.class);
        wrapperDescription.addPropertyParameters("functionTypes", FunctionTypeModel.class);
        constructor.addTypeDescription(wrapperDescription);

        TypeDescription functionTypeDescription = new TypeDescription(FunctionTypeModel.class);
        functionTypeDescription.addPropertyParameters("attributes", FunctionTypeAttributeModel.class);
        constructor.addTypeDescription(functionTypeDescription);

        Yaml yaml = new Yaml(constructor);

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/load-initial-function-types.yaml")) {
            if (inputStream == null) throw new IllegalStateException("YAML file not found");

            FunctionTypeListWrapper wrapper = yaml.load(inputStream);
            System.out.println("Parsed YAML: " + wrapper);
            List<FunctionTypeModel> functionTypes = wrapper.getFunctionTypes();

            functionTypes.forEach(ft -> {
                if (ft.getAttributes() != null) {
                    ft.getAttributes().forEach(attr -> attr.setFunctionType(ft));
                }
                if (functionTypeRepository.findByName(ft.getName()).isEmpty()) {
                    functionTypeRepository.save(ft);
                }
            });
        }
    }

}
