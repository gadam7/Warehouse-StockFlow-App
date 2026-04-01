package com.adamidis.learning.warehousestockflow.Utils;

import com.adamidis.learning.warehousestockflow.DTO.UserDTO;
import com.adamidis.learning.warehousestockflow.Model.UserPrincipal;
import org.springframework.security.core.Authentication;

public class UserUtils {
    public static UserDTO getAuthenticatedUser(Authentication authentication) {
        return ((UserDTO) authentication.getPrincipal());
    }


    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }
}
