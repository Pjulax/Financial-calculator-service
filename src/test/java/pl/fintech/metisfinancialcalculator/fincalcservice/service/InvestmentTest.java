package pl.fintech.metisfinancialcalculator.fincalcservice.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.exception.CustomException;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.User;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.InvestmentRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvestmentTest {

    @Mock
    InvestmentRepository investmentRepository;

    @Mock
    PortfolioRepository portfolioRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @InjectMocks
    InvestmentService investmentService;

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldReturnExistingInvestment(){
        Investment investment = new Investment();
        Random random = new Random();
        double systematicDepositValue = (double) random.nextInt(100) + random.nextDouble();
        double roi = (double) random.nextInt(10) + random.nextDouble()/10;
        double initialDeposit = ((double) random.nextInt(1000) + random.nextDouble())/10;
        double duration = (double) random.nextInt(50) + random.nextDouble()/10;
        double frequency = (double) random.nextInt(10) + random.nextDouble()/10;
        double risk = (double) random.nextInt(10) + random.nextDouble()/10;
        String category = "some category";
        String name = "some name";
        investment.setSystematicDepositValue(systematicDepositValue);
        investment.setReturnOfInvestment(roi);
        investment.setInitialDepositValue(initialDeposit);
        investment.setDurationInYears(duration);
        investment.setFrequencyInYears(frequency);
        investment.setRisk(risk);
        investment.setCategory(category);
        investment.setName(name);
        investment.setResult(new Result());
        when(investmentRepository.findById(any())).thenReturn(Optional.of(investment));
        when(portfolioRepository.findByInvestmentsContaining(any())).thenReturn(Optional.of(new Portfolio("some")));
        User user = new User();
        user.setUsername("user");
        when(userRepository.findUserByPortfoliosContaining(any())).thenReturn(Optional.of(user));
        when(userService.whoami()).thenReturn(user);

        InvestmentDetailsDTO investmentDetailsDTO = investmentService.getInvestment(random.nextLong());
        assertEquals(investmentDetailsDTO.getCategory(), category);
        assertEquals(investmentDetailsDTO.getDurationInYears(), duration);
        assertEquals(investmentDetailsDTO.getFrequencyInYears(), frequency);
        assertEquals(investmentDetailsDTO.getInitialDepositValue(), initialDeposit);
        assertEquals(investmentDetailsDTO.getSystematicDepositValue(), systematicDepositValue);
        assertEquals(investmentDetailsDTO.getReturnOfInvestmentPercentage(), roi);
        assertEquals(investmentDetailsDTO.getName(), name);
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonExistingInvestment(){
        Random random = new Random();
        assertThrows(CustomException.class, () -> investmentService.getInvestment(random.nextLong()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonBelongingInvestmentToPortfolio(){
        Random random = new Random();
        when(investmentRepository.findById(any())).thenReturn(Optional.of(new Investment()));
        when(portfolioRepository.findByInvestmentsContaining(any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> investmentService.getInvestment(random.nextLong()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonBelongingInvestmentToUser(){
        Random random = new Random();
        when(investmentRepository.findById(any())).thenReturn(Optional.of(new Investment()));
        when(portfolioRepository.findByInvestmentsContaining(any())).thenReturn(Optional.of(new Portfolio()));
        when(userRepository.findUserByPortfoliosContaining(any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> investmentService.getInvestment(random.nextLong()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonExistingInvestmentOnRemoveOperation(){
        Random random = new Random();
        assertThrows(CustomException.class, () -> investmentService.removeInvestment(random.nextLong()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonBelongingInvestmentToPortfolioOnRemoveOperation(){
        Random random = new Random();
        when(investmentRepository.findById(any())).thenReturn(Optional.of(new Investment()));
        when(portfolioRepository.findByInvestmentsContaining(any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> investmentService.removeInvestment(random.nextLong()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldThrowOnNonBelongingInvestmentToUserOnRemoveOperation(){
        Random random = new Random();
        when(investmentRepository.findById(any())).thenReturn(Optional.of(new Investment()));
        when(portfolioRepository.findByInvestmentsContaining(any())).thenReturn(Optional.of(new Portfolio()));
        when(userRepository.findUserByPortfoliosContaining(any())).thenReturn(Optional.empty());
        assertThrows(CustomException.class, () -> investmentService.removeInvestment(random.nextLong()));
    }



    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCalculateNegativeDuration(){
        InvestmentParametersDTO parametersDTO = new InvestmentParametersDTO();
        Random random = new Random();
        parametersDTO.setSystematicDepositValue((double) random.nextInt(100) + random.nextDouble());
        parametersDTO.setReturnOfInvestment(((double) random.nextInt(10) + random.nextDouble())/10);
        parametersDTO.setInitialDepositValue(((double) random.nextInt(1000) + random.nextDouble())/10);
        parametersDTO.setDurationInYears(((double) random.nextInt(50) + random.nextDouble())/10-100);
        parametersDTO.setFrequencyInYears(((double) random.nextInt(10) + random.nextDouble())/10);
        assertThrows(CustomException.class, () -> investmentService.calculateInvestment(parametersDTO));

    }


    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCalculateNegativeFrequency(){
        InvestmentParametersDTO parametersDTO = new InvestmentParametersDTO();
        Random random = new Random();
        parametersDTO.setSystematicDepositValue((double) random.nextInt(100) + random.nextDouble());
        parametersDTO.setReturnOfInvestment(((double) random.nextInt(10) + random.nextDouble())/10);
        parametersDTO.setInitialDepositValue(((double) random.nextInt(1000) + random.nextDouble())/10);
        parametersDTO.setDurationInYears(((double) random.nextInt(100) + random.nextDouble())/10);
        parametersDTO.setFrequencyInYears(((double) random.nextInt(10) + random.nextDouble())/10-15);
        assertThrows(CustomException.class, () -> investmentService.calculateInvestment(parametersDTO));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCalculateNegativeDeposit(){
        InvestmentParametersDTO parametersDTO = new InvestmentParametersDTO();
        Random random = new Random();
        parametersDTO.setSystematicDepositValue((double) random.nextInt(100) + random.nextDouble() -100);
        parametersDTO.setReturnOfInvestment(((double) random.nextInt(10) + random.nextDouble())/10);
        parametersDTO.setInitialDepositValue(((double) random.nextInt(1000) + random.nextDouble())/10-1000);
        parametersDTO.setDurationInYears(((double) random.nextInt(100) + random.nextDouble())/10);
        parametersDTO.setFrequencyInYears(((double) random.nextInt(10) + random.nextDouble())/10);
        assertThrows(CustomException.class, () -> investmentService.calculateInvestment(parametersDTO));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCalculateNegativeSystematicDeposit(){
        InvestmentParametersDTO parametersDTO = new InvestmentParametersDTO();
        Random random = new Random();
        parametersDTO.setSystematicDepositValue((double) random.nextInt(100) + random.nextDouble() -100);
        parametersDTO.setReturnOfInvestment(((double) random.nextInt(10) + random.nextDouble())/10);
        parametersDTO.setInitialDepositValue(((double) random.nextInt(1000) + random.nextDouble())/10);
        parametersDTO.setDurationInYears(((double) random.nextInt(100) + random.nextDouble())/10);
        parametersDTO.setFrequencyInYears(((double) random.nextInt(10) + random.nextDouble())/10);
        assertThrows(CustomException.class, () -> investmentService.calculateInvestment(parametersDTO));
    }

}
