package com.rig.authenticationservice.validation;

import com.rig.authenticationservice.validation.group.*;
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
