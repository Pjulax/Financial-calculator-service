package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.PortfolioNameDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Portfolio;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        this.mockMvc.perform(get(PORTFOLIO +"/hello2")).andDo(print()).andExpect(status().isOk())
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
        this.mockMvc.perform(get(PORTFOLIO +"/names")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(names.toString()));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnNoNames() throws Exception {
        when(portfolioService.getAllPortfoliosNames()).thenReturn(List.of());
        String names = List.of().toString();
        this.mockMvc.perform(get(PORTFOLIO +"/names")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(names));
    }


    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnModifiedPortfolio() throws Exception {
        Portfolio portfolio = new Portfolio("modifiedname");
        when(portfolioService.modifyPortfolio(1L, "modifiedname")).thenReturn(portfolio);
        String portfolioJSON = new JSONObject().put("id", portfolio.getId())
                .put("name", portfolio.getName())
                .put("investments",portfolio.getInvestments())
                .put("result", portfolio.getResult()).toString();
        this.mockMvc.perform(put(PORTFOLIO+"?new-name=modifiedname&id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(portfolioJSON));
    }
}
