package com.mbb.auth.rest.controller;


import com.mbb.auth.rest.dto.UserInfoDto;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.entity.UserModel;
import xin.yuki.auth.core.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity info(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        UserInfoDto info = new UserInfoDto();
        info.setName(user.getName());
        info.setUsername(user.getUsername());

        //Roles
        final Collection<RoleModel> groupRoles =
                CollectionUtils.emptyIfNull(user.getGroups()).stream()
                        .flatMap(g -> g.getRoles().stream()).collect(Collectors.toList());
        final Collection<RoleModel> allRoles = CollectionUtils
                .union(CollectionUtils.emptyIfNull(user.getRoles()),
                        groupRoles);
        info.setRoles(
                allRoles.stream().map(RoleModel::getName).collect(Collectors.toList()));

        //Authorities
        final List<GrantedAuthority> premissions =
                allRoles.stream().flatMap(role -> role.getPermissions().stream()).collect(Collectors.toList());
        info.setAuthorities(premissions.stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.toList()));

        return ResponseEntity.ok(info);
}

}
