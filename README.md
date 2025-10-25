# CrimeWave - Aplicación Android Nativa

## 📱 Descripción
CrimeWave es una aplicación Android nativa desarrollada con Jetpack Compose para reportar y seguir incidentes de seguridad. La aplicación permite a los usuarios reportar crímenes, ver estadísticas, y seguir el estado de los incidentes en su área.

## 🚀 Funcionalidades Principales

### 🏠 Pantalla Principal (Home)
- Lista de incidentes recientes
- Información detallada de cada incidente (título, descripción, ubicación, fecha, severidad)
- Navegación a perfil y detalles
- Botón flotante para reportar nuevos incidentes

### 👤 Perfil de Usuario
- Información del usuario
- Estadísticas personales (reportes, seguimientos, casos resueltos)
- Acciones rápidas (reportar incidente, ver reportes, ver estadísticas)
- Navegación a configuración

### 📊 Estadísticas
- Resumen general de incidentes
- Gráficos de severidad con barras de progreso
- Estadísticas por ubicación
- Cards con métricas importantes (total, mensual, críticos)

### ⚙️ Configuración
- Ajustes de notificaciones
- Modo oscuro/claro
- Información de la aplicación

### 📝 Reportar Incidentes
- Formulario completo para reportar nuevos incidentes
- Campos: título, descripción, ubicación, severidad
- Validación de campos obligatorios
- Dropdown para seleccionar nivel de severidad

### 🔍 Detalles del Incidente
- Vista detallada de cada incidente
- Información completa del caso
- Acciones disponibles (seguir caso, reportar información adicional)

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Navegación**: Sistema de navegación por estados
- **Material Design**: Material 3
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
│       └── CrimeItem.kt           # Modelo de datos para incidentes
├── ui/
│   ├── components/
│   │   └── CrimeCard.kt           # Componente reutilizable para mostrar incidentes
│   ├── screens/
│   │   ├── HomeScreen.kt          # Pantalla principal
│   │   ├── ProfileScreen.kt       # Pantalla de perfil
│   │   ├── SettingsScreen.kt      # Pantalla de configuración
│   │   ├── DetailsScreen.kt       # Pantalla de detalles
│   │   ├── ReportScreen.kt        # Pantalla para reportar incidentes
│   │   └── StatsScreenNew.kt      # Pantalla de estadísticas
│   ├── theme/
│   │   ├── Color.kt               # Colores del tema
│   │   ├── Theme.kt               # Configuración del tema
│   │   └── Type.kt                # Tipografía
│   └── viewmodel/
│       └── CrimeViewModel.kt      # ViewModel para manejo de datos
└── navigation/
    ├── Screen.kt                  # Definición de rutas (para futuro uso con Navigation Compose)
    └── CrimewaveNavigation.kt     # Navegación (para futuro uso con Navigation Compose)
```

## 🎨 Características de Diseño

- **Material Design 3**: Interfaz moderna y consistente
- **Tema adaptativo**: Soporte para modo claro y oscuro
- **Iconografía**: Uso de Material Icons con soporte para AutoMirrored
- **Colores semánticos**: Sistema de colores para diferentes niveles de severidad
- **Responsive**: Diseño adaptable a diferentes tamaños de pantalla

## 🔧 Configuración del Proyecto

### Permisos
- `INTERNET`: Para futuras funcionalidades de red
- `ACCESS_NETWORK_STATE`: Para verificar conectividad

### Dependencias Clave
```kotlin
// Compose BOM para compatibilidad de versiones
implementation(platform(libs.androidx.compose.bom))

// Core Compose
implementation(libs.androidx.compose.ui)
implementation(libs.androidx.compose.material3)
implementation(libs.androidx.activity.compose)

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

// Material Icons Extended
implementation("androidx.compose.material:material-icons-extended:1.5.4")
```

## 📱 Flujo de Navegación

```
HomeScreen (Inicio)
├── ProfileScreen (Perfil)
│   ├── SettingsScreen (Configuración)
│   └── StatsScreen (Estadísticas)
├── DetailsScreen (Detalles del incidente)
└── ReportScreen (Reportar incidente)
```

## 🚀 Funcionalidades Futuras

- [ ] Integración con APIs reales para datos de incidentes
- [ ] Sistema de autenticación de usuarios
- [ ] Notificaciones push
- [ ] Mapas interactivos con ubicaciones de incidentes
- [ ] Sistema de comentarios y actualizaciones en tiempo real
- [ ] Exportación de reportes
- [ ] Modo offline con sincronización
- [ ] Sistema de calificaciones y verificación de reportes

## 🏗️ Compilación y Ejecución

1. Abrir el proyecto en Android Studio
2. Sincronizar dependencias (Gradle Sync)
3. Ejecutar en emulador o dispositivo físico
4. La aplicación funcionará completamente sin necesidad de conexión a internet

## 💡 Notas de Desarrollo

- La aplicación actualmente usa datos de muestra (mock data)
- Sistema de navegación implementado con estados de Compose
- Preparado para migrar a Navigation Compose cuando sea necesario
- Arquitectura escalable para agregar nuevas funcionalidades
- Código limpio y bien documentado para fácil mantenimiento

## 🎯 Objetivo del Proyecto

Esta aplicación demuestra el desarrollo de una aplicación Android nativa moderna usando las mejores prácticas de desarrollo con Jetpack Compose, proporcionando una base sólida para una aplicación de seguridad ciudadana completamente funcional.
