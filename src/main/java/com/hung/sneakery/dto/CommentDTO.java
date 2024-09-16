package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("commentText")
    private String commentText;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("parentCommentId")
    private Long parentCommentId;

    @JsonProperty("replies")
    private List<CommentDTO> replies;
}
