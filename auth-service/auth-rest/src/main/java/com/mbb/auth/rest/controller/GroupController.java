package com.mbb.auth.rest.controller;

import com.mbb.auth.rest.dto.req.UserChangeGroupData;
import com.mbb.auth.rest.dto.resp.UserChangeGroupResp;
import com.mbb.auth.rest.dto.resp.UserGroupData;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.yuki.auth.core.entity.GroupModel;
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.service.GroupService;
import xin.yuki.auth.core.service.RoleService;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/userGroup")
    public ResponseEntity userGroup(@RequestParam Long id) {
        Stream<Long> userGroups = groupService.findUserGroups(id).stream().map(GroupModel::getId);

        List<GroupModel> allGroups = groupService.findAll();

        return ResponseEntity.ok(allGroups.stream().map(g -> {
            UserGroupData data = new UserGroupData();
            data.setKey(g.getId());
            data.setLabel(g.getCode() + "-" + g.getName());
            data.setExists(userGroups.anyMatch(gid -> gid.equals(g.getId())));
            return data;
        }).collect(Collectors.toList()));
    }


    @PostMapping("/changeUserGroup")
    public ResponseEntity changeUserGroup(@RequestBody UserChangeGroupData data) {
        //更新用户组
        groupService.changeUserGroups(data.getUserId(), data.getGroups());
        //查询新的用户组
        List<GroupModel> groups = groupService.findUserGroups(data.getUserId());

        UserChangeGroupResp resp = new UserChangeGroupResp();
        resp.setGroupNames(groups.stream().map(GroupModel::getName).collect(Collectors.toList()));

//        final Collection<RoleModel> groupRoles =
//                CollectionUtils.emptyIfNull(groups).stream()
//                        .flatMap(g -> {
//                            List<RoleModel> roles = roleService.findGroupRoles(g.getId());
//                            return roles.stream();
//                        }).collect(Collectors.toList());
//        final Collection<RoleModel> allRoles = CollectionUtils
//                .union(CollectionUtils.emptyIfNull(roleService.findUserRoles(data.getUserId())),
//                        groupRoles);
//        resp.setRoleNames(allRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));
        return ResponseEntity.ok(resp);

    }
}
