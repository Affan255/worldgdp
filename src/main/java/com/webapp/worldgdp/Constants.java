package com.webapp.worldgdp;

public enum Constants {
    PAGE_SIZE(20);

    private Constants(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    private Integer pageSize;
}

