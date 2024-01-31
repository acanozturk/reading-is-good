package com.rig.orderservice.validation;

import com.rig.orderservice.validation.group.*;
import jakarta.validation.GroupSequence;

@GroupSequence(
        {
                NullValidationGroup.class,
                ValueValidationGroup.class,
                PayloadValidationGroup.class,
        }
)
public interface ValidationGroupSequence {
}
