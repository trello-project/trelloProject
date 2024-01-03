package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.UserCard;
import com.example.trelloproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardAssigneeListDto {
    private List<UserCard> assignee = new ArrayList<>();
}
