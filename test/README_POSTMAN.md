# Colección Postman - EzyVet API

Esta carpeta contiene la colección de Postman para probar todos los endpoints de la API de EzyVet.

## 📁 Archivos

- **EzyVet_API.postman_collection.json**: Colección completa con todos los endpoints organizados por categorías
- **EzyVet_Development.postman_environment.json**: Environment para desarrollo con variables pre-configuradas

## 🚀 Cómo Importar

### 1. Importar la Colección
1. Abre Postman
2. Haz clic en el botón **Import** (parte superior izquierda)
3. Selecciona el archivo `EzyVet_API.postman_collection.json`
4. La colección aparecerá en tu sidebar con el nombre "EzyVet API"

### 2. Importar el Environment (Recomendado)
1. Haz clic en **Import** nuevamente
2. Selecciona el archivo `EzyVet_Development.postman_environment.json`
3. El environment aparecerá en la esquina superior derecha
4. Selecciona "EzyVet - Development" en el dropdown de environments

## 🔧 Configuración Inicial

### ¿Environment o Variables de Colección?

**Opción A - Usar Environment (Recomendado)**
- ✅ Perfecto para gestionar múltiples configuraciones (Dev, Prod)
- ✅ Fácil cambio entre ambientes
- ✅ Variables visibles en la esquina superior derecha
- Importa `EzyVet_Development.postman_environment.json` y selecciónalo

**Opción B - Usar solo Variables de Colección**
- ✅ Más simple para pruebas rápidas
- ⚠️ Solo una configuración a la vez
- Edita las variables en: `EzyVet API` → pestaña `Variables`

### Credenciales por Defecto

El sistema crea automáticamente un usuario administrador:

```json
{
    "email": "admin@ezyvet.local",
    "password": "admin123"
}
```

**Importante**: Si intentas hacer login con un usuario que no existe (ej: "Mono@gmail.com"), obtendrás un error 500. Primero debes registrar el usuario con los endpoints de registro.

### Variables Disponibles

La colección incluye las siguientes variables que puedes modificar:

- `baseUrl`: URL base de la API (por defecto: `http://localhost:8080`)
- `authToken`: Token JWT (se actualiza automáticamente después del login)
- `ownerId`: ID del dueño de mascota
- `veterinarianId`: ID del veterinario
- `petId`: ID de la mascota
- `appointmentId`: ID de la cita
- `vaccineId`: ID de la vacuna
- `planId`: ID del plan de vacunación
- `certificateId`: ID del certificado

### Flujo de Trabajo Recomendado

1. **Autenticación**
   - Ejecuta `Authentication > Login` con las credenciales del admin:
     ```json
     {
         "email": "admin@ezyvet.local",
         "password": "admin123"
     }
     ```
   - Postman guardará automáticamente la respuesta
   - Copia el `userId` de la respuesta y guárdalo en la variable `{{ownerId}}` o `{{veterinarianId}}` según corresponda

2. **Registro** (Opcional - para crear más usuarios)
   - Usa `Register Owner` o `Register Veterinarian` para crear nuevos usuarios
   - Después puedes hacer login con esos usuarios

3. **Operaciones CRUD**
   - Todos los endpoints bajo `/api/**` requieren el token en el header `Authorization: Bearer {{authToken}}`
   - El environment ya está configurado para usar `{{authToken}}` automáticamente

## 📋 Estructura de la Colección

### 1. Authentication
- Login
- Register Owner
- Register Veterinarian

### 2. Pet Owners
- List All Owners

### 3. Veterinarians
- List All Veterinarians

### 4. Pets
- List All Pets
- Get Pet by ID
- List Pets by Owner
- Create Pet

### 5. Appointments
- List All Appointments
- List by Owner/Veterinarian/Pet
- Create Appointment
- Complete Appointment
- Delete Appointment

### 6. Vaccines
- List All Vaccines
- Create Vaccine

### 7. Vaccination Plans
- Create Vaccination Plan
- List by Pet/Veterinarian
- Mark as Completed
- Delete Plan

### 8. Medical History
- List by Pet
- Create Medical Record

### 9. Vaccination Certificates
- Get Certificate by ID
- List by Pet

## 💡 Consejos de Uso

1. **Guardar IDs**: Después de crear recursos (mascotas, citas, etc.), guarda sus IDs en las variables de colección para usarlos en otros requests

2. **Scripts de Test**: Puedes agregar scripts de test en cada request para validar respuestas automáticamente

3. **Ambientes**: Considera crear diferentes ambientes (Development, Production) para gestionar múltiples configuraciones

4. **Ejemplos de Respuestas**: Ejecuta los requests y guarda ejemplos de respuestas para documentación

## 🔐 Autenticación

Todos los endpoints (excepto `/auth/*`) requieren un token JWT en el header:

```
Authorization: Bearer <tu-token-jwt>
```

El token se obtiene al hacer login y tiene una duración de 60 minutos por defecto.

## 📝 Ejemplos de Payloads

### Crear Mascota
```json
{
    "ownerId": "owner-id-here",
    "name": "Max",
    "species": "Perro",
    "breed": "Labrador",
    "birthDate": "2020-05-15",
    "microchipId": "123456789012345",
    "neutered": true
}
```

### Crear Cita
```json
{
    "ownerId": "owner-id-here",
    "petId": "pet-id-here",
    "veterinarianId": "vet-id-here",
    "type": "CONSULTATION",
    "appointmentDate": "2026-06-10T10:00:00",
    "reason": "Revisión anual",
    "notes": "Primera visita del año"
}
```

## 🐛 Resolución de Problemas

- **ECONNREFUSED (127.0.0.1:8080)**: El servidor no está corriendo. Ejecuta `mvn spring-boot:run` en la raíz del proyecto
- **500 Internal Server Error en /auth/login**: El usuario no existe en la base de datos. Usa las credenciales del admin (`admin@ezyvet.local` / `admin123`) o registra el usuario primero
- **401 Unauthorized**: Tu token ha expirado o no es válido. Ejecuta el login nuevamente
- **404 Not Found**: Verifica que el ID del recurso sea correcto
- **400 Bad Request**: Revisa que el payload tenga todos los campos requeridos y con el formato correcto

## 📧 Contacto

Para más información sobre la API, consulta el README principal del proyecto.
