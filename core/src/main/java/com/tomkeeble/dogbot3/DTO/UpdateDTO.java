package com.tomkeeble.dogbot3.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDTO {
    List<UserDTO> users;
    List<GroupDTO> groups;
    List<NicknameDTO> nicknames;

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

    public List<NicknameDTO> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<NicknameDTO> nicknames) {
        this.nicknames = nicknames;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
