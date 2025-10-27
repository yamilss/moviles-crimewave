# Estructura de Navegación - Crimewave App

Esta carpeta contiene toda la lógica de navegación de la aplicación, organizada de manera modular y escalable.

## 📁 Estructura de Archivos

### `Routes.kt`
- **Propósito**: Define todas las rutas/constantes de navegación
- **Contenido**: Constantes string para cada pantalla de la aplicación
- **Beneficio**: Centraliza las rutas y evita errores de tipeo

### `NavigationState.kt`
- **Propósito**: Maneja el estado de navegación de la aplicación
- **Contenido**: 
  - Estado actual y anterior de pantallas
  - Stack de navegación para el historial
  - ID de elementos seleccionados
  - Control de diálogos
- **Beneficio**: Estado centralizado y reactivo

### `NavigationActions.kt`
- **Propósito**: Define e implementa todas las acciones de navegación
- **Contenido**:
  - Interfaz `NavigationActions` con todas las acciones posibles
  - Implementación `NavigationActionsImpl` que ejecuta las acciones
- **Beneficio**: Separación clara entre definición e implementación

### `ScreenNavigator.kt`
- **Propósito**: Composable que maneja la navegación entre pantallas
- **Contenido**: 
  - Switch principal que decide qué pantalla mostrar
  - Validaciones de autenticación y permisos
  - Animaciones de transición
- **Beneficio**: Lógica de navegación centralizada y organizada

### `NavigationHost.kt`
- **Propósito**: Host principal que orquesta toda la navegación
- **Contenido**:
  - Inicialización de ViewModels
  - Configuración de estado de navegación
  - Manejo de BackHandler
  - Diálogo de salida
- **Beneficio**: Punto de entrada único y limpio

### `NavigationUtils.kt`
- **Propósito**: Utilidades y helpers para navegación
- **Contenido**:
  - Funciones de validación (autenticación, permisos)
  - Títulos de pantallas
  - Diálogo de confirmación de salida
- **Beneficio**: Funciones reutilizables y organizadas

## 🔄 Flujo de Navegación

1. **MainActivity** → Inicializa `NavigationHost`
2. **NavigationHost** → Configura ViewModels y estado
3. **ScreenNavigator** → Decide qué pantalla mostrar
4. **NavigationActions** → Ejecuta acciones de navegación
5. **NavigationState** → Mantiene el estado actual

## ✅ Beneficios de esta Estructura

### 🧹 **Código Más Limpio**
- MainActivity reducido de ~330 líneas a ~15 líneas
- Separación clara de responsabilidades
- Código más legible y mantenible

### 🔧 **Fácil Mantenimiento**
- Agregar nuevas pantallas es simple
- Modificar lógica de navegación en un solo lugar
- Debugging más sencillo

### 🚀 **Escalabilidad**
- Estructura preparada para crecimiento
- Fácil agregar nuevas funcionalidades
- Patrones consistentes

### 🛡️ **Robustez**
- Validaciones centralizadas
- Manejo consistente de errores
- Estado predecible

### 🎯 **Reutilización**
- Acciones de navegación reutilizables
- Utilidades compartidas
- Patrones consistentes

## 📝 Cómo Agregar una Nueva Pantalla

1. **Agregar ruta** en `Routes.kt`:
   ```kotlin
   const val NEW_SCREEN = "newScreen"
   ```

2. **Agregar acción** en `NavigationActions.kt`:
   ```kotlin
   fun navigateToNewScreen()
   ```

3. **Implementar acción** en `NavigationActionsImpl`:
   ```kotlin
   override fun navigateToNewScreen() {
       navigationState.navigateToScreen(Routes.NEW_SCREEN)
   }
   ```

4. **Agregar caso** en `ScreenNavigator.kt`:
   ```kotlin
   Routes.NEW_SCREEN -> {
       NewScreen(
           onNavigateBack = { navigationActions.navigateBack() }
       )
   }
   ```

5. **Opcional**: Agregar validaciones en `NavigationUtils.kt`

## 🎯 Ejemplo de Uso

```kotlin
// En cualquier pantalla, usar las acciones de navegación:
@Composable
fun SomeScreen(navigationActions: NavigationActions) {
    Button(
        onClick = { navigationActions.navigateToProfile() }
    ) {
        Text("Ir a Perfil")
    }
}
```

## 🔒 Seguridad y Validaciones

- ✅ Validación de autenticación automática
- ✅ Restricciones de acceso por rol (admin/cliente)
- ✅ Validación de parámetros requeridos
- ✅ Redirecciones automáticas de seguridad
- ✅ Manejo de estados edge cases

Esta estructura proporciona una base sólida y escalable para toda la navegación de la aplicación.