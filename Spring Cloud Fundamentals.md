### Spring Cloud Fundamentals
Curso desenvolvido pela plataforma Pluralsight iniciado no dia 24 de Janeiro.

#### <b>Minhas Anotações</b>
- Existem outras alternativas ao Spring Cloud Netflix (o mais conhecido).
- Adicionar Spring Actuator

#### Finding Services Using Service Discovery
Discover Services Alternatives:
- Spring Cloud Consul
- Spring Cloud Zookeeper
- Spring Cloud Netflix

Pilares do Spring Cloud Netflix:
- Eureka Server
- Eureka Client

Pilares da Arquitetura de Microserviços:
1) Discovery Server Application
2) Service Application
3) Client Application

Criando um Service Discovery Simples:
1) Adicione as dependências do Spring Cloud
```
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
2) Dê um nome ao servidor
```
spring:
    application:
        name: dicovery-server
```
3) Mostre ao Spring que é uma aplicação Eureka server
```
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServerApplication.class, args);
  }

}
```
