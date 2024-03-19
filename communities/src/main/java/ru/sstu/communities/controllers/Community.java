package ru.sstu.communities.controllers;

import org.springframework.web.bind.annotation.*;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.services.CommunityService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class Community {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String community() {
        return "community";
    }

    @GetMapping("/community_management")
    public List<ru.sstu.communities.models.Community> showAllOwn(Principal principal) {
        return communityService.showAllOwn(principal);
    }

    @GetMapping("/my_communities")
    public List<ru.sstu.communities.models.Community> myCommunities(Principal principal) {
        return communityService.showAll(principal);
    }

    @PostMapping("/add_community")
    public void create(ru.sstu.communities.models.Community community, Principal principal) {
        communityService.create(community, principal);
    }

    @DeleteMapping("/delete_community")
    public void delete(int id, Principal principal) {
        communityService.delete(id, principal);
    }

    @GetMapping("/communities/{memberLogin}")
    public List<ru.sstu.communities.models.Community> showAll(@PathVariable String memberLogin, Principal principal) {
        if (Objects.equals(memberLogin, principal.getName()))
            return communityService.showAll(principal);
        return communityService.showAll(memberLogin);
    }

    @GetMapping("/community/{id}")
    public ru.sstu.communities.models.Community show(@PathVariable int id) {
        return communityService.show(id);
    }

    @PostMapping("/join_community")
    public void join(Principal principal, int communityId) {
        communityService.join(principal, communityId);
    }

    @DeleteMapping("/leave_community")
    public void leave(Principal principal, CommunityMember communityMember) {
        communityService.leave(principal, communityMember);
    }

    @DeleteMapping("/kick_community_member")
    public void kickCommunityMember(CommunityMember communityMember, Principal principal) {
        communityService.kickCommunityMember(communityMember, principal);
    }

    @PostMapping("/add_community_post")
    public void createPost(CommunityPost communityPost, Principal principal) {
        communityService.createPost(communityPost, principal);
    }

    @DeleteMapping("/delete_community_post")
    public void deletePost(CommunityPost communityPost, Principal principal) {
        communityService.deletePost(communityPost, principal);
    }

    @GetMapping("/find_communities")
    public List<ru.sstu.communities.models.Community> find(String keyword) {
        return communityService.find(keyword);
    }

}
