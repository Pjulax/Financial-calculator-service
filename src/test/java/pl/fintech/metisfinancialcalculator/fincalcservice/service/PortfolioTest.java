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
import pl.fintech.metisfinancialcalculator.fincalcservice.repository.PortfolioRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PortfolioTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldReturnPortfoliosNames(){
        Portfolio portfolio1 = new Portfolio("First");
        Portfolio portfolio2 = new Portfolio("Second");

        when(portfolioRepository.findAll()).thenReturn(List.of(portfolio1, portfolio2));

        List<PortfolioNameDTO> portfolioNameDTOS = portfolioService.getAllPortfoliosNames();

        assertEquals( portfolioNameDTOS.get(0).getName(), "First");
        assertEquals( portfolioNameDTOS.get(1).getName(), "Second");

    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowToCreatePortfolioWithTheSameName(){
        when(portfolioRepository.existsByName(any())).thenReturn(true);
        assertThrows(CustomException.class, () -> portfolioService.createPortfolio(any()));
    }

    @Test
    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    public void shouldNotAllowUserToCreateBlankPortfolio(){
        assertThrows(CustomException.class, () -> portfolioService.createPortfolio(""));
    }
}
