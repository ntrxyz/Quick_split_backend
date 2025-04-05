package com.billsplitter.service;

import com.billsplitter.model.Group;
import com.billsplitter.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(String name) {
        Group group = new Group(name);
        return groupRepository.save(group);
    }

    public Optional<Group> getGroupById(String groupId) {
        return groupRepository.findById(groupId);
    }

    public List<Group> getGroupsByUserId(String userId) {
        return groupRepository.findByMembersContaining(userId);
    }

    public Group updateGroup(String groupId, String newName) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.setName(newName);
            return groupRepository.save(group);
        }
        return null;
    }

    public boolean deleteGroup(String groupId) {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            return true;
        }
        return false;
    }

    public boolean addUserToGroup(String groupId, String userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getMembers().add(userId);  // Add user ID
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    public boolean removeUserFromGroup(String groupId, String userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);

        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getMembers().remove(userId);
            groupRepository.save(group);
            return true;
        }
        return false;
    }
}
