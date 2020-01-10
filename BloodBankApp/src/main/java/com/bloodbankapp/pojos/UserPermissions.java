package com.bloodbankapp.pojos;

import java.util.ArrayList;
import java.util.List;

public class UserPermissions {

private int userId;
private List<Integer> roleIdList = new ArrayList<>();
private List<Permission> permissionList = new ArrayList<>();
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public List<Integer> getRoleIdList() {
	return roleIdList;
}
public void setRoleIdList(List<Integer> roleIdList) {
	this.roleIdList = roleIdList;
}
public List<Permission> getPermissionList() {
	return permissionList;
}
public void setPermissionList(List<Permission> permissionList) {
	this.permissionList = permissionList;
}
}
