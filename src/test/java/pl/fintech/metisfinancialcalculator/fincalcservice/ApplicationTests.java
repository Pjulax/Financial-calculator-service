package pl.fintech.metisfinancialcalculator.fincalcservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.fintech.metisfinancialcalculator.fincalcservice.controller.PortfolioController;
import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureJdbc
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private PortfolioController portfolioController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(portfolioController).isNotNull();
    }

    @Autowired
    private MockMvc mockMvc;




    @WithMockUser(username = "user", password = "name", roles = {"ADMIN", "CLIENT"})
    @Test
    public void shouldReturnHello() throws Exception {
        this.mockMvc.perform(get("/portfolios/hello2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("hello")));
    }

}
