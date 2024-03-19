package ru.sstu.communities.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityMember {

    private final JdbcTemplate jdbcTemplate;

    public ru.sstu.communities.models.CommunityMember save(ru.sstu.communities.models.CommunityMember communityMember) {
        return jdbcTemplate.query("call save_community_member(?, ?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public List<ru.sstu.communities.models.CommunityMember> findAllByCommunityId(int communityId) {
        return jdbcTemplate.query("call find_community_members_by_community_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityMember.class),
                communityId);
    }

    public ru.sstu.communities.models.CommunityMember findByMemberLoginAndCommunityId(String memberLogin, int communityId) {
        return jdbcTemplate.query("call find_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityMember.class),
                        memberLogin,
                        communityId)
                .stream().findAny().orElse(null);
    }

    public ru.sstu.communities.models.CommunityMember deleteByMemberLoginAndCommunityId(ru.sstu.communities.models.CommunityMember communityMember) {
        return jdbcTemplate.query("call delete_community_member_by_member_login_and_community_id(?, ?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityMember.class),
                        communityMember.getMemberLogin(),
                        communityMember.getCommunityId())
                .stream().findAny().orElse(null);
    }

    public ru.sstu.communities.models.CommunityMember deleteById(int id) {
        return jdbcTemplate.query("call delete_community_member_by_id(?)", new BeanPropertyRowMapper<>(ru.sstu.communities.models.CommunityMember.class),
                        id)
                .stream().findAny().orElse(null);
    }

}
