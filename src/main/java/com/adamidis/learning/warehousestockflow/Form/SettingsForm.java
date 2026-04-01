package com.adamidis.learning.warehousestockflow.Form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsForm {
    @NotNull(message = "Enabled status is required")
    private Boolean enabled;
    @NotNull(message = "Account lock status is required")
    private Boolean notLocked;
}
