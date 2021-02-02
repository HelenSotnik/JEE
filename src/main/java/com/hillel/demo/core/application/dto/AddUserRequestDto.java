package com.hillel.demo.core.application.dto;

import com.hillel.demo.core.application.validator.UserAgeConstraint;
import com.hillel.demo.core.database.entity.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@ApiModel(value = "Add User Request", description = "Request Model for creating user")
@Getter
@Setter
public class AddUserRequestDto {

    @ApiModelProperty(value = "User gender", example = "MALE", required = true)
    @NotNull
    private Gender gender;

    @ApiModelProperty(value = "User First Name", example = "Helen", required = true)
    @NotBlank
    private String firstName;

    @ApiModelProperty(value = "User Last Name", example = "Mechnikov", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(value = "User email", example = "name@gmail.com", required = true)
    @Pattern(regexp = "([a-zA-Z0-9\\.]{1,30})\\@gmail[\\.]com", message = "Email not valid. Only @gmail.com required")
    @Email
    private String email;

    @ApiModelProperty(value = "User password", example = "Query1224", required = true)
    @Pattern(regexp = "([a-zA-Z0-9\\.]{6,30})", message = "Password not valid")
    private String password;

    @Min(18)
    @Max(70)
    @UserAgeConstraint
    private Integer age;

    @ApiModelProperty(value = "Bank Account of User", required = true)
    private List<BankAccountDto> bankAccounts;

    @ApiModelProperty(value = "User hospital info", required = true)
    private HospitalDto hospitalEntity;
}
