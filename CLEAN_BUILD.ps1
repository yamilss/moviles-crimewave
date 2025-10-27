# Script de PowerShell para limpiar y compilar el proyecto
Write-Host "🧹 Limpiando proyecto CrimeWave..." -ForegroundColor Cyan

# Cambiar al directorio del proyecto
Set-Location "C:\Users\sekai\AndroidStudioProjects\crimewave"

Write-Host ""
Write-Host "⚠️ Eliminando directorios de build..." -ForegroundColor Yellow
Remove-Item "app\build" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item ".gradle" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "build" -Recurse -Force -ErrorAction SilentlyContinue

Write-Host ""
Write-Host "🗑️ Eliminando archivos temporales..." -ForegroundColor Yellow
Get-ChildItem -Recurse -Include "*.tmp", "*.temp" | Remove-Item -Force -ErrorAction SilentlyContinue

Write-Host ""
Write-Host "✅ Proyecto limpiado exitosamente!" -ForegroundColor Green

Write-Host ""
Write-Host "🔄 Ejecutando limpieza de Gradle..." -ForegroundColor Cyan
& .\gradlew clean

Write-Host ""
Write-Host "🏗️ Compilando proyecto..." -ForegroundColor Cyan
& .\gradlew assembleDebug

Write-Host ""
Write-Host "🎉 ¡Proceso completado!" -ForegroundColor Green
Write-Host "Presiona Enter para continuar..."
Read-Host
