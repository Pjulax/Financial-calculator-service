package pl.fintech.metisfinancialcalculator.fincalcservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class PortfolioTest {

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
}
