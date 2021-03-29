package com.progressoft.jip11.tools.objects;

import java.util.List;

public class CategorizeRequest<STUDENT_TYPE> {

    private final List<STUDENT_TYPE> list;
    private final double eliteDev;
    private final double failedDev;

    public CategorizeRequest(List<STUDENT_TYPE> list, double eliteDev, double failedDev) {
        this.list = list;
        this.eliteDev = eliteDev;
        this.failedDev = failedDev;
    }

    public List<STUDENT_TYPE> getList() {
        return list;
    }

    public double getEliteDev() {
        return eliteDev;
    }

    public double getFailedDev() {
        return failedDev;
    }
}
