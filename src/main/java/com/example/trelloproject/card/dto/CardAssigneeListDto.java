package com.example.trelloproject.card.dto;

import com.example.trelloproject.user.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CardAssigneeListDto {
    private List<User> assignee = new ArrayList<>();
}
