package com.rig.bookservice.data.payload.request;

import com.rig.bookservice.validation.ValidationGroupSequence;
import com.rig.bookservice.validation.data.UpdateBookStockValid;
import com.rig.bookservice.validation.group.NullValidationGroup;
import com.rig.bookservice.validation.group.PayloadValidationGroup;
import com.rig.bookservice.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UpdateBookStockValid(groups = PayloadValidationGroup.class)
@GroupSequence({ValidationGroupSequence.class, UpdateBookStockRequest.class})
public final class UpdateBookStockRequest {

    @NotNull(message = "{constraint.bookId.null}", groups = NullValidationGroup.class)
    private Long bookId;

    @NotNull(message = "{constraint.totalQuantity.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.totalQuantity.positive}", groups = ValueValidationGroup.class)
    private Integer totalQuantity;
    
}
