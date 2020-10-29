package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

@RestController
@Api(tags = "categories")
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final PortfolioService portfolioService;

    @GetMapping
    @ApiOperation(value = "${CategoryController.categories-get}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public String[] getCategories(){
        return portfolioService.getCategories();
    }
}
