package com.progressoft.jip11.tools.objects;

import java.util.List;

public class CategorizeRequest {

    private final List<?> list;
    private final double eliteDev;
    private final double failedDev;

    public CategorizeRequest(List<?> list, double eliteDev, double failedDev) {
        this.list = list;
        this.eliteDev = eliteDev;
        this.failedDev = failedDev;
    }

    public List<?> getList() {
        return list;
    }

    public double getEliteDev() {
        return eliteDev;
    }

    public double getFailedDev() {
        return failedDev;
    }
}
