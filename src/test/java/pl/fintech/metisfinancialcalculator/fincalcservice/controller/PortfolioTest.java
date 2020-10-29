package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentInPortfolioDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureJdbc
@AutoConfigureMockMvc
public class PortfolioTest {

    @Autowired
    private PortfolioController portfolioController;

    private final String PORTFOLIO = "/portfolios";

    @MockBean
    private PortfolioService portfolioService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(portfolioController).isNotNull();
    }

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnHello() throws Exception {
        this.mockMvc.perform(get(PORTFOLIO + "/hello2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("hello")));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnNames() throws Exception {
        PortfolioNameDTO portfolioNameDTO1 = new PortfolioNameDTO();
        portfolioNameDTO1.setId(1L);
        portfolioNameDTO1.setName("Some name for first");

        PortfolioNameDTO portfolioNameDTO2 = new PortfolioNameDTO();
        portfolioNameDTO2.setId(2L);
        portfolioNameDTO2.setName("Some name for second");

        when(portfolioService.getAllPortfoliosNames()).thenReturn(List.of(portfolioNameDTO1, portfolioNameDTO2));
        List<String> names = List.of(new JSONObject().put("id", 1).put("name", "Some name for first").toString(),
                new JSONObject().put("id", 2).put("name", "Some name for second").toString());
        this.mockMvc.perform(get(PORTFOLIO + "/names")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(names.toString()));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnNoNames() throws Exception {
        when(portfolioService.getAllPortfoliosNames()).thenReturn(List.of());
        String names = List.of().toString();
        this.mockMvc.perform(get(PORTFOLIO + "/names")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(names));
    }


    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnModifiedPortfolio() throws Exception {
        Portfolio portfolio = new Portfolio("modifiedname");
        when(portfolioService.modifyPortfolio(1L, "modifiedname")).thenReturn(portfolio);
        ObjectMapper mapper = new ObjectMapper();
        String portfolioJSON = mapper.writeValueAsString(portfolio);
        this.mockMvc.perform(put(PORTFOLIO + "?new-name=modifiedname&id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(portfolioJSON));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnAllInvestmentsDetails() throws Exception {
        PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();

        Random random = new Random();
        List<GraphPoint> graphPointsValue = new ArrayList<>();
        graphPointsValue.add(new GraphPoint(random.nextDouble(), random.nextDouble()));
        List<InvestmentInPortfolioDTO> investments = new ArrayList<>();
        investments.add(new InvestmentInPortfolioDTO());

        portfolioDetailsDTO.setRateOfReturnValue(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setTotalInvestedCash(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setRateOfReturnPercentage(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setGraphPointsValue(graphPointsValue);
        portfolioDetailsDTO.setInvestments(investments);
        when(portfolioService.getPortfolioAllInvestmentsDetails()).thenReturn(portfolioDetailsDTO);

        ObjectMapper mapper = new ObjectMapper();
        String portfolioDetailsJSON = mapper.writeValueAsString(portfolioDetailsDTO);

        this.mockMvc.perform(get(PORTFOLIO + "/all-investments-details")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(portfolioDetailsJSON));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnPortfolio() throws Exception {
        PortfolioDetailsDTO portfolioDetailsDTO = new PortfolioDetailsDTO();

        Random random = new Random();
        List<GraphPoint> graphPointsValue = new ArrayList<>();
        graphPointsValue.add(new GraphPoint(random.nextDouble(), random.nextDouble()));
        List<InvestmentInPortfolioDTO> investments = new ArrayList<>();
        investments.add(new InvestmentInPortfolioDTO());

        portfolioDetailsDTO.setRateOfReturnValue(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setTotalInvestedCash(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setRateOfReturnPercentage(BigDecimal.valueOf(random.nextDouble()));
        portfolioDetailsDTO.setGraphPointsValue(graphPointsValue);
        portfolioDetailsDTO.setInvestments(investments);

        when(portfolioService.getPortfolioDetails(1L)).thenReturn(portfolioDetailsDTO);

        ObjectMapper mapper = new ObjectMapper();
        String portfolioDetailsJSON = mapper.writeValueAsString(portfolioDetailsDTO);

        this.mockMvc.perform(get(PORTFOLIO + "?id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(portfolioDetailsJSON));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldCreatePortfolio() throws Exception {
        Portfolio portfolio = new Portfolio();

        portfolio.setInvestments(List.of(new Investment()));
        portfolio.setId(1L);
        portfolio.setName("Name");
        portfolio.setResult(new Result());

        when(portfolioService.createPortfolio(portfolio.getName())).thenReturn(portfolio);

        ObjectMapper mapper = new ObjectMapper();
        String portfolioJSON = mapper.writeValueAsString(portfolio);

        String path = String.format("?name=%s", portfolio.getName());
        this.mockMvc.perform(post(PORTFOLIO + path)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(portfolioJSON));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldRemovePortfolio() throws Exception {
        Portfolio portfolio = new Portfolio();

        portfolio.setInvestments(List.of(new Investment()));
        portfolio.setId(1L);
        portfolio.setName("Name");
        portfolio.setResult(new Result());

        String path = String.format("?id=%s", portfolio.getId());
        this.mockMvc.perform(delete(PORTFOLIO + path)).andDo(print()).andExpect(status().isOk());
    }
}
