# Trabajo Práctico Inmobiliaria - Entrega 3

Aplicación Android diseñada para que los propietarios gestionen sus inmuebles. En esta tercera entrega, con la autenticación y el perfil ya consolidados, sumamos todo el módulo de inmuebles: ver el catálogo, subir fotos desde el dispositivo y cambiar el estado de disponibilidad, manteniendo la arquitectura MVVM bien prolija.

## Funcionalidades principales

* **Ingreso y Sesión:** Login conectado a la API mediante Retrofit. Guardamos el token JWT (en SharedPreferences) para que el usuario no tenga que volver a loguearse.
* **Mapa de la Inmobiliaria:** En la pantalla de inicio integramos un mapa interactivo (usando el SDK de **MapLibre** junto a **MapTiler**) centrado en la ubicación física de la agencia.
* **Perfil y Seguridad:** El dueño puede ver y editar sus datos personales. Armamos el cambio de contraseña en una vista independiente para manejar de forma segura el hash en la base de datos.
* **Gestión de Inmuebles (Nuevo):** 
  * **Catálogo:** Una lista (`RecyclerView`) para ver todas las propiedades. Usamos **Glide** para que las imágenes se descarguen y almacenen en caché de forma eficiente.
  * **Alta con Foto:** Formulario dinámico para registrar propiedades (`POST /api/Inmuebles/cargar`). Permite elegir una foto de la galería y mandarla al servidor mediante peticiones `multipart/form-data`.
  * **Detalles y Estado:** Navegación fluida hacia la info completa pasando el objeto a través de `Bundle`. Incluye un CheckBox para habilitar o deshabilitar el inmueble, actualizando la base de datos en tiempo real (`PUT /api/Inmuebles/actualizar`).
* **Cierre de Sesión (Logout):** Cierre seguro mediante un diálogo de confirmación (`AlertDialog`) antes de salir.

## Arquitectura (MVVM)

Nos pusimos bastante estrictos con separar las responsabilidades. La vista solo muestra la interfaz y el ViewModel se encarga de todo el trabajo pesado:

### Modelo
* **Mapeo de datos:** Clases estructuradas (`Propietario`, `Inmueble`) que implementan `Serializable` para parsear los JSON y pasar datos entre pantallas.
* **Capa de red:** Configuración de `ApiClient` e interfaces de Retrofit, sumando el manejo de `@Multipart` para subir archivos binarios (`MultipartBody.Part`) junto a las cabeceras Bearer.

### Vistas (Activities y Fragments)
* Se limitan exclusivamente a inflar el diseño con **ViewBinding**, capturar interacciones del usuario y renderizar componentes visuales cuando el ViewModel avisa.
* El enrutamiento de pantallas se gestiona mediante el `NavController` (`mobile_navigation.xml`).

### ViewModels
* Son el cerebro de la app: contienen la lógica de negocio, validaciones y transformaciones de datos.
* Son los únicos encargados de extraer datos de los `Bundle`, así como de transformar las URIs de las imágenes a arreglos de bytes para la subida.
* Toda la comunicación hacia la vista se hace de forma reactiva exponiendo objetos `LiveData` / `MutableLiveData` (para actualizar datos, errores de red o alertas).

## Integrantes
* **Soto Vela Luciano Ezequiel** - DNI: 42799718
* **Grippo Federico** - DNI: 44752589
