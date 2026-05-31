# Trabajo Práctico Inmobiliaria 

Este es nuestro proyecto de Android para que los propietarios puedan gestionar sus inmuebles, contratos y pagos directamente desde el celular. 

Nos enfocamos en construir una app robusta, fluida y escalable. Nos basamos en la arquitectura MVVM, así que la vista es completamente "tonta" y el ViewModel se encarga de hacer todo el trabajo pesado.

---

## Qué hace la aplicación

### Sesiones y Seguridad
* **Autenticación:** Login conectado a nuestra API REST. Guardamos el token JWT en SharedPreferences para mantener la sesión activa y que no haya que loguearse a cada rato.
* **Recuperar clave:** Sumamos la opción de "¿Olvidaste tu contraseña?" consumiendo un endpoint específico para resetear la contraseña.
* **Gestión de perfil:** El dueño puede ver y editar sus datos personales, pero dejamos el cambio de contraseña en un módulo aislado por un tema de seguridad.

### Llamada rápida por sensor
* **Acelerómetro:** Si el usuario agita el teléfono en la pantalla de login, el sistema detecta el movimiento y dispara un Intent para llamar automáticamente a "las oficinas de la agencia".

### Interfaz y Navegación
* **Mapa:** En la pantalla de inicio metimos un mapa interactivo (usando el SDK de MapLibre y MapTiler) centrado en la ubicación física de la inmobiliaria.
* **Menú navegable:** Toda la navegación se maneja con un Navigation Drawer (el clásico menú hamburguesa) y el NavController de Android, inyectando los Fragments dinámicamente en una única Activity.

### Administración de Inmuebles
* **Listado:** Usamos un RecyclerView para listar todas las propiedades y Glide para bajar y guardar en caché las imágenes de forma eficiente.
* **Alta con foto:** Formulario dinámico para registrar propiedades. Te permite abrir la galería del celular, procesa la imagen a un arreglo de bytes y la sube al servidor mediante peticiones multipart/form-data. Por regla de negocio, todo inmueble nuevo nace deshabilitado.
* **Estado del inmueble:** Metimos un Checkbox interactivo en el detalle de la propiedad para habilitar o deshabilitar el inmueble al toque, impactando en la API mediante un PUT.

### Inquilinos, Contratos y Pagos
* **Detalles del alquiler:** Mostramos la información detallada de las propiedades que están alquiladas en el momento, incluyendo los datos del inquilino y también de su garante.
* **Historial de pagos:** Una vista dedicada para revisar el historial completo de todas las cuotas abonadas para cada contrato en particular.

---

## Arquitectura (MVVM)

Separación de responsabilidades:

### Vistas (Activities y Fragments)
Son pasivas. Se encargan de inflar el XML utilizando ViewBinding, capturar los clics del usuario y renderizar los componentes visuales recién cuando el ViewModel les avisa que hay novedades.

### ViewModels
Son el cerebro de la app. Hacen las validaciones, procesan la lectura de las URIs de las imágenes para prepararlas para la subida y manejan la matemática de los sensores. Toda la comunicación hacia la vista se hace de forma reactiva exponiendo objetos LiveData y MutableLiveData. También son los únicos encargados de extraer la info que viaja entre pantallas mediante los Bundle.

### Modelo y Red (Retrofit)
* **Mapeo:** Clases estructuradas (Propietario, Inmueble, Contrato, Inquilino, Pago) que implementan Serializable para parsear fácilmente los JSON de respuesta con Gson.
* **Endpoints:** Usamos las interfaces de Retrofit para definir los métodos de red (@GET, @POST, @PUT, @Multipart).
* **Seguridad de la API:** La app nunca envía el ID del propietario en las peticiones. El servidor se encarga de desencriptar el Token Bearer que viaja en el Header para deducir automáticamente de quién son los datos.

---

## Stack Tecnológico

* **Lenguaje:** Java
* **UI y Navegación:** Material Design Components, ViewBinding, Navigation Component.
* **Conexión a API:** Retrofit2, OkHttp, Gson.
* **Carga de Imágenes:** Glide.
* **Mapas:** MapLibre Native SDK para Android.
* **Almacenamiento Local:** SharedPreferences.

---

## Integrantes

* Soto Vela Luciano Ezequiel - DNI: 42.799.718
* Grippo Federico - DNI: 44.752.589
