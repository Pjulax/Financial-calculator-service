package pl.fintech.metisfinancialcalculator.fincalcservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentDetailsDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.dto.InvestmentParametersDTO;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.XDateType;
import pl.fintech.metisfinancialcalculator.fincalcservice.enums.YValueType;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.GraphPoint;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Investment;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Result;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.InvestmentService;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.PortfolioService;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureJdbc
@AutoConfigureMockMvc
class InvestmentTest {

    @Autowired
    private InvestmentController investmentController;

    private final String INVESTMENTS = "/investments";

    @MockBean
    private InvestmentService investmentService;

    @MockBean
    private PortfolioService portfolioService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(investmentController).isNotNull();
    }

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    void shouldCalculateInvestment() throws Exception {
        InvestmentParametersDTO parametersDTO = new InvestmentParametersDTO();
        parametersDTO.setStartDate("");
        parametersDTO.setInitialDepositValue(4.5);
        parametersDTO.setSystematicDepositValue(4.6);
        parametersDTO.setFrequencyInYears(4.4);
        parametersDTO.setDurationInYears(4.1);
        parametersDTO.setReturnOfInvestment(1.5);

        InvestmentDetailsDTO detailsDTO = new InvestmentDetailsDTO();
        detailsDTO.setName("Name");
        detailsDTO.setCategory("Category");
        detailsDTO.setRisk(4.5);
        detailsDTO.setInitialDepositValue(4.6);
        detailsDTO.setSystematicDepositValue(4.7);
        detailsDTO.setFrequencyInYears(2.3);
        detailsDTO.setDurationInYears(1.2);
        detailsDTO.setReturnOfInvestmentPercentage(1.3);
        detailsDTO.setRateOfReturnValue(BigDecimal.valueOf(1.4));
        detailsDTO.setRateOfReturnPercentage(1.5);
        detailsDTO.setGraphPointsValue(List.of(new GraphPoint()));
        detailsDTO.setXAxisDataType(XDateType.YEAR);
        detailsDTO.setYAxisDataType(YValueType.POUNDS);

        when(investmentService.calculateInvestment(parametersDTO)).thenReturn(detailsDTO);

        ObjectMapper mapper = new ObjectMapper();
        String parametersJSON = mapper.writeValueAsString(parametersDTO);

        this.mockMvc.perform(post(INVESTMENTS + "/calculate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(parametersJSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    void shouldGetInvestmentDetails() throws Exception {
        InvestmentDetailsDTO detailsDTO = new InvestmentDetailsDTO();
        detailsDTO.setName("Name");
        detailsDTO.setCategory("Category");
        detailsDTO.setRisk(4.5);
        detailsDTO.setInitialDepositValue(4.6);
        detailsDTO.setSystematicDepositValue(4.7);
        detailsDTO.setFrequencyInYears(2.3);
        detailsDTO.setDurationInYears(1.2);
        detailsDTO.setReturnOfInvestmentPercentage(1.3);
        detailsDTO.setRateOfReturnValue(BigDecimal.valueOf(1.4));
        detailsDTO.setRateOfReturnPercentage(1.5);
        detailsDTO.setGraphPointsValue(List.of(new GraphPoint()));
        detailsDTO.setXAxisDataType(XDateType.YEAR);
        detailsDTO.setYAxisDataType(YValueType.POUNDS);

        when(investmentService.getInvestment(1L)).thenReturn(detailsDTO);
        ObjectMapper mapper = new ObjectMapper();
        String detailsJSON = mapper.writeValueAsString(detailsDTO);

        this.mockMvc.perform(get(INVESTMENTS + "?id=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(detailsJSON));
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    void shouldAddInvestment() throws Exception {
        InvestmentDetailsDTO detailsDTO = new InvestmentDetailsDTO();
        detailsDTO.setName("Name");
        detailsDTO.setCategory("Category");
        detailsDTO.setRisk(4.5);
        detailsDTO.setInitialDepositValue(4.6);
        detailsDTO.setSystematicDepositValue(4.7);
        detailsDTO.setFrequencyInYears(2.3);
        detailsDTO.setDurationInYears(1.2);
        detailsDTO.setReturnOfInvestmentPercentage(1.3);
        detailsDTO.setRateOfReturnValue(BigDecimal.valueOf(1.4));
        detailsDTO.setRateOfReturnPercentage(1.5);
        detailsDTO.setGraphPointsValue(List.of(new GraphPoint()));
        detailsDTO.setXAxisDataType(XDateType.YEAR);
        detailsDTO.setYAxisDataType(YValueType.POUNDS);

        Investment result = new Investment();
        result.setId(1L);
        result.setName("Name");
        result.setCategory("Category");
        result.setRisk(2.1);
        result.setInitialDepositValue(6.4);
        result.setSystematicDepositValue(4.1);
        result.setFrequencyInYears(1.3);
        result.setReturnOfInvestment(4.2);
        result.setDurationInYears(4.5);
        result.setResult(new Result());

        when(portfolioService.addInvestment(detailsDTO, result.getId())).thenReturn(result);

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post(INVESTMENTS + "?id=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(detailsDTO))
        )
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    void shouldModifyInvestment() throws Exception{
        InvestmentDetailsDTO detailsDTO = new InvestmentDetailsDTO();
        detailsDTO.setName("Name");
        detailsDTO.setCategory("Category");
        detailsDTO.setRisk(4.5);
        detailsDTO.setInitialDepositValue(4.6);
        detailsDTO.setSystematicDepositValue(4.7);
        detailsDTO.setFrequencyInYears(2.3);
        detailsDTO.setDurationInYears(1.2);
        detailsDTO.setReturnOfInvestmentPercentage(1.3);
        detailsDTO.setRateOfReturnValue(BigDecimal.valueOf(1.4));
        detailsDTO.setRateOfReturnPercentage(1.5);
        detailsDTO.setGraphPointsValue(List.of(new GraphPoint()));
        detailsDTO.setXAxisDataType(XDateType.YEAR);
        detailsDTO.setYAxisDataType(YValueType.POUNDS);

        Investment result = new Investment();
        result.setId(1L);
        result.setName("Name");
        result.setCategory("Category");
        result.setRisk(2.1);
        result.setInitialDepositValue(6.4);
        result.setSystematicDepositValue(4.1);
        result.setFrequencyInYears(1.3);
        result.setReturnOfInvestment(4.2);
        result.setDurationInYears(4.5);
        result.setResult(new Result());

        when(investmentService.modifyInvestment(result.getId(), detailsDTO)).thenReturn(result);

        ObjectMapper mapper = new ObjectMapper();
        String resultJSON = mapper.writeValueAsString(result);

        this.mockMvc.perform(put(INVESTMENTS + "?id=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(detailsDTO))
        )
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    void shouldRemoveInvestment() throws Exception{
        this.mockMvc.perform(delete(INVESTMENTS + "?id=1")).andExpect(status().isOk());
    }
}