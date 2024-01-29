package com.rig.apigateway.validation;

import com.rig.apigateway.validation.group.BlankValidationGroup;
import com.rig.apigateway.validation.group.FormatValidationGroup;
import com.rig.apigateway.validation.group.PayloadValidationGroup;
import jakarta.validation.GroupSequence;

@GroupSequence(
        {
                BlankValidationGroup.class,
                FormatValidationGroup.class,
                PayloadValidationGroup.class,
        }
)
public interface ValidationGroupSequence {
}
