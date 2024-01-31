package com.rig.apigateway.service.customer.request;

import com.rig.apigateway.service.customer.Gender;
import com.rig.apigateway.validation.ValidationGroupSequence;
import com.rig.apigateway.validation.group.BlankValidationGroup;
import com.rig.apigateway.validation.group.NullValidationGroup;
import com.rig.apigateway.validation.group.SizeValidationGroup;
import com.rig.apigateway.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@GroupSequence({ValidationGroupSequence.class, CreateCustomerRequest.class})
public final class CreateCustomerRequest {

    @NotNull(message = "{constraint.gender.null}", groups = NullValidationGroup.class)
    private Gender gender;

    @NotBlank(message = "{constraint.firstName.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.firstName.size}", groups = SizeValidationGroup.class)
    private String firstName;

    @NotBlank(message = "{constraint.lastName.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.lastName.size}", groups = SizeValidationGroup.class)
    private String lastName;

    @NotNull(message = "{constraint.dateOfBirth.null}", groups = NullValidationGroup.class)
    @Past(message = "{constraint.dateOfBirth.past}", groups = ValueValidationGroup.class)
    private LocalDate dateOfBirth;

    @NotNull(message = "{constraint.account.null}", groups = NullValidationGroup.class)
    @Valid
    private CreateAccountPayload account;

    @NotNull(message = "{constraint.address.null}", groups = NullValidationGroup.class)
    @Valid
    private CreateCustomerAddressPayload address;

}
