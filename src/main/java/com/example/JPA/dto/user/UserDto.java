package com.example.JPA.dto.user;

import com.example.JPA.dto.OrdersDto;
import com.example.JPA.model.CardPass;
import com.example.JPA.model.Orders;
import com.example.JPA.model.Role;
import com.example.JPA.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.h2.engine.UserBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    private String role;
    private String cardHolderName;
    private Integer amount;
    private List<OrdersDto> orders = new ArrayList<>();
    private CardPass cardPass;

    public UserDto toUserDto(User user) {
        return new UserDtoBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .role(user.getRole().toString())
                .build();
    }


    public UserDto toUserDto(User user,CardPass cardPass,List<Orders> ordersList){
        return new UserDtoBuilder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .role(user.getRole().toString())
                .cardPass(cardPass)
                .orders(new OrdersDto().toOrdersDto(ordersList))
                .build();
    }


}
