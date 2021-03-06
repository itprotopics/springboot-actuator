# Demostración de Spring boot Actuator


- [Demostración de Spring boot Actuator](#demostración-de-spring-boot-actuator)
  - [Módulo de Spring Boot Actuator](#módulo-de-spring-boot-actuator)
  - [Generar el proyecto en start.spring.io](#generar-el-proyecto-en-startspringio)
  - [Dependencia de maven](#dependencia-de-maven)
  - [Endpoints principales](#endpoints-principales)
  - [Seguridad](#seguridad)
  - [Habilitar y cambiar la ruta de los endpoints](#habilitar-y-cambiar-la-ruta-de-los-endpoints)
  - [Mejorar Actuator con Endpoints personalizados](#mejorar-actuator-con-endpoints-personalizados)
    - [Crear la clase con la lógica del Actuator](#crear-la-clase-con-la-lógica-del-actuator)
    - [Configuración del Bean](#configuración-del-bean)
    - [Incluir el endpoint en la configuración](#incluir-el-endpoint-en-la-configuración)
    - [Incluir un método en la clase actuator](#incluir-un-método-en-la-clase-actuator)
  - [Incluir información de Git](#incluir-información-de-git)
    - [Agregar el plugin de git al pom del proyecto](#agregar-el-plugin-de-git-al-pom-del-proyecto)
    - [Configurar el endpoint info](#configurar-el-endpoint-info)
  - [Referencias:](#referencias)
  

## Módulo de Spring Boot Actuator

Actuator es un módulo de Spring Boot que permite monitorear y administrar aplicaciones en ambientes productivos sin necesidad de generar código específico para estas actividades. La información se expone vía REST en enpoints URLs.

## Generar el proyecto en [start.spring.io](https://start.spring.io/)

![Alt](/images/start-spring-io.png "Title")

## Dependencia de maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## Endpoints principales

| Endpoint | Usage |
| ----------- | ----------- |  
| /auditevents | Returns all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied |
| /beans | Returns a complete list of all the Spring beans in your application. |
| /mappings | Displays a collated list of all @RequestMapping paths.. |
| /env | Returns list of properties in current environment |
| /health | Returns application health information. |
| /caches | It exposes available caches. |
| /loggers | The configuration of loggers in the application.. |
| /shutdown | Lets the application be gracefully shutdown. Disabled by default. |
| /metrics | It shows several useful metrics information like JVM memory used, system CPU usage, open files, and much more. |



## Seguridad


```java
@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {
	 
	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http
         .csrf().disable()
         .authorizeRequests().antMatchers("/msuser-manager/actuator").permitAll()
		 .antMatchers("/msuser-manager/v1/**").authenticated()
         .and()
         .httpBasic();
; 
    }

```

## Habilitar y cambiar la ruta de los endpoints

```yaml
management:
  endpoints:
    web:
      base-path: /msuser-manager/actuator
      exposure:
        include: health, info, env, beans, metrics, threaddump

```

## Mejorar Actuator con Endpoints personalizados

En esta sección, veremos cómo podemos mejorar fácilmente Spring Boot Actuator agregando nuestros propios endpoints personalizados.

### Crear la clase con la lógica del Actuator

```java
@Endpoint(id = "vigencia")
public class VigenciaActuator {
    
    @ReadOperation
    public String vigencia() {
        return "IS UP";
    }
}
```

Se debe usar la anotación @Endpoint(id = "xxxx") para que Spring identifique a esta clase como parte de Actuator

### Configuración del Bean

En una clase de configuración se debe instanciar o generar un Bean de Spring para la clase anterior.

```java
@Bean
public VigenciaActuator vigenciaActuator() {
    return new VigenciaActuator();
}
```

### Incluir el endpoint en la configuración

```
management.endpoints.web.exposure.include=vigencia
```

### Incluir un método en la clase actuator

```java
    @ReadOperation
    public String vigencia() {
        return "IS UP";
    }
```

Para exponer un endpoint de Spring Actuator a una solicitud HTTP GET, necesitaremos anotar nuestro método con la anotación @ReadOperation.

## Incluir información de Git

Una característica útil del endpoint  *info* es su capacidad para publicar información sobre el estado del repositorio de código fuente de git cuando se creó el proyecto. Si hay disponible un bean GitProperties, se exponen las propiedades git.branch, git.commit.id y git.commit.time.

### Agregar el plugin de git al pom del proyecto

```xml
<build>
	<plugins>
		<plugin>
			<groupId>pl.project13.maven</groupId>
			<artifactId>git-commit-id-plugin</artifactId>
		</plugin>
	</plugins>
</build>
```

### Configurar el endpoint info

```yaml
management:
  info:
    git:
      mode: full
```

## Referencias:

- [Spring Boot Actuator](https://howtodoinjava.com/spring-boot/actuator-endpoints-example/)
- [Enhancing Spring Boot Actuator with Custom Endpoints](https://medium.com/@jamiekee94/enhancing-spring-boot-actuator-with-custom-endpoints-d6343fbaa1ca)
- [Custom Endpoint in Spring Boot Actuator](https://www.javadevjournal.com/spring-boot/spring-boot-actuator-custom-endpoint/)
- [Part V. Spring Boot Actuator: Production-ready features](https://docs.spring.io/spring-boot/docs/2.1.x/reference/html/production-ready-endpoints.html)