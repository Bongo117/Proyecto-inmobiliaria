# Trabajo Práctico Inmobiliaria - Entrega 3

Aplicación Android desarrollada para la gestión de inmuebles orientada a propietarios. En esta tercera entrega, habiendo consolidado la autenticación y el perfil, se implementó el módulo completo de Gestión de Inmuebles, incluyendo visualización en listas, subida de imágenes al servidor y actualización de estados, manteniendo un estricto apego al patrón de diseño MVVM.

## Funcionalidades principales
*   **Autenticación y Sesión:** Ingreso seguro de propietarios consumiendo una API REST mediante Retrofit, con almacenamiento de Token JWT (SharedPreferences) para mantener la sesión activa.
*   **Ubicación de la Inmobiliaria (Mapa):** Implementación del SDK de **MapLibre** en conjunto con la API de **MapTiler** en el Fragment de Inicio para renderizar un mapa interactivo centrado en la ubicación estática de la agencia. 
*   **Gestión de Perfil y Seguridad:** Visualización y edición de los datos personales del propietario. Módulo independiente para el cambio de clave seguro diseñado en una vista separada para garantizar la seguridad del hash en la base de datos.
*   **Gestión de Inmuebles (Nuevo):** Visualización del catálogo de propiedades del dueño mediante un `RecyclerView`. Las imágenes de cada propiedad se descargan y cachean de forma asíncrona y eficiente utilizando la librería **Glide**.
*   **Alta de Inmuebles con Foto (Nuevo):** Formulario dinámico para registrar una nueva propiedad (`POST /api/Inmuebles/cargar`). Permite seleccionar una fotografía desde la galería del dispositivo y enviarla al servidor mediante peticiones HTTP de tipo `multipart/form-data`.
*   **Detalle y Disponibilidad (Nuevo):** Navegación fluida hacia el detalle completo de una propiedad pasando el objeto serializado a través de la arquitectura de navegación (`Bundle`). Permite al usuario habilitar o deshabilitar el inmueble actualizando su estado en tiempo real en la base de datos (`PUT /api/Inmuebles/actualizar`).
*   **Cierre de Sesión (Logout):** Implementación de diálogos de confirmación modales (`AlertDialog`) que interactúan de forma segura con el ciclo de vida de la aplicación.

## Arquitectura MVVM 
El proyecto sigue el patrón arquitectónico Model-View-ViewModel, asegurando una separación total de responsabilidades. Se ha puesto especial énfasis en erradicar cualquier tipo de lógica de negocio o procesamiento de datos en la capa de la vista:

*   **Modelo:** 
    *   *Mapeo de Datos:* Clases estructuradas que implementan `Serializable` (`Propietario`, `Inmueble`) para parsear las respuestas JSON y facilitar el paso de argumentos entre fragmentos.
    *   *Capa de Red:* Configuración ampliada de `ApiClient` y la interfaz de Retrofit, incorporando el manejo de `@Multipart` para la subida de archivos binarios (`RequestBody`, `MultipartBody.Part`) junto a las cabeceras de autorización Bearer.
*   **Vistas (Activities y Fragments):** 
    *   Son vistas 100% pasivas. Se limitan exclusivamente a inflar el diseño (ViewBinding), capturar interacciones del usuario y renderizar componentes visuales únicamente cuando el ViewModel emite el evento correspondiente. 
    *   **Navegación Segura:** El enrutamiento se gestiona a través del `NavController` (`mobile_navigation.xml`).
*   **ViewModels:** 
    *   Contienen toda la lógica de negocio, cálculos y validaciones. 
    *   **Procesamiento Centralizado:** Los ViewModels son los únicos encargados de deserializar y extraer datos de los `Bundle` o `Intents`, así como de transformar las URIs de las imágenes a arreglos de bytes para su posterior subida.
    *   Toda la comunicación hacia la vista se realiza de forma reactiva exponiendo objetos `LiveData` / `MutableLiveData` para notificar actualizaciones de datos, errores de red o mensajes para Toasts.

## Integrantes
*   **Soto Vela Luciano Ezequiel** - DNI: 42799718
*   **Grippo Federico** - DNI: 44752589
