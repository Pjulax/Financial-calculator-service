package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.exception.CustomException;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.User;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PortfolioTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldReturnPortfoliosNames(){
        Portfolio portfolio1 = new Portfolio("First");
        Portfolio portfolio2 = new Portfolio("Second");

        User user = new User();
        user.setUsername("user");
        user.setPassword("name");
        user.setPortfolios(List.of(portfolio1, portfolio2));
        when(userService.whoami()).thenReturn(user);
        List<PortfolioNameDTO> portfolioNameDTOS = portfolioService.getAllPortfoliosNames();
        assertEquals( portfolioNameDTOS.get(0).getName(), "First");
        assertEquals( portfolioNameDTOS.get(1).getName(), "Second");
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCreatePortfolioWithTheSameName(){
        when(portfolioRepository.existsByName("First")).thenReturn(true);
        assertThrows(CustomException.class, () -> portfolioService.createPortfolio("First"));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowUserToCreateBlankPortfolio(){
        assertThrows(CustomException.class, () -> portfolioService.createPortfolio(""));
    }


    //REPO TEST
    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldPortfolioNotExistAfterRemoveOperation() {
        //TODO
    }

    //REPO TEST
    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldModifyPortfolio() {
        //TODO
    }

    //REPO TEST
    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldAddInvestment() {
        //TODO
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotRemoveNonExistingPortfolio() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("name");
        when(userService.whoami()).thenReturn(user);
        Optional<Portfolio> opt = Optional.empty();
        when(portfolioRepository.findById(anyLong())).thenReturn(opt);

        assertThrows(CustomException.class, () -> portfolioService.removePortfolio(4L));
    }



    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotModifyNonExistingPortfolio() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("name");
        when(userService.whoami()).thenReturn(user);
        Optional<Portfolio> opt = Optional.empty();
        when(portfolioRepository.findById(any())).thenReturn(opt);
        Random random = new Random();
        assertThrows(CustomException.class, () -> portfolioService.modifyPortfolio((long) random.nextInt(241241),"some new portfolio"));
    }





    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldReturnAllPortfolioInvestmentDetailsForSeparateInvestments() {
        Portfolio portfolio1 = new Portfolio("First");
        Portfolio portfolio2 = new Portfolio("Second");

        Result result = new Result();
        result.setGraphPointValues(List.of());
        result.setRateOfReturnValue(BigDecimal.valueOf(50));
        result.setRateOfReturnPercentage(0.05d);

        Investment investment = new Investment();
        investment.setDurationInYears(2d);
        investment.setReturnOfInvestment(0.05d);
        investment.setInitialDepositValue(100d);
        investment.setCategory("example category");
        investment.setName("example name");
        investment.setFrequencyInYears(0.25d);
        investment.setRisk(0.05d);
        investment.setSystematicDepositValue(50d);
        investment.setResult(result);


        Investment investment2 = new Investment();
        investment2.setDurationInYears(2d);
        investment2.setReturnOfInvestment(0.05d);
        investment2.setInitialDepositValue(100d);
        investment2.setCategory("example category");
        investment2.setName("example name");
        investment2.setFrequencyInYears(0.25d);
        investment2.setSystematicDepositValue(50d);
        investment2.setRisk(0.05d);
        investment2.setResult(result);

        Investment investment3 = new Investment();
        investment3.setDurationInYears(2d);
        investment3.setReturnOfInvestment(0.05d);
        investment3.setInitialDepositValue(100d);
        investment3.setCategory("example category");
        investment3.setName("example name");
        investment3.setFrequencyInYears(0.25d);
        investment3.setSystematicDepositValue(50d);
        investment3.setRisk(0.05d);
        investment3.setResult(result);

        portfolio1.setInvestments(List.of(investment, investment2));
        portfolio2.setInvestments(List.of(investment2, investment3));

        User user = new User();
        user.setUsername("user");
        user.setPassword("name");
        user.setPortfolios(List.of(portfolio1, portfolio2));
        when(userService.whoami()).thenReturn(user);
        PortfolioDetailsDTO portfolioDetailsDTO = portfolioService.getPortfolioAllInvestmentsDetails();
        assertEquals(portfolioDetailsDTO.getInvestments().get(0).getName(), investment.getName());
        assertEquals(portfolioDetailsDTO.getInvestments().get(1).getName(), investment2.getName());
        assertEquals(portfolioDetailsDTO.getInvestments().get(2).getName(), investment3.getName());

    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldReturnPortfolioDetails() {

    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotGetDetailsOfNonExistingPortfolio() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("name");
        user.setPortfolios(List.of());
        when(userService.whoami()).thenReturn(user);
        assertThrows(CustomException.class, () -> portfolioService.getPortfolioAllInvestmentsDetails());
    }


}
