package com.rig.apigateway.service.book.request;

import com.rig.apigateway.validation.ValidationGroupSequence;
import com.rig.apigateway.validation.group.NullValidationGroup;
import com.rig.apigateway.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@GroupSequence({ValidationGroupSequence.class, UpdateBookStockRequest.class})
public final class UpdateBookStockRequest {

    @NotNull(message = "{constraint.bookId.null}", groups = NullValidationGroup.class)
    private Long bookId;

    @NotNull(message = "{constraint.totalQuantity.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.totalQuantity.positive}", groups = ValueValidationGroup.class)
    private Integer totalQuantity;
    
}
