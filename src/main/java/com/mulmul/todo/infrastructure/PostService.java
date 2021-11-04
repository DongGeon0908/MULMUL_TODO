package com.mulmul.todo.infrastructure;

import com.mulmul.todo.domain.Post;
import com.mulmul.todo.dto.bundle.PostCreateBundle;
import com.mulmul.todo.dto.bundle.PostFindBundle;
import com.mulmul.todo.dto.bundle.PostUpdateBundle;
import com.mulmul.todo.dto.response.PostCreateResponse;
import com.mulmul.todo.dto.response.PostDetailResponse;
import com.mulmul.todo.error.exception.NotExitsPostException;
import com.mulmul.todo.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostCreateResponse create(PostCreateBundle bundle) {
        Post instance = new Post(bundle.getTitle(), bundle.getContent());

        Post post = postRepository.save(instance);

        return new PostCreateResponse(
                post.getId(),
                post.getTitle(),
                post.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public PostDetailResponse find(PostFindBundle bundle) {
        Post post = postRepository.findById(bundle.getId())
                .orElseThrow(NotExitsPostException::new);

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostDetailResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post ->
                        new PostDetailResponse(
                                post.getId(),
                                post.getTitle(),
                                post.getContent(),
                                post.getStatus()
                        )
                );
    }

    @Transactional
    public PostDetailResponse update(PostUpdateBundle bundle) {
        Post post = postRepository.findById(bundle.getId())
                .orElseThrow(NotExitsPostException::new);
        post.update(bundle.getTitle(), bundle.getContent());

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus()
        );
    }
}