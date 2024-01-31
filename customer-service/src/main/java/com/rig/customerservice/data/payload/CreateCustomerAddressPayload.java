package com.rig.customerservice.data.payload;

import com.rig.customerservice.validation.ValidationGroupSequence;
import com.rig.customerservice.validation.group.BlankValidationGroup;
import com.rig.customerservice.validation.group.SizeValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@GroupSequence({ValidationGroupSequence.class, CreateCustomerAddressPayload.class})
public final class CreateCustomerAddressPayload {

    @NotBlank(message = "{constraint.title.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.title.size}", groups = SizeValidationGroup.class)
    private String title;

    @NotBlank(message = "{constraint.country.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.country.size}", groups = SizeValidationGroup.class)
    private String country;

    @NotBlank(message = "{constraint.city.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.city.size}", groups = SizeValidationGroup.class)
    private String city;

    @NotBlank(message = "{constraint.district.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.district.size}", groups = SizeValidationGroup.class)
    private String district;

    @NotBlank(message = "{constraint.street.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.street.size}", groups = SizeValidationGroup.class)
    private String street;

    @NotBlank(message = "{constraint.houseNumber.blank}", groups = BlankValidationGroup.class)
    @Size(max = 20, message = "{constraint.houseNumber.size}", groups = SizeValidationGroup.class)
    private String houseNumber;

    @NotBlank(message = "{constraint.postCode.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.postCode.size}", groups = SizeValidationGroup.class)
    private String postCode;

    private String description;

}
