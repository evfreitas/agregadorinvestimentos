package tech.agregadorInvestimentos.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

public record UpdateUserDto(String username, String password) {
}
