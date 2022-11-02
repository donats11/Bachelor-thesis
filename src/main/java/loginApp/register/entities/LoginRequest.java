package loginApp.register.entities;

import io.smallrye.common.constraint.NotNull;

public class LoginRequest {
    @NotNull
    public String username;
    @NotNull
    public String password;
}
