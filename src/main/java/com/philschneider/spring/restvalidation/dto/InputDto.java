package com.philschneider.spring.restvalidation.dto;

import com.philschneider.spring.restvalidation.validation.NameToLongConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NameToLongConstraint
public class InputDto {

    String name;

    @Min(5)
    String firstName;

    @NotNull
    String type;


}
