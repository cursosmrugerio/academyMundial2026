package com.academy.kogito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion Spring Boot + Kogito.
 *
 * Kogito genera en tiempo de compilacion los endpoints REST a partir de los
 * artefactos en src/main/resources:
 *   - aprobacionCredito.bpmn2  -> POST /aprobacionCredito
 *   - scoringCredito.dmn       -> POST /scoringCredito
 *
 * IMPORTANTE (dos detalles del arranque con Kogito en Spring Boot):
 *   1) Kogito genera sus beans de infraestructura en el paquete
 *      {@code org.kie.kogito.app} (por ejemplo {@code org.kie.kogito.app.Application},
 *      del que dependen los procesos generados). Como esta clase vive en
 *      {@code com.academy.kogito}, hay que ampliar el component-scan para incluir
 *      {@code org.kie.kogito}; de lo contrario esos beans no se descubren.
 *   2) Esta clase NO se llama "Application" a proposito: Kogito ya genera
 *      {@code org.kie.kogito.app.Application} (nombre de bean "application"); si la
 *      nuestra se llamara igual, habria un conflicto de definicion de bean al
 *      escanear {@code org.kie.kogito}.
 */
@SpringBootApplication(scanBasePackages = {"com.academy.kogito", "org.kie.kogito"})
public class AprobacionCreditoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AprobacionCreditoApplication.class, args);
    }
}
