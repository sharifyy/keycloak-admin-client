package com.example.keycloakapi.permission;

import java.util.Set;

public class GroupPermissionDTO {

    private String permissionId;
    private Set<Group> groups;

    public static class Group{
        private final String id;
        private final String name;

        public Group(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
