package com.rig.bookservice.data.payload.request;

import com.rig.bookservice.validation.ValidationGroupSequence;
import com.rig.bookservice.validation.group.BlankValidationGroup;
import com.rig.bookservice.validation.group.NullValidationGroup;
import com.rig.bookservice.validation.group.SizeValidationGroup;
import com.rig.bookservice.validation.group.ValueValidationGroup;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@GroupSequence({ValidationGroupSequence.class, CreateBookRequest.class})
public final class CreateBookRequest {

    @NotBlank(message = "{constraint.title.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.title.size}", groups = SizeValidationGroup.class)
    private String title;

    @NotBlank(message = "{constraint.genre.blank}", groups = BlankValidationGroup.class)
    @Size(max = 50, message = "{constraint.genre.size}", groups = SizeValidationGroup.class)
    private String genre;

    @NotBlank(message = "{constraint.author.blank}", groups = BlankValidationGroup.class)
    @Size(max = 100, message = "{constraint.author.size}", groups = SizeValidationGroup.class)
    private String author;

    @NotNull(message = "{constraint.publishDate.null}", groups = NullValidationGroup.class)
    @Past(message = "{constraint.publishDate.past}", groups = ValueValidationGroup.class)
    private LocalDate publishDate;

    @NotNull(message = "{constraint.pages.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.pages.positive}", groups = ValueValidationGroup.class)
    private Integer pages;

    @NotNull(message = "{constraint.price.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.price.positive}", groups = ValueValidationGroup.class)
    private Double price;

    @NotNull(message = "{constraint.totalQuantity.null}", groups = NullValidationGroup.class)
    @Positive(message = "{constraint.totalQuantity.positive}", groups = ValueValidationGroup.class)
    private Integer totalQuantity;
    
}
