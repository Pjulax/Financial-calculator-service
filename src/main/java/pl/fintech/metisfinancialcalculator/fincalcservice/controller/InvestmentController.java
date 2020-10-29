package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.InvestmentService;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

@RestController
@Api(tags = "investments")
@RequestMapping("/investments")
@AllArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;
    private final PortfolioService portfolioService;

    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "${InvestmentController.calculate}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){
        return investmentService.calculateInvestment(parameters);
    }

    @GetMapping
    @ApiOperation(value = "${InvestmentController.investments-get}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public InvestmentDetailsDTO getInvestmentDetails(@RequestParam(value = "id") Long investmentId){
        return investmentService.getInvestment(investmentId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "${InvestmentController.investments-post}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public Investment addInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long portfolioId){
        return portfolioService.addInvestment(investmentDTO,portfolioId);
    }

    @PutMapping
    @ApiOperation(value = "${InvestmentController.investments-put}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public Investment modifyInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @RequestParam(value = "id") Long investmentId){
        return investmentService.modifyInvestment(investmentId,investmentDTO);
    }

    @DeleteMapping
    @ApiOperation(value = "${InvestmentController.investments-delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 404, message = "The user doesn't exist")})
    public void removeInvestment(@RequestParam(value = "id") Long investmentId){
        investmentService.removeInvestment(investmentId);
    }

}
