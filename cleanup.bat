@echo off
echo 🧹 Limpiando proyecto CrimeWave...
echo.

rem Eliminar archivos de build
if exist "app\build" (
    echo ✅ Eliminando carpeta app\build...
    rmdir /s /q "app\build"
)

if exist ".gradle" (
    echo ✅ Eliminando carpeta .gradle...
    rmdir /s /q ".gradle"
)

rem Eliminar archivos temporales de Android Studio
if exist ".idea\caches" (
    echo ✅ Eliminando caches de IDE...
    rmdir /s /q ".idea\caches"
)

if exist ".idea\shelf" (
    echo ✅ Eliminando archivos temporales...
    rmdir /s /q ".idea\shelf"
)

rem Eliminar archivos de log
for /r . %%i in (*.log) do (
    echo ✅ Eliminando %%i
    del "%%i" 2>nul
)

echo.
echo 🎯 Limpieza completada!
echo ℹ️  Los archivos de build se regenerarán automáticamente en la próxima compilación.
echo.
pause
