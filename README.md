# 📱 Cool Tour — App Android

App de turismo con mapa interactivo, audio-guías por geolocalización, rutas personalizadas y caché offline.

---

## 📦 Stack

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje |
| MVVM + Clean Architecture | Arquitectura |
| Hilt | Inyección de dependencias |
| Room | Persistencia local / caché offline |
| Retrofit + OkHttp | Cliente HTTP hacia el backend |
| DataStore Preferences | Guardar sesión (token JWT) |
| Google Maps SDK | Mapa interactivo |
| FusedLocationProvider | Ubicación en tiempo real |
| Geofencing API | Activación de audio por proximidad |
| WorkManager | Sincronización offline en background |
| Jetpack Navigation | Navegación entre Fragments |
| MediaPlayer (Foreground Service) | Reproducción de audio-guías |

---

## ⚙️ Requisitos previos

- Android Studio (última estable)
- JDK 17
- Un dispositivo físico o emulador con Google Play Services
- Backend de Cool Tour corriendo (ver `README_backend.md`)
- API Key de Google Maps

---

## 📁 Estructura del proyecto

```
app/src/main/java/com/example/cool_tour/
├── CoolTourApp.kt
├── data/
│   ├── local/              # Room: entities, DAOs, AppDatabase, SessionManager
│   ├── location/           # GPS y Geofencing
│   ├── mapper/              # DTO ↔ Entity ↔ Domain
│   ├── remote/              # ApiService, DTOs (Retrofit)
│   ├── repository/          # Implementaciones de los repositorios
│   ├── service/             # AudioPlayerService (foreground)
│   └── worker/               # OfflineSyncWorker
├── di/                       # Módulos de Hilt
├── domain/
│   ├── model/                # Entidades de dominio (POI, Ruta, Usuario...)
│   ├── repository/           # Interfaces
│   └── usecase/              # Casos de uso
└── ui/
    ├── auth/                 # Login, Registro
    ├── map/                   # Mapa, selección de ruta
    ├── poi/                   # Detalle de POI
    ├── missions/               # Misiones
    ├── profile/                # Perfil
    └── qr/                     # Escaneo QR
```

---

## 🚀 Setup inicial

### 1. Clonar el proyecto y abrir en Android Studio

### 2. Configurar el `local.properties` (o `AndroidManifest.xml`) con tu API Key de Google Maps

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_API_KEY_AQUI"/>
```

### 3. Configurar la URL del backend

En `app/build.gradle.kts`, dentro de `defaultConfig`:

```kotlin
buildConfigField("String", "BASE_URL_EMULATOR", "\"http://10.0.2.2:3000/\"")
buildConfigField("String", "BASE_URL_DEVICE", "\"http://TU_IP_LOCAL:3000/\"")
```

> Encuentra tu IP local con `ip addr show | grep "inet " | grep -v 127.0.0.1` en la máquina donde corre el backend.

### 4. Permitir tráfico HTTP local

Archivo `res/xml/network_security_config.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="false">10.0.2.2</domain>
        <domain includeSubdomains="false">TU_IP_LOCAL</domain>
    </domain-config>
</network-security-config>
```

Referenciado en `AndroidManifest.xml`:
```xml
<application android:networkSecurityConfig="@xml/network_security_config" ...>
```

### 5. Sync Gradle y Run ▶️

---

## 🌐 Detección automática de entorno

El `NetworkModule` detecta si la app corre en emulador o dispositivo físico y usa la URL correcta automáticamente — no hace falta cambiar código entre pruebas:

```kotlin
private fun isEmulator(): Boolean {
    return Build.FINGERPRINT.startsWith("generic")
        || Build.MODEL.contains("Emulator")
        || Build.MODEL.contains("Android SDK built for x86")
}
```

| Entorno | URL usada |
|---|---|
| Emulador | `10.0.2.2:3000` |
| Dispositivo físico | Tu IP local de red |

---

## 🧱 Versiones de build verificadas

| Herramienta | Versión |
|---|---|
| AGP | 8.7.3 |
| Kotlin | 2.0.21 |
| KSP | 2.0.21-1.0.28 |
| Hilt | 2.52 |
| Room | 2.6.1 |
| compileSdk / targetSdk | 35 |
| minSdk | 26 |
| JVM target | 17 |

> ⚠️ Esta combinación fue validada tras resolver incompatibilidades con AGP 9.x (Hilt aún no soporta `BaseExtension` removida en AGP 9). No actualizar AGP sin verificar compatibilidad de Hilt primero.

---

## 🔑 Flujo de autenticación

1. `LoginFragment` → `POST /api/auth/login` → guarda JWT en `SessionManager` (DataStore)
2. Todas las llamadas autenticadas usan `Authorization: Bearer <token>` vía `SessionManager.obtenerToken()`
3. `RegistroFragment` → `POST /api/auth/registrar` → auto-login tras registro exitoso

---

## 🗺️ Flujo principal de la app

```
Login/Registro
    → Mapa (GET /api/pois, cacheado en Room)
    → Seleccionar ruta libre (checkboxes de POIs)
    → POST /api/rutas (guarda la ruta en backend)
    → Polilínea trazada + Geofences registrados
    → Al acercarse a un POI → Audio se reproduce automático
    → Detalle del POI → reproducción manual + descripción
    → Perfil → historial de rutas (GET /api/rutas/mis-rutas)
```

---

## 📡 Capa de red — endpoints consumidos

| Método | Endpoint | Desde |
|---|---|---|
| POST | `/api/auth/login` | `LoginViewModel` |
| POST | `/api/auth/registrar` | `RegistroViewModel` |
| GET | `/api/pois` | `MapViewModel` (vía `POIRepository.sincronizarDesdeBackend()`) |
| POST | `/api/rutas` | `RouteSelectionViewModel` |
| GET | `/api/rutas/mis-rutas` | `ProfileViewModel` |
| POST | `/api/rutas/:id/visita` | *(pendiente conectar a Geofencing)* |
| POST | `/api/cupones/validar` | `QRScanViewModel` |

Ver `endpoints_cool_tour.md` para el detalle completo del backend.

---

## 💾 Caché offline

`POIRepositoryImpl` sigue el patrón **network-first con fallback a Room**:
- Si hay conexión: trae del backend y actualiza Room
- Si no hay conexión: Room sigue sirviendo lo último cacheado
- `OfflineSyncWorker` (WorkManager) sincroniza cada hora en background

---

## 🧪 Troubleshooting común

| Problema | Causa | Solución |
|---|---|---|
| Mapa aparece gris | Falta API Key de Google Maps | Verifica `meta-data` en `AndroidManifest.xml` |
| `CLEARTEXT communication not permitted` | Falta `network_security_config.xml` | Agrégalo y referencia en el manifest |
| App no conecta desde teléfono físico | IP incorrecta o firewall | Verifica IP con `ip addr show`, abre puerto con `sudo ufw allow 3000` |
| Geofences no se activan en emulador | El emulador no simula geofencing real | Prueba en dispositivo físico |
| `kspDebugKotlin` falla | Incompatibilidad de versiones AGP/Hilt/KSP | Usar exactamente las versiones de la tabla de arriba |

---

## 🔵 Roadmap (post-MVP)

- [ ] Escaneo QR real con ML Kit (actualmente stub)
- [ ] Misiones y logros conectados a backend
- [ ] Reseñas de POIs
- [ ] Registro de locatarios (B2B)
- [ ] Sesión persistente (auto-login si ya hay token válido)
- [ ] Filtrado visual de marcadores según ruta seleccionada
