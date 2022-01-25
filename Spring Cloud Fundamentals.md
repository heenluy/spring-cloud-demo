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

Criando um <b>Service Discovery</b> Simples:
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
4) Desabilitar o registro com outros discoverys - a não ser que existam outros servidores de descoberta(discovery servers):
```
eureka:
    client:
        register-with-eureka: false
```
5) Desabilitar a obtenção de dados de outro(s) discovery(s):
```
fetch-registry: false
```
6) Informar a porta do servidor:
```
server:
    port: 8761
```

Criando um <b>Service App</b>:

1) Criar módulo ou app com a dependêcia:
```
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
2) Indicar ao Server que é um cliente:
```
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
```
2) Mostrar para o app aonde está o enderço do discovery server:
```
eureka:
    client:
        service-url:
            defaultZone: http://localhost:8761/eureka
```