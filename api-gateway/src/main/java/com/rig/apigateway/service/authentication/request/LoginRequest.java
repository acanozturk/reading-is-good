package com.rig.apigateway.service.authentication.request;

import com.rig.apigateway.validation.ValidationGroupSequence;
import com.rig.apigateway.validation.group.BlankValidationGroup;
import com.rig.apigateway.validation.group.FormatValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@GroupSequence({ValidationGroupSequence.class, LoginRequest.class})
public final class LoginRequest {

    @NotBlank(message = "{constraint.email.blank}", groups = BlankValidationGroup.class)
    @Email(message = "{constraint.email.format}", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", groups = FormatValidationGroup.class)
    private String email;

    @NotBlank(message = "{constraint.password.blank}", groups = BlankValidationGroup.class)
    private String password;
    
}
