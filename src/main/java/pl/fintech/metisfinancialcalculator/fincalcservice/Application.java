package pl.fintech.metisfinancialcalculator.fincalcservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.Role;
import pl.fintech.metisfinancialcalculator.fincalcservice.model.User;
import pl.fintech.metisfinancialcalculator.fincalcservice.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories
public class Application implements CommandLineRunner { // implemented interface is to delete after auth done

    // user service is to delete after auth done
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    //to delete after authentication and authorization will be tested to be done
    @Override
    public void run(String... params) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

        userService.signup(admin);

        User client = new User();
        client.setUsername("client");
        client.setPassword("client");
        client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

        userService.signup(client);
    }
}
