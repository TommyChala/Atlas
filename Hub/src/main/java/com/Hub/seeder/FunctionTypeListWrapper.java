package com.Hub.seeder;

import com.Hub.organization.model.FunctionTypeModel;

import java.util.List;

public class FunctionTypeListWrapper {

    private List<FunctionTypeModel> functionTypes;

    public List<FunctionTypeModel> getFunctionTypes() {
        return functionTypes;
    }

    public void setFunctionTypes(List<FunctionTypeModel> functionTypes) {
        this.functionTypes = functionTypes;
    }
}
