package com.rig.bookservice.validation;

import com.rig.bookservice.validation.group.*;
import jakarta.validation.GroupSequence;

@GroupSequence(
        {
                BlankValidationGroup.class,
                NullValidationGroup.class,
                ValueValidationGroup.class,
                SizeValidationGroup.class,
                PayloadValidationGroup.class,
        }
)
public interface ValidationGroupSequence {
}
