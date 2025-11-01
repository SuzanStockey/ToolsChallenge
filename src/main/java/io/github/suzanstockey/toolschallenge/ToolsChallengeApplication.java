package io.github.suzanstockey.toolschallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Tools Challenge - API de Pagamentos",
                version = "1.0.0",
                description = "API REST para simulação de transações de pagamento, estorno e consulta, como solução para o Tools Java Challenge [C].",
                contact = @Contact(
                        name = "Suzan Stockey Pereira",
                        url = "https://github.com/SuzanStockey"
                )
        )
)
@SpringBootApplication
public class ToolsChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsChallengeApplication.class, args);
    }

}
