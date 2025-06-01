# Flor.es

Flor.es es una plataforma web desarrollada como proyecto académico para la asignatura ISST (Ingeniería de Servicios y Sistemas de Telecomunicación) en la UPM. Su objetivo es conectar a clientes directamente con floricultores locales, desintermediando la compra de flores para garantizar envíos rápidos en los que las flores lleguen frescas, y reducir la huella de carbono.

## Descripción general

Flor.es permite a los usuarios explorar catálogos de flores, realizar pedidos personalizados y valorar a los floricultores. Los floricultores pueden gestionar su inventario y recibir pedidos. La plataforma está diseñada para ser intuitiva, segura y eficiente, fomentando el comercio local y la transparencia.

## Características principales

- **Registro y autenticación:**  
  Soporte para clientes y floricultores, con roles diferenciados y seguridad en el acceso.
- **Catálogo de productos:**  
  Visualización de flores y arreglos disponibles, con imágenes, descripciones y precios.
- **Búsqueda avanzada:**  
  Filtros por código postal, tipo de flor, precio y disponibilidad.
- **Carrito de compras:**  
  Añade productos, modifica cantidades y realiza pedidos personalizados.
- **Gestión de pedidos:**  
  Seguimiento de pedidos para clientes y panel de gestión para floricultores.
- **Valoraciones y comentarios:**  
  Los clientes pueden valorar y dejar opiniones sobre los floricultores.
- **Panel de administración:**  
  Los floricultores pueden añadir, editar o eliminar productos y gestionar pedidos.
- **Base de datos embebida:**  
  Uso de H2 para facilitar el desarrollo y pruebas.

## Estructura del proyecto
- `src/main/java/es/upm/dit/isst/isstgrupo07flores/`  
  Código fuente Java: controladores, modelos, repositorios y servicios.
- `src/main/resources/templates/`  
  Plantillas Thymeleaf para la interfaz web.
- `src/main/resources/static/`  
  Archivos estáticos: CSS, imágenes, JS.
- `src/main/resources/application.properties`  
  Configuración de la aplicación (puerto, base de datos, etc).
- `data/isst.mv.db`  
  Base de datos H2 embebida.

## Tecnologías utilizadas
- **Backend:** Java 17, Spring Boot, Spring Data JPA, Spring Security
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap
- **Base de datos:** H2 embebida
- **Build:** Maven
- **Control de versiones:** Git y GitHub

## Instalación y ejecución

1. **Clona el repositorio:**
   ```sh
   git clone https://github.com/nietovictor/flor.es
   cd flor.es
   ```

2. **Compila el proyecto:**
   ```sh
   ./mvnw clean package
   ```

3. **Ejecuta la aplicación:**
   ```sh
   ./mvnw spring-boot:run
   ```

4. **Accede a la web:**  
   [http://localhost:8080](http://localhost:8080)

## Base de datos

El proyecto utiliza una base de datos H2 embebida. Los datos se almacenan en la carpeta `data/`.  
Puedes acceder a la consola de H2 en [http://localhost:8080/h2-console](http://localhost:8080/h2-console) usando las credenciales configuradas en `application.properties`.


## Tests

Para ejecutar los tests:
```sh
./mvnw test
```
---

**Autores:**  
Grupo 07 - ISST  
- Víctor Nieto Palacios  
- David Fuentes Martín  
- Mateo Sarria Franco de Sarabia  
- Agustín Sánchez Martínez 

Escuela Técnica Superior de Ingenieros de Telecomunicación, UPM