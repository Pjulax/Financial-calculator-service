package pl.fintech.metisfinancialcalculator.fincalcservice.configuration;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .securitySchemes(Lists.newArrayList(apiKey(),apiKeyJWT()))
                .securityContexts(Collections.singletonList(securityContext()))
                .tags(new Tag("categories","Operations about categories"),
                        new Tag("investments","Operations about investments"),
                        new Tag("portfolios","Operations about portfolios"),
                        new Tag("users", "Operations about users"))
                .genericModelSubstitutes(Optional.class);

    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Metis Investment Calculator Api")
                .description("With this application you can calculate return of investments. You are able to group them into portfolios, describe investment category and set risk.")
                .version("0.4.6 DEMO")
                //.license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")//
                //.contact(new Contact("DevMountain - Fintech Challenge", null, null))//
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private ApiKey apiKeyJWT() { return new ApiKey("AuthorizationJwt", "AuthorizationJwt", "header"); };


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes), new SecurityReference("AuthorizationJwt", authorizationScopes));
    }

}
