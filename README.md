# Trabajo Práctico Inmobiliaria - Entrega 1

Aplicación Android desarrollada para la gestión de inmuebles. En esta primera entrega se implementan los cimientos arquitectónicos del proyecto: la autenticación de usuarios consumiendo una API REST y la estructura base de navegación.

## Funcionalidades principales
* **Autenticación de Propietarios:** Formulario de login que captura credenciales y se comunica de manera asíncrona con el servidor mediante Retrofit.
* **Manejo de Sesión:** Captura del Token JWT provisto por la API y almacenamiento persistente local utilizando `SharedPreferences` para el uso en futuras peticiones (formato `Bearer <token>`).
* **Navegación Base:** Implementación de un menú lateral desplegable (Navigation Drawer) que actúa como esqueleto para las futuras secciones de la aplicación (Perfil, Inmuebles, Inquilinos, Contratos).

## División de Tareas

El desarrollo de esta etapa se dividió estratégicamente en dos áreas fundamentales:

* **Soto Vela Luciano Ezequiel:** Encargado de la capa de red, seguridad y la lógica de inicio de sesión. Desarrolló el consumo de la API REST mediante **Retrofit** (`ApiClient` y la interfaz `ApiInmobiliaria`), el almacenamiento del Token JWT, y aplicó el patrón MVVM estricto en la validación y autenticación de credenciales (`LoginActivity` y `LoginViewModel`).
* **Grippo Federico:** Encargado de la arquitectura visual y el enrutamiento interno. Desarrolló la estructura base ("Responsive Views Activity"), la configuración del **Navigation Drawer** en el `MainActivity`, el grafo de navegación (`mobile_navigation.xml`) y la creación de los Fragments base necesarios para las futuras entregas.

## Arquitectura MVVM
El proyecto sigue el patrón arquitectónico Model-View-ViewModel, asegurando una separación total de responsabilidades y cero lógica de negocio o referencias al contexto de Android dentro de los ViewModels:

* **Modelo:**
  * Paquete `modelo`: Clases de datos (`Inmueble`, `Propietario`) estructuradas para el mapeo de datos.
  * Paquete `request`: Configuración del cliente HTTP con Retrofit (`ApiClient`) soportando conversores duales (Scalars y Gson) y la interfaz de rutas (`ApiInmobiliaria`).
* **Vistas:**
  * `LoginActivity`: Captura de datos del usuario, observación de respuestas y enrutamiento post-login (sin contener condicionales lógicos).
  * `MainActivity`: Gestión del menú navegable y contenedor principal de los fragmentos.
  * `Fragments`: Vistas vacías preparadas para el renderizado de datos en las próximas etapas.
* **ViewModels:**
  * `LoginViewModel`: Lógica de validación de campos, ejecución de llamadas asíncronas a la API (`.enqueue`) y procesamiento de errores HTTP. 
* **Comunicación:** Uso exclusivo de `MutableLiveData` y `LiveData` para notificar resultados o mensajes de error desde los ViewModels hacia las Vistas, sin retornos directos de información ni uso de Intents en la capa lógica.

## Integrantes
* **Soto Vela Luciano Ezequiel** - DNI: 42799718
* **Grippo Federico** - DNI: 44752589
