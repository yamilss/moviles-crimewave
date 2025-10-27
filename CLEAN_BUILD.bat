@echo off
echo 🧹 Limpiando proyecto para resolver errores de compilacion...
cd "C:\Users\sekai\AndroidStudioProjects\crimewave"

echo.
echo ⚠️ Eliminando directorios de build...
rmdir /s /q "app\build" 2>nul
rmdir /s /q ".gradle" 2>nul
rmdir /s /q "build" 2>nul

echo.
echo 🗑️ Eliminando archivos temporales...
del /s /q "*.tmp" 2>nul
del /s /q "*.temp" 2>nul

echo.
echo ✅ Proyecto limpiado exitosamente!
echo.
echo 🔄 Ejecutando limpieza de Gradle...
.\gradlew clean

echo.
echo 🏗️ Compilando proyecto...
.\gradlew assembleDebug

echo.
echo 🎉 ¡Proceso completado!
pause
