package com.rig.customerservice.validation;

import com.rig.customerservice.validation.group.*;
import jakarta.validation.GroupSequence;

@GroupSequence(
        {
                BlankValidationGroup.class,
                NullValidationGroup.class,
                FormatValidationGroup.class,
                ValueValidationGroup.class,
                SizeValidationGroup.class
        }
)
public interface ValidationGroupSequence {
}
