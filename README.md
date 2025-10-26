# 🛍️ CrimeWave - Tienda de Ropa Online

## 📱 Descripción
CrimeWave es una aplicación Android nativa desarrollada con Jetpack Compose que funciona como una **tienda online especializada en productos de anime y ropa streetwear**. La aplicación ofrece una experiencia completa de ecommerce con autenticación, gestión de productos, y manejo de direcciones.

## 🚀 Funcionalidades Principales

### 🔐 Autenticación
- **Login/Register**: Sistema completo de autenticación
- **Roles de usuario**: Diferenciación entre Admin y Cliente
- **Usuarios predefinidos**:
  - Admin: `admin:admin` (Gestión completa)
  - Cliente: `hola:hola` (Compras y perfil)

### 🏪 Catálogo de Productos
- **Página principal**: Muestra productos destacados y categorías
- **Detalles de producto**: Vista individual con información completa
- **Categorías disponibles**:
  - 👕 **Poleras** (Tallas: XS, S, M, L, XL, XXL)
  - 🧥 **Polerones** (Tallas: XS, S, M, L, XL, XXL) 
  - 🖼️ **Cuadros Anime** (Medidas: 30x39, 40x50, 50x70, 70x81)
- **Precios en CLP** (Pesos Chilenos)

### 👤 Gestión de Perfil
- **Información personal**: Email, teléfono, datos de usuario
- **Editar detalles**: Actualización de información personal
- **Direcciones de envío**: Formulario completo con validaciones
- **Direcciones de facturación**: Gestión separada de datos fiscales
- **Validaciones chilenas**: RUT y teléfono de 9 dígitos

### ⚙️ Panel de Administración (Solo Admin)
- **Gestión de productos**: Ver, editar y eliminar productos
- **Validaciones**: Precio mínimo $15,000 CLP, stock no negativo
- **Tallas dinámicas**: Según categoría del producto
- **Imágenes predeterminadas**: Por cada categoría de producto

### 🛒 Experiencia de Compra
- **Singles de producto**: Página detallada de cada artículo
- **Información completa**: Descripción, precio, tallas/medidas disponibles
- **Navegación fluida**: Entre catálogo y detalles de productos
- **Diseño responsive**: Optimizado para dispositivos móviles

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin 100%
- **UI Framework**: Jetpack Compose (Modern Android UI)
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Navegación**: Sistema de estados manual
- **Material Design**: Material Design 3
- **Dependencias principales**:
  - androidx.compose.material3
  - androidx.lifecycle.viewmodel-compose
  - androidx.compose.material:material-icons-extended

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/crimewave/
├── MainActivity.kt                 # Actividad principal y navegación
├── data/
│   └── model/
│       ├── ClothingItem.kt        # Modelo de productos de ropa
│       ├── User.kt                # Modelo de usuarios y direcciones
│       └── CrimeItem.kt           # Modelo legacy (deprecated)
├── ui/
│   ├── components/
│   │   └── ProductCard.kt         # Componente para mostrar productos
│   ├── screens/
│   │   ├── HomeScreen.kt          # Catálogo principal
│   │   ├── DetailsScreen.kt       # Detalles de producto (single)
│   │   ├── ProfileScreen.kt       # Perfil de usuario
│   │   ├── LoginScreen.kt         # Autenticación
│   │   ├── RegisterScreen.kt      # Registro de usuarios
│   │   ├── ReportScreen.kt        # Agregar productos (admin)
│   │   ├── EmployeePanelScreen.kt # Panel de administración
│   │   ├── ShippingAddressScreen.kt # Direcciones de envío
│   │   ├── BillingAddressScreen.kt  # Direcciones de facturación
│   │   └── SettingsScreen.kt      # Configuración
│   ├── theme/
│   │   ├── Color.kt               # Colores del tema
│   │   ├── Theme.kt               # Configuración del tema
│   │   └── Type.kt                # Tipografía
│   └── viewmodel/
│       ├── CrimeViewModel.kt      # ViewModel para productos (ClothingViewModel)
│       └── AuthViewModel.kt       # ViewModel para autenticación
├── res/
│   └── drawable/                  # Imágenes de productos
│       ├── satorupolera.jpg       # Productos poleras
│       ├── togahoodie.jpg         # Productos polerones
│       ├── givencuadro.jpg        # Productos cuadros
│       └── ...                    # Más imágenes de productos
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Android Studio (versión más reciente)
- SDK de Android (API 24+)
- Kotlin 1.8+
- Gradle 8.0+

### Pasos de Instalación
1. **Clonar el repositorio**:
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd crimewave
   ```

2. **Abrir en Android Studio**:
   - Seleccionar "Open an existing project"
   - Navegar a la carpeta del proyecto
   - Esperar a que se sincronicen las dependencias

3. **Ejecutar la aplicación**:
   - Conectar un dispositivo Android o iniciar un emulador
   - Presionar "Run" o usar `Shift + F10`

## 👥 Usuarios de Prueba

### Administrador
- **Email**: `admin`
- **Contraseña**: `admin`
- **Permisos**: Gestión completa de productos, panel de empleados

### Cliente
- **Email**: `hola`
- **Contraseña**: `hola`
- **Permisos**: Navegación, perfil, direcciones

## 🎨 Características de Diseño

### Material Design 3
- **Colores principales**: Azul Material (#2196F3)
- **Tipografía**: Sistema de tipos Material
- **Componentes**: Cards, Buttons, TextFields modernos
- **Iconografía**: Material Icons Extended

### Responsive Design
- **Headers optimizados**: Espaciado correcto para móviles (48dp)
- **Inputs estandarizados**: Altura de 56dp para mejor accesibilidad
- **Navegación fluida**: ScrollView en todos los formularios
- **Botones accesibles**: Área mínima de toque 48dp

## 📊 Productos Incluidos

### Poleras (8 productos)
- Polera Araña - $25,990 CLP
- Polera Satoru Gojo - $29,990 CLP
- Collection Anime - $27,500 CLP
- Y más diseños exclusivos...

### Polerones (2 productos)
- Polerón Anime Premium - $42,000 CLP
- Toga Hoodie - $45,000 CLP

### Cuadros Decorativos (1 producto)
- Cuadros Anime Collection - $45,000 CLP
- Medidas disponibles: 30x39, 40x50, 50x70, 70x81 cm

## ⚙️ Configuración del Proyecto

### Versiones
- **Compile SDK**: 34
- **Min SDK**: 24
- **Target SDK**: 34
- **Kotlin**: 1.8.10
- **Compose BOM**: 2023.08.00

### Dependencias Principales
```gradle
dependencies {
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.material3:material3'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose'
    implementation 'androidx.compose.material:material-icons-extended'
}
```

## 🔄 Roadmap Futuro

### Próximas Funcionalidades
- [ ] **Carrito de compras** completo
- [ ] **Sistema de pagos** (WebPay, Mercado Pago)
- [ ] **Base de datos** local (Room)
- [ ] **API REST** para productos dinámicos
- [ ] **Búsqueda y filtros** avanzados
- [ ] **Sistema de reviews** y calificaciones
- [ ] **Notificaciones push**

### Mejoras Técnicas
- [ ] **Navigation Compose** (reemplazar navegación manual)
- [ ] **Testing automatizado** (Unit + UI tests)
- [ ] **CI/CD Pipeline**
- [ ] **Internacionalización** (i18n)
- [ ] **Performance monitoring**

## 🤝 Contribuciones

Este proyecto está abierto a contribuciones. Para contribuir:

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Distribuido bajo la licencia MIT. Ver `LICENSE` para más información.

## 📞 Contacto

**Desarrollador**: [Tu Nombre]
**Email**: [tu.email@ejemplo.com]
**Proyecto**: [Link del repositorio]

---

**⭐ Si te gusta este proyecto, ¡dale una estrella en GitHub!**
