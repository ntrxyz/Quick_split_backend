package com.billsplitter.controller;

import com.billsplitter.model.Group;
import com.billsplitter.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestParam String name) {
        Group group = groupService.createGroup(name);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable String groupId) {
        return groupService.getGroupById(groupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserGroups(@PathVariable String userId) {
        List<Group> groups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable String groupId, @RequestParam String newName) {
        Group updatedGroup = groupService.updateGroup(groupId, newName);
        if (updatedGroup != null) {
            return ResponseEntity.ok(updatedGroup);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupId) {
        if (groupService.deleteGroup(groupId)) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Group deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{groupId}/add-user/{userId}")
    public ResponseEntity<?> addUserToGroup(@PathVariable String groupId, @PathVariable String userId) {
        if (groupService.addUserToGroup(groupId, userId)) {
            return ResponseEntity.ok(Collections.singletonMap("message", "User added successfully"));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{groupId}/remove-user/{userId}")
    public ResponseEntity<?> removeUserFromGroup(@PathVariable String groupId, @PathVariable String userId) {
        if (groupService.removeUserFromGroup(groupId, userId)) {
            return ResponseEntity.ok(Collections.singletonMap("message", "User removed successfully"));
        }
        return ResponseEntity.notFound().build();
    }

}
