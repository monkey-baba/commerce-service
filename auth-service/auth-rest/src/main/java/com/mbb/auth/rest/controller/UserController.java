package com.mbb.auth.rest.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.auth.rest.dto.req.UserChangePwdData;
import com.mbb.auth.rest.dto.req.UserEnableData;
import com.mbb.auth.rest.dto.req.UserListQuery;
import com.mbb.auth.rest.dto.req.UserUpdateData;
import com.mbb.auth.rest.dto.resp.UserListResp;
import com.mbb.auth.rest.dto.resp.UserLoginResp;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.yuki.auth.core.entity.GroupModel;
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.entity.UserModel;
import xin.yuki.auth.core.exception.UserException;
import xin.yuki.auth.core.service.GroupService;
import xin.yuki.auth.core.service.PermissionService;
import xin.yuki.auth.core.service.RoleService;
import xin.yuki.auth.core.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity info(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        UserLoginResp info = new UserLoginResp();
        info.setName(user.getName());
        info.setUsername(user.getUsername());
        info.setId(user.getId());

        //Groups
        List<GroupModel> groups = groupService.findUserGroups(user.getId());

        final Collection<RoleModel> groupRoles =
                CollectionUtils.emptyIfNull(groups).stream()
                        .flatMap(g -> {
                            List<RoleModel> roles = roleService.findGroupRoles(g.getId());
                            return roles.stream();
                        }).collect(Collectors.toList());

        final Collection<RoleModel> allRoles = CollectionUtils
                .union(CollectionUtils.emptyIfNull(roleService.findUserRoles(user.getId())),
                        groupRoles);
        info.setRoles(
                allRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));

        //Authorities
        final List<GrantedAuthority> premissions =
                allRoles.stream().flatMap(
                        role -> permissionService.findRolePermissions(role.getId()).stream())
                        .collect(Collectors.toList());
        info.setAuthorities(premissions.stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.toList()));

        return ResponseEntity.ok(info);
    }

    @GetMapping("/list")
    public ResponseEntity info(UserListQuery query) {
        UserModel user = new UserModel();
        user.setUsername(query.getUsername());
        user.setName(query.getName());
        user.setMobileNumber(query.getMobileNumber());

        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserModel> users = userService.findUserByExample(user);
        List<UserListResp> list = users.stream().map(u -> {
            UserListResp info = new UserListResp();
            info.setId(u.getId());
            info.setUsername(u.getUsername());
            info.setName(u.getName());
            info.setEmail(u.getEmail());
            info.setMobileNumber(u.getMobileNumber());
            info.setEnabled(u.getActive());
            List<GroupModel> groups = groupService.findUserGroups(u.getId());
            info.setGroups(groups.stream().map(GroupModel::getName)
                    .collect(Collectors.toList()));

            final Collection<RoleModel> groupRoles =
                    CollectionUtils.emptyIfNull(groups).stream()
                            .flatMap(g -> {
                                List<RoleModel> roles = roleService.findGroupRoles(g.getId());
                                return roles.stream();
                            }).collect(Collectors.toList());
            final Collection<RoleModel> allRoles = CollectionUtils
                    .union(CollectionUtils.emptyIfNull(roleService.findUserRoles(user.getId())),
                            groupRoles);

            info.setRoles(allRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));

            return info;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(PageInfo.of(list));


    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserUpdateData data) throws UserException {
        UserModel user=userService.findById(data.getId());
        user.setId(data.getId());
        user.setMobileNumber(data.getMobileNumber());
        user.setEmail(data.getEmail());
        user.setName(data.getName());
        user.setUsername(data.getUsername());
        userService.updateUser(user);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/changePwd")
    public ResponseEntity changePwd(@RequestBody UserChangePwdData data) {
        userService.changePassword(data.getUsername(),data.getPassword());
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/enable")
    public ResponseEntity enable(@RequestBody UserEnableData data) {
        userService.enableUser(data.getUsername(),data.getEnabled());
        return ResponseEntity.ok("更新成功");
    }

}
