package com.mbb.auth.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.auth.rest.dto.req.GroupCreateData;
import com.mbb.auth.rest.dto.req.GroupDeleteData;
import com.mbb.auth.rest.dto.req.GroupListQuery;
import com.mbb.auth.rest.dto.req.GroupUpdateData;
import com.mbb.auth.rest.dto.req.UserChangeGroupData;
import com.mbb.auth.rest.dto.resp.GroupListResp;
import com.mbb.auth.rest.dto.resp.UserChangeGroupResp;
import com.mbb.auth.rest.dto.resp.UserGroupData;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.yuki.auth.core.entity.GroupModel;
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.exception.GroupException;
import xin.yuki.auth.core.service.GroupService;
import xin.yuki.auth.core.service.RoleService;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private IdService idService;
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


    @GetMapping("/list")
    public ResponseEntity list(GroupListQuery query) {
        GroupModel group = new GroupModel();
        group.setCode(query.getCode());
        group.setName(query.getName());

        //开启分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        //查询数据
        List<GroupModel> groups = groupService.findGroupByExample(group);
        //获取页码等信息
        PageInfo<GroupModel> origin = PageInfo.of(groups);

        //Model转Data
        List<GroupListResp> list = origin.getList().stream().map(g -> {
            GroupListResp info = new GroupListResp();
            info.setId(g.getId());
            info.setName(g.getName());
            info.setCode(g.getCode());
            info.setDescription(g.getDescription());
            final Collection<RoleModel> roles = CollectionUtils
                    .emptyIfNull(roleService.findGroupRoles(g.getId()));
            info.setRoles(roles.stream().map(RoleModel::getName).collect(Collectors.toList()));
            return info;
        }).collect(Collectors.toList());

        //用data生成新的分页数据
        PageInfo<GroupListResp> result = PageInfo.of(list);
        //把原来的总条数复制进去
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);

    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody GroupUpdateData data) throws GroupException {
        GroupModel group = groupService.findById(data.getId());
        group.setId(data.getId());
        group.setCode(data.getCode());
        group.setName(data.getName());
        group.setDescription(data.getDescription());
        groupService.updateGroup(group);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody GroupCreateData data) {
        GroupModel group = new GroupModel();
        group.setId(idService.genId());
        group.setName(data.getName());
        group.setCode(data.getCode());
        group.setDescription(data.getDescription());
        group.setVersion(0L);
        groupService.createGroup(group);
        GroupListResp resp = new GroupListResp();
        resp.setId(group.getId());
        resp.setCode(group.getCode());
        resp.setName(group.getName());
        resp.setDescription(group.getDescription());
        resp.setRoles(Collections.emptyList());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody List<GroupDeleteData> datas) throws GroupException {
        groupService.deleteGroups(
                datas.stream().map(GroupDeleteData::getId).collect(Collectors.toList()));
        return ResponseEntity.ok("删除成功");
    }
}
