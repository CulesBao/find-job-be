package com.findjobbe.findjobbe.service;

import org.springframework.data.domain.Page;

public interface IFollowerService {
    void savedFollow(String followerId, String followingId);
    void deleteFollow(String followerId, String followingId);
    Page<?> getFollowers(String userId, int page, int size);
}
