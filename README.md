# Trabajo Práctico Inmobiliaria - Entrega 2

Aplicación Android desarrollada para la gestión de inmuebles orientada a propietarios. En esta segunda entrega, sobre los cimientos de la autenticación con API REST, se implementó la navegación interna, la gestión del perfil del usuario y la visualización de mapas interactivos, implementando el patrón de diseño MVVM.

## Funcionalidades principales
*   **Autenticación y Sesión:** Ingreso seguro de propietarios consumiendo una API REST mediante Retrofit, con almacenamiento de Token JWT (SharedPreferences) para mantener la sesión activa.
*   **Ubicación de la Inmobiliaria (Mapa):** Implementación del SDK de **MapLibre** en conjunto con la API de **MapTiler** en el Fragment de Inicio para renderizar un mapa interactivo centrado en la ubicación estática de la agencia. 
*   **Gestión de Perfil:** Visualización y edición de los datos personales del propietario logueado, consumiendo los endpoints `GET` y `PUT` de la API de la cátedra.
*   **Cambio de Contraseña Seguro:** Módulo independiente para el cambio de clave (`PUT /api/Propietarios/changePassword`) diseñado en una vista separada para garantizar la seguridad y evitar la corrupción de los *hashes* en la base de datos del servidor.
*   **Cierre de Sesión (Logout):** Implementación de diálogos de confirmación modales que interactúan de forma segura con el ciclo de vida de la aplicación.

## Arquitectura MVVM
El proyecto sigue el patrón arquitectónico Model-View-ViewModel, asegurando una separación total de responsabilidades y cero lógica de negocio o referencias al contexto de Android dentro de los ViewModels:

*   **Modelo:** 
    *   *Mapeo de Datos:* Clases estructuradas (`Propietario`, `Inmueble`) para serializar las respuestas JSON de la API.
    *   *Capa de Red:* Configuración ampliada de `ApiClient` e `ApiInmobiliaria` incorporando los nuevos métodos GET y PUT con cabeceras de autorización Bearer.
*   **Vistas (Activities y Fragments):** 
    *   Se limitan exclusivamente a inflar el diseño (ViewBinding), capturar interacciones del usuario y dibujar componentes visuales (como los `Toast` o `AlertDialog`) únicamente cuando el ViewModel emite el evento correspondiente. 
    *   Se gestiona estrictamente el ciclo de vida de los componentes visuales complejos (como el MapView) delegando sus métodos (`onStart`, `onResume`, etc.) para evitar fugas de memoria (*Memory Leaks*).
*   **ViewModels:** 
    *   Contienen toda la lógica de negocio y validación. Toman las decisiones sobre el estado de la UI (por ejemplo, habilitar/deshabilitar los campos de edición en el Perfil) y manejan las peticiones asíncronas a Retrofit y la configuración de MapLibre. 
    *   Toda la comunicación hacia la vista se realiza de forma reactiva exponiendo objetos `LiveData` / `MutableLiveData`.

## Integrantes
*   **Soto Vela Luciano Ezequiel** - DNI: 42799718
*   **Grippo Federico** - DNI: 44752589
