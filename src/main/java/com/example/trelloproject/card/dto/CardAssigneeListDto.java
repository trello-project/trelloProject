package com.example.trelloproject.card.dto;

import com.example.trelloproject.user.entity.User;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CardAssigneeListDto {
    private Set<User> assignee = new LinkedHashSet<>();
}
