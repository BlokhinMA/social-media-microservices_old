package ru.sstu.communities.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityPost {

    private final JdbcTemplate jdbcTemplate;

    public ru.sstu.communities.models.CommunityPost save(ru.sstu.communities.models.CommunityPost communityPost) {
        return jdbcTemplate.query("call save_community_post(?, ?, ?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityPost.class),
                        communityPost.getPostText(),
                        communityPost.getAuthorLogin(),
                        communityPost.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<ru.sstu.communities.models.CommunityPost> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("call find_community_posts_by_community_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityPost.class),
                communityId);
    }

    public ru.sstu.communities.models.CommunityPost deleteById(int id) {
        return jdbcTemplate.query("call delete_community_post_by_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityPost.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
