package org.portfolio.enums;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private final String login;
    private final String password;
}
