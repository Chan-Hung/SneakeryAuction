package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CommentRequest {

    @JsonProperty("productId")
    @NotNull
    private Long productId;

    @JsonProperty("parentCommentId")
    private Long parentCommentId;

    @JsonProperty("commentText")
    @NotNull
    private String commentText;
}
