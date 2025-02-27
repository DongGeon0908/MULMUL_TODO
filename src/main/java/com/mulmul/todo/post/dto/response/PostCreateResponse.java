package com.mulmul.todo.post.dto.response;

import com.mulmul.todo.post.domain.vo.Status;

public class PostCreateResponse {
    private final Long id;
    private final String title;
    private final String status;

    public PostCreateResponse(Long id, String title, Status status) {
        this.id = id;
        this.title = title;
        this.status = status.name();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
