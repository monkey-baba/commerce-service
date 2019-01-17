package com.mbb.auth.rest.controller;

import com.mbb.auth.rest.dto.req.GroupChangeRoleData;
import com.mbb.auth.rest.dto.req.UserChangeRoleData;
import com.mbb.auth.rest.dto.resp.RoleData;
import com.mbb.auth.rest.dto.resp.ChangeRoleResp;
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
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.service.GroupService;
import xin.yuki.auth.core.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController extends BaseController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/userRole")
    public ResponseEntity userGroup(@RequestParam Long id) {
        Stream<Long> userRoles = roleService.findUserRoles(id).stream().map(RoleModel::getId);

        List<RoleModel> allRoles = roleService.findAll();

        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(userRoles.anyMatch(rid -> rid.equals(r.getId())));
            return data;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/groupRole")
    public ResponseEntity groupRole(@RequestParam Long id) {
        Stream<Long> groupRoles = roleService.findGroupRoles(id).stream().map(RoleModel::getId);

        List<RoleModel> allRoles = roleService.findAll();
        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(groupRoles.anyMatch(rid -> rid.equals(r.getId())));
            return data;
        }).collect(Collectors.toList()));
    }


    @PostMapping("/changeUserRole")
    public ResponseEntity changeUserGroup(@RequestBody UserChangeRoleData data) {
        //更新用户组
        roleService.changeUserRoles(data.getUserId(), data.getRoles());
        //查询新的用户组
//        List<GroupModel> groups = groupService.findUserGroups(data.getUserId());

        ChangeRoleResp resp = new ChangeRoleResp();

//        final Collection<RoleModel> groupRoles =
//                CollectionUtils.emptyIfNull(groups).stream()
//                        .flatMap(g -> {
//                            List<RoleModel> roles = roleService.findGroupRoles(g.getId());
//                            return roles.stream();
//                        }).collect(Collectors.toList());
        final Collection<RoleModel> groupRoles = Collections.emptyList();
        final Collection<RoleModel> allRoles = CollectionUtils
                .union(CollectionUtils.emptyIfNull(roleService.findUserRoles(data.getUserId())),
                        groupRoles);
        resp.setRoleNames(allRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));
        return ResponseEntity.ok(resp);

    }

    @PostMapping("/changeGroupRole")
    public ResponseEntity changeGroupRole(@RequestBody GroupChangeRoleData data) {
        //更新用户组
        roleService.changeGroupRoles(data.getGroupId(), data.getRoles());
        ChangeRoleResp resp = new ChangeRoleResp();
        final Collection<RoleModel> groupRoles = CollectionUtils
                .emptyIfNull(roleService.findGroupRoles(data.getGroupId()));
        resp.setRoleNames(groupRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));
        return ResponseEntity.ok(resp);

    }
}
