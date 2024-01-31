package com.rig.customerservice.data.payload;

import com.rig.customerservice.validation.ValidationGroupSequence;
import com.rig.customerservice.validation.data.UniqueEmail;
import com.rig.customerservice.validation.data.UniquePhone;
import com.rig.customerservice.validation.group.FormatValidationGroup;
import com.rig.customerservice.validation.group.NullValidationGroup;
import com.rig.customerservice.validation.group.SizeValidationGroup;
import com.rig.customerservice.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@GroupSequence({ValidationGroupSequence.class, CreateAccountPayload.class})
public final class CreateAccountPayload {

    @NotBlank(message = "{constraint.email.blank}", groups = NullValidationGroup.class)
    @Size(max = 255, message = "{constraint.email.size}", groups = SizeValidationGroup.class)
    @Email(message = "{constraint.email.format}", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", groups = FormatValidationGroup.class)
    @UniqueEmail(groups = ValueValidationGroup.class)
    private String email;

    @NotBlank(message = "{constraint.password.blank}", groups = NullValidationGroup.class)
    @Size(max = 255, message = "{constraint.password.size}", groups = SizeValidationGroup.class)
    private String password;

    @NotBlank(message = "{constraint.phone.blank}", groups = NullValidationGroup.class)
    @Size(max = 20, message = "{constraint.phone.size}", groups = SizeValidationGroup.class)
    @UniquePhone(groups = ValueValidationGroup.class)
    private String phone;

}
