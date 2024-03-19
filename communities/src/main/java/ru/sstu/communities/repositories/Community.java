package ru.sstu.communities.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class Community {

    private final JdbcTemplate jdbcTemplate;

    public ru.sstu.communities.models.Community save(ru.sstu.communities.models.Community community) {
        return jdbcTemplate.query("call save_community(?, ?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                        community.getName(),
                        community.getCreatorLogin())
                .stream().findAny().orElse(null);
    }

    public List<ru.sstu.communities.models.Community> findAllByMemberLogin(String memberLogin) {
        return jdbcTemplate.query("call find_communities_by_member_login(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                memberLogin);
    }

    public List<ru.sstu.communities.models.Community> findAllByCreatorLogin(String creatorLogin) {
        return jdbcTemplate.query("call find_communities_by_creator_login(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                creatorLogin);
    }

    public ru.sstu.communities.models.Community findById(int id) {
        return jdbcTemplate.query("call find_community_by_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public ru.sstu.communities.models.Community deleteById(int id) {
        return jdbcTemplate.query("call delete_community_by_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                        id)
                .stream().findAny().orElse(null);
    }

    public List<ru.sstu.communities.models.Community> findAllLikeName(String word) {
        return jdbcTemplate.query("call find_communities_like_name(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.Community.class),
                word);
    }

}
