package com.hung.sneakery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> extends BaseResponse {

    private T data;

    @JsonProperty("_page")
    private int page;

    @JsonProperty("_limit")
    private int limit;

    @JsonProperty("_totalRecords")
    private long totalRecords;

    @JsonProperty("_totalPage")
    private long totalPage;

    public PageResponse(T data, int page, int limit, long totalRecords, long totalPage) {
        super(true, "");
        this.data = data;
        this.page = page;
        this.limit = limit;
        this.totalRecords = totalRecords;
        this.totalPage = totalPage;
    }
}
