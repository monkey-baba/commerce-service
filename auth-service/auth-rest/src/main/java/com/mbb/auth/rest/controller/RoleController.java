package com.mbb.auth.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.auth.rest.dto.req.GroupChangeRoleData;
import com.mbb.auth.rest.dto.req.RoleChangeRoleData;
import com.mbb.auth.rest.dto.req.RoleCreateData;
import com.mbb.auth.rest.dto.req.RoleDeleteData;
import com.mbb.auth.rest.dto.req.RoleListQuery;
import com.mbb.auth.rest.dto.req.RoleUpdateData;
import com.mbb.auth.rest.dto.req.UserChangeRoleData;
import com.mbb.auth.rest.dto.resp.ChangeRoleResp;
import com.mbb.auth.rest.dto.resp.RoleData;
import com.mbb.auth.rest.dto.resp.RoleListResp;
import com.mbb.auth.rest.dto.resp.RoleSelectResp;
import com.mbb.auth.rest.dto.resp.RoleTreeResp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.yuki.auth.core.entity.RoleModel;
import xin.yuki.auth.core.exception.GroupException;
import xin.yuki.auth.core.exception.RoleException;
import xin.yuki.auth.core.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController extends BaseController {

    @Autowired
    private IdService idService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/userRole")
    public ResponseEntity userGroup(@RequestParam Long id) {
        List<Long> userRoles = roleService.findUserRoles(id).stream().map(RoleModel::getId).collect(
                Collectors.toList());

        List<RoleModel> allRoles = roleService.findAll();

        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(userRoles.contains(r.getId()));
            return data;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/groupRole")
    public ResponseEntity groupRole(@RequestParam Long id) {
        List<Long> groupRoles = roleService.findGroupRoles(id).stream().map(RoleModel::getId)
                .collect(Collectors.toList());

        List<RoleModel> allRoles = roleService.findAll();
        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(groupRoles.contains(r.getId()));
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


    @GetMapping("/list")
    public ResponseEntity list(RoleListQuery query) {
        RoleModel role = new RoleModel();
        role.setCode(query.getCode());
        role.setName(query.getName());

        //开启分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        //查询数据
        List<RoleModel> roles = roleService.findRoleByExample(role);
        //获取页码等信息
        PageInfo<RoleModel> origin = PageInfo.of(roles);

        //Model转Data
        List<RoleListResp> list = origin.getList().stream().map(r -> {
            RoleListResp info = new RoleListResp();
            info.setId(r.getId());
            info.setName(r.getName());
            info.setCode(r.getCode());
            info.setDescription(r.getDescription());

            Collection<RoleModel> parents = CollectionUtils
                    .emptyIfNull(roleService.findParentRoles(r.getId()));
            info.setParents(parents.stream().map(RoleModel::getName).collect(Collectors.toList()));

            Collection<RoleModel> children = CollectionUtils
                    .emptyIfNull(roleService.findChildRoles(r.getId()));
            info.setChildren(
                    children.stream().map(RoleModel::getName).collect(Collectors.toList()));

            return info;
        }).collect(Collectors.toList());

        //用data生成新的分页数据
        PageInfo<RoleListResp> result = PageInfo.of(list);
        //把原来的总条数复制进去
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);

    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody RoleUpdateData data) throws RoleException {
        RoleModel role = roleService.findById(data.getId());
        role.setId(data.getId());
        role.setCode(data.getCode());
        role.setName(data.getName());
        role.setDescription(data.getDescription());
        roleService.updateRole(role);
        return ResponseEntity.ok("更新成功");
    }


    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RoleCreateData data) {
        RoleModel role = new RoleModel();
        role.setId(idService.genId());
        role.setName(data.getName());
        role.setCode(data.getCode());
        role.setDescription(data.getDescription());
        role.setVersion(0L);
        roleService.createRole(role);
        RoleListResp resp = new RoleListResp();
        resp.setId(role.getId());
        resp.setCode(role.getCode());
        resp.setName(role.getName());
        resp.setDescription(role.getDescription());
        resp.setChildren(Collections.emptyList());
        resp.setParents(Collections.emptyList());
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody List<RoleDeleteData> datas) throws GroupException {
        roleService.deleteRoles(
                datas.stream().map(RoleDeleteData::getId).collect(Collectors.toList()));
        return ResponseEntity.ok("删除成功");
    }


    @GetMapping("/parentRole")
    public ResponseEntity parentRole(@RequestParam Long id) {
        List<Long> parentRoles = roleService.findParentRoles(id).stream().map(RoleModel::getId)
                .collect(Collectors.toList());

        List<Long> childRoles = roleService.findAllChildRoles(id).stream().map(RoleModel::getId)
                .collect(Collectors.toList());

        //排除掉已经被设置成子角色的
        List<RoleModel> allRoles = roleService.findAll().stream()
                .filter(r -> !childRoles.contains(r.getId())).filter(
                        r -> !r.getId().equals(id)
                ).collect(Collectors.toList());
        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(parentRoles.contains(r.getId()));
            return data;
        }).collect(Collectors.toList()));
    }


    @GetMapping("/childRole")
    public ResponseEntity childRole(@RequestParam Long id) {

        List<Long> parentRoles = roleService.findAllParentRoles(id).stream().map(RoleModel::getId)
                .collect(Collectors.toList());

        List<Long> childRoles = roleService.findChildRoles(id).stream().map(RoleModel::getId)
                .collect(Collectors.toList());
        //排除掉已经被设置成父角色的
        List<RoleModel> allRoles = roleService.findAll().stream()
                .filter(r -> !parentRoles.contains(r.getId())).filter(
                        r -> !r.getId().equals(id)
                ).collect(Collectors.toList());
        return ResponseEntity.ok(allRoles.stream().map(r -> {
            RoleData data = new RoleData();
            data.setKey(r.getId());
            data.setLabel(r.getCode() + "-" + r.getName());
            data.setExists(childRoles.contains(r.getId()));
            return data;
        }).collect(Collectors.toList()));
    }


    @PostMapping("/changeParentRole")
    public ResponseEntity changeParentRole(@RequestBody RoleChangeRoleData data) {
        //更新父角色
        roleService.changeParentRoles(data.getRoleId(), data.getRoles());
        return ResponseEntity.ok("更新成功");

    }

    @PostMapping("/changeChildRole")
    public ResponseEntity changeChildRole(@RequestBody RoleChangeRoleData data) {
        //更新子角色
        roleService.changeChildRoles(data.getRoleId(), data.getRoles());
        return ResponseEntity.ok("更新成功");

    }


    @GetMapping("/tree")
    public ResponseEntity tree() {
        //更新子角色
        List<RoleModel> roles = roleService.findRootRoles();
        List<RoleTreeResp> resp = getChildRoleTree(ListUtils.emptyIfNull(roles));
        return ResponseEntity.ok(resp);

    }

    /**
     * 从根获取角色树
     * @param roots
     * @return
     */
    private List<RoleTreeResp> getChildRoleTree(List<RoleModel> roots) {
        List<RoleTreeResp> list=new ArrayList<>();
        for (RoleModel root : roots) {
            RoleTreeResp role = new RoleTreeResp();
            role.setId(root.getId());
            role.setLabel(root.getCode() + "-" + root.getName());
            List<RoleModel> childRoles = roleService.findChildRoles(root.getId());
            if (CollectionUtils.isNotEmpty(childRoles)){
                List<RoleTreeResp> childRoleTree = getChildRoleTree(childRoles);
                role.setChildren(childRoleTree);
            }
            list.add(role);
        }

        return list;
    }

    @GetMapping("/findRoleByName")
    public ResponseEntity findRoleByName(@RequestParam String name) {
        //更新子角色
        List<RoleModel> roles = roleService.findByNameOrCode(name);
        List<RoleSelectResp> resp = roles.stream().map(r -> {
            RoleSelectResp role = new RoleSelectResp();
            role.setKey(r.getId());
            role.setLabel(r.getCode() + "-" + r.getName());
            return role;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(resp);

    }
}
