package ru.sstu.communities.services;

import ru.sstu.communities.repositories.CommunityPost;
import ru.sstu.communities.repositories.Community;
import ru.sstu.communities.repositories.CommunityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final Community community;
    private final CommunityMember communityMember;
    private final CommunityPost communityPost;

    public List<ru.sstu.communities.models.Community> showAllOwn(Principal principal) {
        return community.findAllByCreatorLogin(principal.getName());
    }

    public List<ru.sstu.communities.models.Community> showAll(Principal principal) {
        return community.findAllByMemberLogin(principal.getName());
    }

    public List<ru.sstu.communities.models.Community> showAll(String memberLogin) {
        return community.findAllByMemberLogin(memberLogin);
    }

    public void create(ru.sstu.communities.models.Community community, Principal principal) {
        community.setCreatorLogin(principal.getName());
        ru.sstu.communities.models.Community createdCommunity = this.community.save(community);
    }

    public void delete(int id, Principal principal) {
        List<ru.sstu.communities.models.CommunityMember> members = communityMember.findAllByCommunityId(id);
        List<ru.sstu.communities.models.CommunityPost> posts = communityPost.findAllByCommunityId(id);
        ru.sstu.communities.models.Community deletedCommunity = community.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);
    }

    public ru.sstu.communities.models.Community show(int id) {
        ru.sstu.communities.models.Community community = this.community.findById(id);
        community.setMembers(communityMember.findAllByCommunityId(id));
        community.setPosts(communityPost.findAllByCommunityId(id));
        return community;
    }

    public void join(Principal principal, int communityId) {
        if (community.findById(communityId) == null)
            return;
        ru.sstu.communities.models.CommunityMember communityMember = new ru.sstu.communities.models.CommunityMember();
        communityMember.setMemberLogin(principal.getName());
        communityMember.setCommunityId(communityId);
        ru.sstu.communities.models.CommunityMember joinedCommunityMember = this.communityMember.save(communityMember);
    }

    public boolean isMember(Principal principal, int communityId) {
        return communityMember.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }

    public void leave(Principal principal, ru.sstu.communities.models.CommunityMember communityMember) {
        if (community.findById(communityMember.getCommunityId()) == null)
            return;
        communityMember.setMemberLogin(principal.getName());
        ru.sstu.communities.models.CommunityMember leftCommunityMember = this.communityMember.deleteByMemberLoginAndCommunityId(communityMember);
    }

    public void kickCommunityMember(ru.sstu.communities.models.CommunityMember communityMember, Principal principal) {
        if (community.findById(communityMember.getCommunityId()) == null)
            return;
        ru.sstu.communities.models.CommunityMember kickedCommunityMember = this.communityMember.deleteById(communityMember.getId());
    }

    public void createPost(ru.sstu.communities.models.CommunityPost communityPost, Principal principal) {
        if (community.findById(communityPost.getCommunityId()) == null)
            return;
        communityPost.setAuthorLogin(principal.getName());
        ru.sstu.communities.models.CommunityPost createdPost = this.communityPost.save(communityPost);
    }

    public void deletePost(ru.sstu.communities.models.CommunityPost communityPost, Principal principal) {
        if (community.findById(communityPost.getCommunityId()) == null)
            return;
        ru.sstu.communities.models.CommunityPost deletedCommunityPost = this.communityPost.deleteById(communityPost.getId());
    }

    public List<ru.sstu.communities.models.Community> find(String keyword) {
        if (keyword != null && !keyword.isEmpty())
            return community.findAllLikeName(keyword);
        return null;
    }

}
