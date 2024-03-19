package ru.sstu.communities.services;

import ru.sstu.communities.models.Community;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.repositories.CommunityPostRepository;
import ru.sstu.communities.repositories.CommunityRepository;
import ru.sstu.communities.repositories.CommunityMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;

    public List<Community> showAllOwn(Principal principal) {
        return communityRepository.findAllByCreatorLogin(principal.getName());
    }

    public List<Community> showAll(Principal principal) {
        return communityRepository.findAllByMemberLogin(principal.getName());
    }

    public List<Community> showAll(String memberLogin) {
        return communityRepository.findAllByMemberLogin(memberLogin);
    }

    public void create(Community community, Principal principal) {
        community.setCreatorLogin(principal.getName());
        Community createdCommunity = communityRepository.save(community);
    }

    public void delete(int id, Principal principal) {
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityId(id);
        List<CommunityPost> posts = communityPostRepository.findAllByCommunityId(id);
        Community deletedCommunity = communityRepository.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);
    }

    public Community show(int id) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        return community;
    }

    public void join(Principal principal, int communityId) {
        if (communityRepository.findById(communityId) == null)
            return;
        CommunityMember communityMember = new CommunityMember();
        communityMember.setMemberLogin(principal.getName());
        communityMember.setCommunityId(communityId);
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
    }

    public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }

    public void leave(Principal principal, CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return;
        communityMember.setMemberLogin(principal.getName());
        CommunityMember leftCommunityMember = communityMemberRepository.deleteByMemberLoginAndCommunityId(communityMember);
    }

    public void kickCommunityMember(CommunityMember communityMember, Principal principal) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return;
        CommunityMember kickedCommunityMember = communityMemberRepository.deleteById(communityMember.getId());
    }

    public void createPost(CommunityPost communityPost, Principal principal) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return;
        communityPost.setAuthorLogin(principal.getName());
        CommunityPost createdPost = communityPostRepository.save(communityPost);
    }

    public void deletePost(CommunityPost communityPost, Principal principal) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return;
        CommunityPost deletedCommunityPost = communityPostRepository.deleteById(communityPost.getId());
    }

    public List<Community> find(String keyword) {
        if (keyword != null && !keyword.isEmpty())
            return communityRepository.findAllLikeName(keyword);
        return null;
    }

}
