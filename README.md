# 🛍️ CrimeWave - Tienda Anime App

Aplicación móvil Android desarrollada en **Kotlin con Jetpack Compose**, inspirada en la moda y cultura anime.  
Permite comprar ropa, administrar productos y gestionar carritos de compra con una interfaz moderna y fluida. 😎

---

## 👤 Equipo

**Desarrollador principal:** Yamil Apablaza 
📧 **Correo:** ya.apablaza@duocuc.cl 

---

## 💡 Descripción general

**CrimeWave** es una app de e-commerce enfocada en la venta de ropa anime.  
Los usuarios pueden registrarse, iniciar sesión, explorar productos, agregarlos al carrito y simular compras.  
Los administradores pueden gestionar el catálogo mediante un panel exclusivo.

---

## ⚙️ Funcionalidades principales

### 🧍 Usuario cliente
- Registro e inicio de sesión  
- Catálogo de productos (poleras, sudaderas, etc.)  
- Detalle del producto (precio, tallas, colores, stock, descripción)  
- Agregar y quitar productos del carrito  
- Checkout con cálculo de IVA (19%)  
- Validaciones de stock y monto máximo de compra  
- Envío gratis por compras sobre **$50.000**

### 🛠️ Usuario administrador
- Panel de control con acceso restringido  
- CRUD completo de productos (crear, leer, actualizar, eliminar)  
- Gestión de stock y categorías  

---

## 🧱 Tecnologías utilizadas

| Tipo | Herramienta |
|------|--------------|
| Lenguaje | **Kotlin** |
| UI | **Jetpack Compose + Material 3** |
| Arquitectura | **MVVM + Repository Pattern** |
| Almacenamiento local | **SharedPreferences** |
| Librerías | Gson, Coil, Retrofit, Material Icons |
| Control de versiones | Git + GitHub |

---

## 📱 Pantallas principales

- 🏠 **Home:** lista de productos  
- 🧢 **Detalle del producto:** información completa + imagen  
- 🛒 **Carrito de compras:** resumen, IVA y total  
- 💳 **Checkout:** simulación de pago  
- 👤 **Perfil:** datos del usuario  
- 🧰 **Panel Admin:** CRUD de productos  

---

## 💰 Lógica de precios

- IVA: **19%** incluido  
- Envío: **gratis sobre $50.000**  
- Monto máximo de compra: **$100.000**  
- Validación de stock antes de agregar al carrito  

---

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio  
   ```bash
   git clone https://github.com/yamilss/moviles-crimewave.git
   ```
2. Abrir la carpeta en **Android Studio**  
3. Sincronizar Gradle automáticamente  
4. Ejecutar el proyecto con:
   - **Shift + F10**, o  
   - **Botón ▶ Run ‘app’**  
5. Esperar a que se inicie el emulador o dispositivo físico  

---

## 🔐 Usuarios de prueba

| Rol | Usuario | Contraseña |
|-----|----------|-------------|
| Admin | admin | admin |
| Cliente | hola@gmail.com | hola |

---

## 🧪 Estado del proyecto

✅ **95% completado**  
☑️ Falta integrar API remota y sistema de notificaciones  

---

## 🌟 Características destacadas

- Interfaz moderna con **Material 3**  
- **Animaciones suaves** con Compose  
- **Validaciones** y manejo de errores  
- **Persistencia local** de sesión y carrito  
- **Arquitectura limpia** y modular  
- **Modo admin** oculto  
- **CRUD completo**  

---


---

## 🧠 Conclusión

**CrimeWave** es una aplicación móvil robusta, bien estructurada y con una interfaz atractiva.  
Cumple con los criterios técnicos de la pauta, destacando en organización, experiencia de usuario y uso de tecnologías modernas.

🔥 **Proyecto listo para evaluación final.**
