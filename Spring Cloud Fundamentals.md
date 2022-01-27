### Spring Cloud Fundamentals
Curso desenvolvido pela plataforma Pluralsight iniciado no dia 24 de Janeiro.

#### <b>Minhas Anotações</b>
- Existem outras alternativas ao Spring Cloud Netflix (o mais conhecido).
- Adicionar Spring Actuator
- Adicionar o Spring Security no Servidor(configuração ou discovery)

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

<br>

### <b>Criando um Service Discovery Simples:</b>
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
<br>

### <b>Criando um Service App:</b>

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
<br>

### <b>Criando um Client:</b>
Para criar um Client basta fazer o mesmo processo do <i>Service</i>, porém com o registro desligado. Porquando o client não precisa se registrar, mas deve "descobrir" todos os serviços existentes.
```
eureka:
    client:
        register-with-eureka: false
```
<br>

#### <b>Formas de consumir um Serviço:</b>
1) Posso injetar o Eureka Client:
```
@Inject
private EurekaClient client;

InstanceInfo instanceInfo = 
    client.getNextServerFromEureka("nome", false);

String baseUrl = instanceInfo.getHomePageUrl();

// Estou pegando o URL raíz do serviço. Posso, por exemplo, usar um RestTemplate para consumí-lo.
```
2) Posso injetar o Spring Discovery Client:
```
@Inject
private DiscoveryClient client;

List<ServiceInstance> instances = 
    client.getInstances("nome");
```
<br>

### <b>Áreas de Configuração do Eureka:</b>
1) eureka.server.*
<br>
Todas as configurações relacionadas ao servidor.

2) eureka.client.*
<br>
Todas as configurações relacionadas ao cliente.

3) eureka.instance.*
<br>
Todas as configurações relacionadas à instância.
<br>

### <b>Saúde das Aplicações no Eureka:</b>
- O Eureka envia sinais a cada serviço ou cliente, a fim de saber se estão bem.
- Ele envia esses sinais a cada 30 segundos.
- Se o serviço não responder por mais de 90 segundos, o Eureka remove esse serviço.
- Pode ser configurado através da propriedade: eureka.client.heathcheck.enabled.

<!-- Final do Módulo -->

<br>

### <b>Configuration in a Distributed System</b>
Posso gerenciar as configurações de várias formas:
1) Spring Cloud Consul - ele também serve para esse fim. 🔸
2) Spring Cloud Zookeeper - ele também serve para esse fim. 🔸
3) Spring Cloud Config - foi projetado para esse fim. ✌️

<b>Spring Cloud Config:</b>

- Funciona no formato HTTP REST
- Retorna diferentes formatos de arquivos de configuração: JSON*, Properties e YAML.
- Integração com vários Backend stores: Git*, SVN e FileSystem.

### <b>Criando um Servidor de Configuração</b>
1) Adicione a dependência do Spring Cloud Config Server:
```
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
```
2) Criar um repositório local ou remoto contendo os arquivos de configuração.
3) Configure a Aplicação:
```
spring:
    cloud:
        config:
            server:
                git:
                    uri: link do repositório(git)
```
<br>

### <b>Criando um Cliente que Consome o Servidor de Configurações</b>
1) Adicionar a depêndencia principal:
```
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
```
2) Para encontrar o servidor de configurações eu preciso da dependência:
```
    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-bootstrap</artifactId>
	</dependency>
```
3) Criar uma configuração para o bootstrap:
- Arquivo: bootstrap.yml
```
spring:
    application:
        name: config-client-app
    cloud:
        config:
            discovery:
                enabled: true
                serviceId: configuration-server // Importante!
```
- Também precisa do URI do servidor de configurações:
```
eureka:
    client:
        server-url:
            defaultZone: http://localhost:8761/eureka
```

### <b>Atualizando as Configurações</b>
1) Faça as alterações no arquivo .git
2) Faça o push para o servidor remoto
3) Faça uma requisição do tipo POST para o servidor de configuração:
```
http://localhost:porta-do-servidor/refresh
```
Atualize o valor de uma proprieda "@Value", colocando a seguinte anotação na classe:
```
@RefreshScope
```
<b>Eu posso criptografar configurações. Estudar mais adiante...</b> 🔔<br>
Por si só, a criptografia não garante a segurança da aplicação. Será, portanto, necessário ter o Spring Security protegendo os endpoints.

<br>

### <b>Mapping Services Using Intelligent Routing</b>
Um Gateway oferece roteamento inteligente, monitoração e filtros.
<br>

Existem duas alternativas:
- Netflix Zuul ("depreciada")
- Spring Cloud Gateway (Atual)