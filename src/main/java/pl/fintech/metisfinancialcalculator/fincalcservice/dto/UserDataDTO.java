package pl.fintech.metisfinancialcalculator.fincalcservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDataDTO {

    @ApiModelProperty(position = 0)
    private String username;
    @ApiModelProperty(position = 1)
    private String password;
    @ApiModelProperty(position = 2)
    List<Role> roles;

}
