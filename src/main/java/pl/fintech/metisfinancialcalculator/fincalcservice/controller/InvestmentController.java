package pl.fintech.metisfinancialcalculator.fincalcservice.controller;


import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 406, message = "Parameters contain invalid values")})
    public InvestmentDetailsDTO calculateInvestment(@RequestBody InvestmentParametersDTO parameters){
        return investmentService.calculateInvestment(parameters);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${InvestmentController.investments-get}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public InvestmentDetailsDTO getInvestmentDetails(@ApiParam("Investment id") @RequestParam(value = "id") Long investmentId){
        return investmentService.getInvestment(investmentId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${InvestmentController.investments-post}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public Investment addInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @ApiParam("Portfolio id") @RequestParam(value = "id") Long portfolioId){
        return portfolioService.addInvestment(investmentDTO,portfolioId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${InvestmentController.investments-put}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized"),
            @ApiResponse(code = 406, message = "Parameters contain invalid values")})
    public Investment modifyInvestment(@RequestBody InvestmentDetailsDTO investmentDTO, @ApiParam("Investment id") @RequestParam(value = "id") Long investmentId){
        return investmentService.modifyInvestment(investmentId,investmentDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${InvestmentController.investments-delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public void removeInvestment(@ApiParam("Investment id") @RequestParam(value = "id") Long investmentId){
        investmentService.removeInvestment(investmentId);
    }

}