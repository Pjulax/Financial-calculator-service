package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.List;

@RestController
@Api(tags = "portfolios")
@RequestMapping("/portfolios")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping(path = "/hello")
    @ApiOperation(value = "${PortfolioController.hello}")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Access denied")})
    public String hello(){
        return "hello";
    }

    @GetMapping(path = "/hello2")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.hello2}")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied")})
    public String hello2(){
        return "hello";
    }

    @GetMapping(path = "/names")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.names}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public List<PortfolioNameDTO> getAllPortfoliosNames(){
        return portfolioService.getAllPortfoliosNames();
    }

    @GetMapping(path = "/all-investments-details")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.all-investments-details}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong or tried to divide by zero"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public PortfolioDetailsDTO getPortfolioAllInvestmentsDetails(){
        return portfolioService.getPortfolioAllInvestmentsDetails();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.portfolios-get}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public PortfolioDetailsDTO getPortfolio(@ApiParam("Portfolio Id") @RequestParam(value = "id") Long portfolioId){
        return portfolioService.getPortfolioDetails(portfolioId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.portfolios-post}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized"),
            @ApiResponse(code = 409, message = "Portfolio already exists or provided name is empty")})
    public Portfolio createPortfolio(@ApiParam("New portfolio name") @RequestParam(value = "name") String name){
        return portfolioService.createPortfolio(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.portfolios-put}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public Portfolio modifyPortfolio(@ApiParam("New name for portfolio") @RequestParam(value="new-name") String name, @ApiParam("Modified portfolio Id") @RequestParam(value = "id") Long portfolioId){
        return portfolioService.modifyPortfolio(portfolioId, name);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${PortfolioController.portfolios-delete}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Unauthorized access"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The resource can't be found or access is unauthorized")})
    public void removePortfolio(@ApiParam("Removed portfolio Id") @RequestParam(value = "id") Long portfolioId){
        portfolioService.removePortfolio(portfolioId);
    }

}
