package com.rig.apigateway.validation;

import com.rig.apigateway.validation.group.*;
import jakarta.validation.GroupSequence;

@GroupSequence(
        {
                BlankValidationGroup.class,
                NullValidationGroup.class,
                ValueValidationGroup.class,
                FormatValidationGroup.class,
                SizeValidationGroup.class,
                PayloadValidationGroup.class
        }
)
public interface ValidationGroupSequence {
}
