@echo off
echo.
echo 🧹 LIMPIEZA AUTOMÁTICA DEL PROYECTO CRIMEWAVE
echo =============================================
echo.

cd /d "C:\Users\sekai\AndroidStudioProjects\crimewave"

echo 📄 Eliminando archivos markdown innecesarios...
del /q "ANALISIS_EVALUACION_2.md" 2>nul
del /q "ANIMACION_ESCALA_ELEGANTE.md" 2>nul
del /q "ANIMACION_ESTILO_PIXEL.md" 2>nul
del /q "ANIMACION_SIN_SALTOS.md" 2>nul
del /q "ANIMACION_SLIDE_IMPLEMENTADA.md" 2>nul
del /q "ANIMACION_SUTIL_IMPLEMENTADA.md" 2>nul
del /q "ANIMACIONES_COMPLETADAS.md" 2>nul
del /q "ANIMACIONES_PANTALLAS.md" 2>nul
del /q "ANIMACIONES_SIMPLIFICADAS.md" 2>nul
del /q "CARRITO_COMPLETO_IMPLEMENTADO.md" 2>nul
del /q "EVALUACION_PROYECTO_CRIMEWAVE.md" 2>nul
del /q "NAVEGACION_BACKHANDLER.md" 2>nul
del /q "POPUP_AUTH_SCREENS.md" 2>nul
del /q "VALIDACIONES_IMPLEMENTADAS.md" 2>nul
echo    ✅ Archivos .md eliminados

echo.
echo 🗂️ Eliminando archivos de código obsoletos...
del /q "app\src\main\java\com\example\crimewave\data\model\CrimeItem.kt" 2>nul
del /q "app\src\main\java\com\example\crimewave\ui\screens\StatsScreen.kt" 2>nul
del /q "app\src\main\java\com\example\crimewave\ui\components\CrimeCard.kt" 2>nul
echo    ✅ Archivos obsoletos eliminados

echo.
echo 🧹 Eliminando scripts de limpieza duplicados...
del /q "cleanup.bat" 2>nul
del /q "cleanup_final.ps1" 2>nul
echo    ✅ Scripts duplicados eliminados

echo.
echo 🏗️ Limpiando directorios de build...
rmdir /s /q "app\build" 2>nul
rmdir /s /q ".gradle" 2>nul
rmdir /s /q "build" 2>nul
echo    ✅ Directorios de build eliminados

echo.
echo 🗑️ Eliminando archivos temporales...
del /s /q "*.tmp" 2>nul
del /s /q "*.temp" 2>nul
del /s /q "*.log" 2>nul
del /s /q "*.bak" 2>nul
echo    ✅ Archivos temporales eliminados

echo.
echo =============================================
echo 🎉 ¡LIMPIEZA COMPLETADA!
echo.
echo ✅ Archivos mantenidos (importantes):
echo    📚 README.md (documentación)
echo    🔧 gradle/ (configuración)
echo    📱 app/src/ (código fuente)
echo    🎨 ImageUtils.kt (utilidades)
echo.
echo 🏆 El proyecto está ahora optimizado y limpio.
echo =============================================
echo.
pause
