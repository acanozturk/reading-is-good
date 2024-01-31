package com.rig.orderservice.data.payload.request;

import com.rig.orderservice.validation.ValidationGroupSequence;
import com.rig.orderservice.validation.data.CreateOrderValid;
import com.rig.orderservice.validation.group.NullValidationGroup;
import com.rig.orderservice.validation.group.PayloadValidationGroup;
import com.rig.orderservice.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CreateOrderValid(groups = PayloadValidationGroup.class)
@GroupSequence({ValidationGroupSequence.class, CreateOrderRequest.class})
public final class CreateOrderRequest {

    @NotNull(message = "{constraint.bookId.null}", groups = NullValidationGroup.class)
    private Long bookId;

    @NotNull(message = "{constraint.customerId.null}", groups = NullValidationGroup.class)
    private Long customerId;

    @NotNull(message = "{constraint.deliveryAddressId.null}", groups = NullValidationGroup.class)
    private Long deliveryAddressId;

    @NotNull(message = "{constraint.orderQuantity.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.orderQuantity.positive}", groups = ValueValidationGroup.class)
    private Integer orderQuantity;
    
}
