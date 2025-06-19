# Ticketo

## Información del Proyecto

**Universidad Nacional de Lanús**  
**Carrera:** Licenciatura en Sistemas  
**Materia:** Orientación a Objetos 2  
**Grupo:** N° 10

**Equipo Docente:**  
- Mg. Lic. María Alejandra Vranic  
- Esp. Lic. Gustavo Siciliano  
- Lic. Oscar Ruina  

**Integrantes:**  
- Alan Maciel
- Emiliano Ginarte Delgado
- Rodrigo Obando
- Santiago Zurlo

Este es un proyecto académico web desarrollado en Java 21 utilizando Spring Boot como framework principal, junto con Spring Data JPA para el acceso a datos.
El objetivo es simular un sistema de soporte mediante tickets, diferenciando roles entre clientes y empleados.

## Requisitos

- Java 21
- Maven 3.9+
- MySQL 8.0 o compatible
- IDE recomendado: IntelliJ IDEA / Eclipse / VS Code

## Dependencias Principales
El proyecto utiliza las siguientes dependencias:

- Spring Boot
- Spring Data JPA
- Spring Web 

Las dependencias están gestionadas automáticamente mediante Maven en el archivo pom.xml.

## Inicialización del Proyecto

Para comenzar, clonar este repositorio:

```bash
git clone https://github.com/rfnobando/ticketo.git
```
Luego, crear la base de datos
Antes de ejecutar la aplicación, asegurate de tener MySQL instalado y ejecutándose. Luego, creá la base de datos "ticketo" ejecutando el siguiente comando:
```bash
CREATE DATABASE ticketo;
```

Una vez abierto en programa, instalar dependencias del maven

Y por ultimo, Para que la aplicación funcione correctamente, es necesario definir las siguientes variables de entorno:
```bash
(POR SEGURIDAD) EN LA CARPPETA DE DRIVE DE LA PRIMER ENTREGA DE SPRING, LLAMADA VARIABLES DE ENTORNO ENCONTRARAN LAS CLAVES ;
```

## Funcionalidades 

### Cliente

- ✅ Registrarse
- ✅ Iniciar sesión
- ✅ Crear ticket
- ✅ Ver tickets creados
- ✅ Ver mensajes de un ticket creado
- ✅ Responder un ticket creado

### Empleado

- ✅ Iniciar sesión
- ✅ Registrar empleado
- ✅ Asignar rol
- ✅ Quitar rol
- ✅ Ver lista de usuarios
- ✅ Ver lista de empleados
- ✅ Agregar categoria de ticket
- ✅ Agregar departamento
- ✅ Asignar empleado a un departamento
- ✅ Asignar categoria del ticket a un departamento
- ✅ Asignar salario
- ✅ Enviar mensaje en ticket
- ✅ Ver tickets contestados
- ✅ Ver tickets del departamento
- ✅ Ver mensajes de un ticket asignado
- ✅ Marcar ticket como resuelto
- ✅ Cerrar un ticket 

La autenticacion se maneja con Spring Security.

## Ejecución

Para ejecutar el proyecto, debe utilizarse la rama `trunk`, que contiene la versión estable del sistema. La ejecución se realiza iniciando el método main de la clase `TicketoApplication`. Desde ahí, podrán probar las distintas funcionalidades del sistema.
