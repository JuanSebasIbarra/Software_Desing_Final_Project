# Ezy vet

Suite completa (backend Spring Boot + frontend React + cliente CLI) para gestionar una clínica veterinaria. Permite registrar dueños y veterinarios, administrar mascotas, agendar/cancelar citas, generar certificados PDF al vacunar, definir planes de vacunación y consumir todo desde un panel visual por rol.

## Contenido
1. [Arquitectura](#arquitectura)
2. [Diagramas](#diagramas)
3. [Requisitos](#requisitos)
4. [Backend](#backend)
5. [Frontend](#frontend)
6. [Cliente CLI](#cliente-cli)
7. [Variables y auto push](#variables-y-auto-push)
8. [Patrones de diseño](#patrones-de-diseño)
9. [Estructura y contribuciones](#estructura-y-contribuciones)

## Arquitectura
- **Backend**: Spring Boot 3.4 (Java 17) + MongoDB. Expone APIs REST para dueños, veterinarios, mascotas, citas, historiales, planes y certificados. Genera certificados en PDF y puede enviar notificaciones/recordatorios configurables.
- **Frontend**: React + Vite. Dos tableros:  
  - Dueño: registra mascotas, ve próximas citas, planes programados y certificados emitidos.  
  - Veterinario: agrega pacientes por ID, agenda y cancela citas, crea planes de vacunación y los marca como completados.
- **CLI**: cliente de consola que interactúa con la misma API para pruebas rápidas.

## Diagramas

### Diagrama de Contexto del Sistema

```mermaid
flowchart LR
    Admin(["**Administrador**\n[Persona]\n\nGestiona usuarios, roles,\nauditorías y configuraciones."])
    Vet(["**Veterinario**\n[Persona]\n\nAtiende citas, registra\nhistorias clínicas y vacunas."])
    Owner(["**Dueño de Mascota**\n[Persona]\n\nConsulta el historial,\nrecibe alertas y agenda\ncitas."])
    System["**Sistema EzyVet**\n[Sistema de Software]\n\nPlataforma centralizada\npara la gestión clínica\nveterinaria, control de\npacientes, agendamiento\nde citas y emisión de\ncertificados."]
    GW["**Gateway de Notificaciones**\n[Sistema Externo]\n\nServicio encargado del\ndespacho de correos\ntransaccionales y SMS."]

    Admin -->|Administra accesos y configuraciones| System
    Vet -->|Registra atención médica y vacunas| System
    System -->|Envía alertas y recordatorios| GW
    GW -.->|Despacha correos / SMS| Owner
    Owner -->|Gestiona citas y consulta historial| System
```

### Diagrama de Infraestructura / Despliegue

```mermaid
flowchart TB
    subgraph Clients["Client Devices – Frontend / Acceso"]
        WB["Web Browser\nEzyVet Web UI\n(HTML / CSS / ReactJS)"]
        MA["Mobile App\nEzyVet Mobile UI\n(Android/iOS PWA / WebView)"]
    end

    subgraph AppServer["Application Server – Spring Boot Enterprise Node\nLinux Ubuntu 20.04 · Java 17 · Spring Boot 3.x"]
        subgraph API["EzyVet API NLSI – Monolito Modular"]
            CL["Controllers Layer\n(REST Endpoints)"]
            SL["Service Layer\n(Business Logic)"]
            RL["Repository Layer\n(Mongo Repositories)"]
        end
        CFG["Configuration Module\n(CORS / Beans / Rate Limiting)"]
        SEC["Security & Filter Module\n(JWTFilter / PasswordEncoder)"]
    end

    subgraph Ext["External Services / Integraciones de Terceros"]
        PDF["PDF Generator Service\n(Thymeleaf / External\nMicroservices)"]
        PUSH["Push Notifications\n(WhatsApp API / Firebase)"]
        EMAIL["Email Provider\n(SMTP / Mailchimp / Gmail API)"]
    end

    subgraph DB["Database Cluster – Persistencia\nMongoDB Atlas / Local Replica Set"]
        C1[(Usuarios Collection)]
        C2[(Mascotas Collection)]
        C3[(Citas Collection)]
        C4[(Vacunas Collection)]
        C5[(Historias Collection)]
        C6[(Certificados Collection)]
        C7[(Tokens Collection)]
    end

    WB -->|HTTPS :443| AppServer
    MA -->|HTTPS :443| AppServer
    CL --> SL --> RL
    CFG --- API
    SEC --- API
    RL -->|MongoDB Protocol :27017| DB
    AppServer -->|HTTPS PDF Generation| PDF
    AppServer -->|HTTPS Mail/Push API| PUSH
    AppServer -->|SMTP :587| EMAIL
```

### Diagrama de Componentes

```mermaid
flowchart LR
    subgraph S1["1. Acceso y Seguridad"]
        AUTH["&lt;&lt;component&gt;&gt;\nAutenticacion"]
        GU0["&lt;&lt;component&gt;&gt;\nGestionUsuarios"]
    end

    subgraph S2["2. Módulos Core EzyVet"]
        GU["&lt;&lt;component&gt;&gt;\nGestionUsuarios"]
        GM["&lt;&lt;component&gt;&gt;\nGestionMascotas"]
        GC["&lt;&lt;component&gt;&gt;\nGestionCitas"]
        GH["&lt;&lt;component&gt;&gt;\nGestionHistorias"]
        GCert["&lt;&lt;component&gt;&gt;\nGestionCertificados"]
        NOT["&lt;&lt;component&gt;&gt;\nNotificaciones"]
    end

    subgraph S3["3. Interfaces del Sistema"]
        IAS["&lt;&lt;interface&gt;&gt;\nIAutenticacionService"]
        ICS["&lt;&lt;interface&gt;&gt;\nICitaService"]
        IUS["&lt;&lt;interface&gt;&gt;\nIUsuarioService"]
        IMS["&lt;&lt;interface&gt;&gt;\nIMascotaService"]
        INS["&lt;&lt;interface&gt;&gt;\nINotificacionService"]
        IHS["&lt;&lt;interface&gt;&gt;\nIHistorialService"]
        ICertS["&lt;&lt;interface&gt;&gt;\nICertificadoService"]
    end

    subgraph S4["4. Infraestructura y Datos"]
        SC["&lt;&lt;component&gt;&gt;\nSistemaCorreo"]
        BD["&lt;&lt;component&gt;&gt;\nBaseDatos"]
        IRA["&lt;&lt;interface&gt;&gt;\nIRepositoryAccess"]
        ISMTP["&lt;&lt;interface&gt;&gt;\nISMTPAccess"]
    end

    AUTH -->|validaCredenciales| IAS
    S1 --> S2
    GU -->|persistenciaUsuarios| IUS
    GM --> IMS
    GC --> ICS
    GC -->|asociaMascota| GM
    GC -->|asociaVeterinario| GU
    GC -->|solicitaRecordatorio| NOT
    GH -->|vinculaPaciente| GC
    GH -->|almacenaDatos| IHS
    GCert -->|firmaVeterinario| GU
    GCert -->|identificaPaciente| GM
    GCert --> ICertS
    NOT --> INS
    IUS --> IRA
    IMS --> IRA
    ICS --> IRA
    IHS --> IRA
    ICertS --> IRA
    INS --> ISMTP
    BD --> IRA
    SC --> ISMTP
    NOT -->|persistenciaCita| ICS
    NOT -->|persistenciaHistorias| IHS
    NOT -->|persistenciaCertificados| ICertS
    NOT -->|despachoMensajes| ISMTP
```

### Diagrama de Arquitectura en Capas

```mermaid
flowchart TB
    subgraph Sec["Seguridad / Utils"]
        SecCfg[SecurityConfig]
        JWT[JwtUtil]
        PE[PasswordEncoder]
    end

    subgraph Controllers["Capa Presentación – Componentes REST / API"]
        AC[AuthController]
        UC[UsuarioController]
        MC[MascotaController]
        CC[CitaController]
        VC[VacunacionController]
        CertC[CertificadoController]
        NC[NotificacionController]
    end

    subgraph Services["Capa del Negocio – Interfaces y Flujos de Servicios"]
        AS[AuthService]
        US[UsuarioService]
        MS[MascotaService]
        CS[CitaService]
        VS[VacunacionService]
        CertS[CertificadoService]
        NS[NotificacionService]
    end

    subgraph Repos["Capa de Acceso a Datos – Componentes DAO / Repository"]
        UR[UsuarioRepository]
        MR[MascotaRepository]
        CR[CitaRepository]
        RVR[RegistroVacunacionRepository]
        VR[VacunaRepository]
        RCR[RegistroClinicoRepository]
        CertR[CertificadoRepository]
        NR[NotificacionRepository]
    end

    subgraph GW["Gateways Externos"]
        ES[EmailService]
        SS[SmsService]
    end

    subgraph MongoDB["Almacenamiento de Datos – Gestor de Persistencia"]
        DB[("MongoDB Cluster\n[EzyVet Database]")]
    end

    SecCfg --> AC
    SecCfg -.-> JWT
    SecCfg -.-> PE
    Controllers --> Services
    AS --> UR
    US --> UR
    MS --> MR
    CS --> CR
    VS --> RVR
    VS --> VR
    NS --> NR
    CertS --> CertR
    CertS --> RCR
    NS --> ES
    NS --> SS
    Repos --> MongoDB
```

### Diagrama de Clases

```mermaid
classDiagram
    class UserAccount {
        +String id
        +String email
        +String fullName
        +String password
        +Role role
        +boolean enabled
        +String referenceId
        +LocalDateTime createdAt
    }
    class PetOwner {
        +String id
        +String userId
        +String phone
        +String address
    }
    class Veterinarian {
        +String id
        +String userId
        +String licenseNumber
        +String specialization
    }
    class Pet {
        +String id
        +String ownerId
        +String name
        +PetSpecies species
        +String breed
        +LocalDate birthDate
        +String microchipId
        +boolean neutered
        +LocalDateTime createdAt
    }
    class Vaccine {
        +String id
        +String name
        +String manufacturer
        +String description
        +int doseIntervalDays
    }
    class Appointment {
        +String id
        +String ownerId
        +String petId
        +String veterinarianId
        +AppointmentType type
        +AppointmentStatus status
        +LocalDateTime appointmentDate
        +String reason
        +String vaccineId
        +String notes
        +boolean reminderSent
    }
    class VaccinationPlan {
        +String id
        +String petId
        +String veterinarianId
        +String vaccineId
        +LocalDate scheduledDate
        +boolean completed
        +LocalDateTime createdAt
    }
    class VaccinationCertificate {
        +String id
        +String petId
        +String veterinarianId
        +String vaccineId
        +String appointmentId
        +LocalDate vaccinationDate
        +LocalDate nextDoseDate
        +String pdfPath
        +LocalDateTime issuedAt
    }
    class MedicalHistoryRecord {
        +String id
        +String petId
        +String veterinarianId
        +String appointmentId
        +String diagnosis
        +String treatment
        +String notes
        +LocalDateTime recordedAt
    }
    class Role {
        <<enumeration>>
        OWNER
        VETERINARIAN
        ADMIN
    }
    class AppointmentStatus {
        <<enumeration>>
        SCHEDULED
        COMPLETED
        CANCELLED
    }
    class AppointmentType {
        <<enumeration>>
        CONSULTATION
        VACCINATION
        CHECKUP
        EMERGENCY
    }
    class PetSpecies {
        <<enumeration>>
        DOG
        CAT
        BIRD
        RABBIT
        OTHER
    }

    UserAccount "1" -- "0..1" PetOwner : referencia
    UserAccount "1" -- "0..1" Veterinarian : referencia
    UserAccount --> Role
    PetOwner "1" --> "*" Pet : posee
    Pet --> PetSpecies
    Veterinarian "1" --> "*" Appointment : atiende
    Pet "1" --> "*" Appointment : tiene
    Pet "1" --> "*" VaccinationPlan : tiene
    Pet "1" --> "*" VaccinationCertificate : tiene
    Pet "1" --> "*" MedicalHistoryRecord : tiene
    Appointment --> AppointmentStatus
    Appointment --> AppointmentType
    Appointment "1" --> "0..1" VaccinationCertificate : genera
    Vaccine "1" --> "*" VaccinationPlan : usado en
    Vaccine "1" --> "*" Appointment : aplicada en
```

## Requisitos
- Java 17+
- Maven 3.9+
- Node.js 20+ / npm 10+
- MongoDB (local o Atlas)

## Backend
1. **Configura variables mínimas**
   ```bash
   export MONGODB_URI=mongodb://localhost:27017/vetcarepro
   export JWT_SECRET=dW5TZWNyZXRvU3VwZXJMYXJnb1NlZ3VybzEyMzQ1Ng==
   ```
2. **Compila y empaqueta**
   ```bash
   mvn clean package -DskipTests
   ```
3. **Ejecuta**
   ```bash
   mvn spring-boot:run
   ```
   El API queda en `http://localhost:8080` (ajusta `SERVER_PORT` si lo necesitas).

### Endpoints destacados
- Autenticación: `POST /auth/login`, `POST /auth/register-owner`, `POST /auth/register-veterinarian`.
- Mascotas/dueños/veterinarios: `GET/POST /api/pets`, `GET /api/owners`, `GET /api/veterinarians`.
- Citas: `GET /api/appointments`, `GET /api/appointments/veterinarian/{id}`, `GET /api/appointments/owner/{id}`, `POST /api/appointments`, `DELETE /api/appointments/{id}`, `POST /api/appointments/{id}/complete`.
- Planes de vacunación: `POST /api/vaccination-plans`, `GET /api/vaccination-plans/pet/{id}`, `GET /api/vaccination-plans/veterinarian/{id}`, `POST /api/vaccination-plans/{id}/complete`, `DELETE /api/vaccination-plans/{id}`.
- Historial y certificados: `/api/medical-history`, `/api/vaccination-certificates/{id}`, `/api/vaccination-certificates/pet/{id}`.

## Frontend
1. Instala dependencias:
   ```bash
   cd frontend
   npm install
   ```
2. Copia `.env.example` a `.env` y ajusta `VITE_API_BASE` si el backend no corre en `http://localhost:8080`.
3. Dev server:
   ```bash
   npm run dev
   ```
   Abre el enlace que imprime Vite (por defecto `http://localhost:5173`).
4. Build producción:
   ```bash
   npm run build
   ```

## Cliente CLI
Ubicado en `src/main/java/com/vetcarepro/cli`. Para probarlo:
```bash
mvn spring-boot:run -Dspring.main.web-application-type=none
```
(o ejecuta `com.ezyvet.cli.TerminalClient` desde tu IDE).

## Variables y auto push
- `MONGODB_URI` y `MONGODB_DB`: conexión a MongoDB.
- `JWT_SECRET` y `JWT_EXPIRATION_MINUTES`: firma y vigencia del token.
- `CERTIFICATE_STORAGE_PATH`: carpeta para PDFs (por defecto `certificates/`).
- `REMINDER_*`: ventanas para recordatorios de citas/vacunas.
- `NOTIFICATION_CHANNELS`: canales permitidos (EMAIL, WHATSAPP, SMS).
- `GIT_AUTO_PUSH_*`: controla el watcher que ejecuta `scripts/auto-push.sh` (desactívalo con `GIT_AUTO_PUSH_ENABLED=false`).

## Patrones de diseño
1. **Builder** – `VaccinationCertificateBuilder` crea certificados consistentes a partir de una cita.
2. **Factory** – `NotificationChannelFactory` resuelve dinámicamente el canal de notificación.
3. **Facade** – `EmailClientFacade` abstrae `JavaMailSender` para el canal de correo.
4. **Singleton (Spring)** – servicios como `PdfGeneratorService` o `VaccinationPlanService` se inyectan una sola vez.

## Estructura y contribuciones
```
├─ src/main/java/com/vetcarepro/      # Backend
├─ src/main/resources/static/         # Panel HTML legacy (referencia)
├─ frontend/                          # Nuevo panel React
├─ scripts/auto-push.sh               # Script usado por el watcher Git
└─ certificates/                      # PDFs generados
```

1. Crea una rama o fork.
2. Ejecuta `mvn clean package` y `npm run build` antes de abrir un PR.
3. Describe claramente el cambio y adjunta evidencia (logs, capturas, etc.).

---
**Demo rápida**:  
`mvn spring-boot:run` → `cd frontend && npm run dev` → abre el navegador y prueba ambos tableros registrando usuarios, mascotas y agendas.
