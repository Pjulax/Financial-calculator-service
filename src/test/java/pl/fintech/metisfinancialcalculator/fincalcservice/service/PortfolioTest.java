package pl.fintech.metisfinancialcalculator.fincalcservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.exception.CustomException;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.User;
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
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
}
