# AbarrotesApp

Aplicación móvil Android desarrollada para gestionar el funcionamiento interno de una tienda de abarrotes.  
El sistema permite administrar trabajadores, productos, inventario, reposición de stock y ventas en caja mediante diferentes roles de usuario.

Este proyecto fue desarrollado con fines educativos para aplicar arquitectura de software, persistencia local con SQLite y comunicación entre Activities y Fragments en Android.

---

# Tecnologías utilizadas

- Android Studio
- Java
- SQLite (base de datos local)
- Arquitectura MVVM
- RecyclerView
- Fragments
- XML UI
- Material Components

---

# Arquitectura del proyecto

El proyecto sigue una estructura organizada basada en **MVVM (Model - View - ViewModel)** para separar responsabilidades.


com.tienda.abarrotes
│
├── data
│ ├── dao
│ ├── db
│ └── repository
│
├── model
│
├── ui
│ ├── admin
│ ├── cajero
│ ├── reponedor
│ ├── login
│ ├── adapters
│ └── common
│
├── utils
│
└── viewmodel


---

# Roles del sistema

La aplicación funciona con **tres roles principales**:

## Administrador

Tiene acceso completo al sistema.

Puede:

- Registrar trabajadores
- Editar trabajadores
- Desactivar trabajadores
- Generar QR de trabajador
- Registrar productos
- Editar productos
- Eliminar productos
- Administrar inventario
- Registrar ingreso de mercadería al almacén
- Ver movimientos de stock

---

## Reponedor

Encargado de trasladar productos del almacén a los estantes de la tienda.

Puede:

- Ver lista de productos
- Ver stock disponible
- Ver productos con stock bajo
- Registrar reposición desde almacén hacia tienda
- Ver historial de reposiciones

No puede:

- Crear productos
- Editar productos
- Registrar trabajadores
- Realizar ventas

---

## Cajero

Encargado de realizar ventas a clientes.

Puede:

- Abrir caja
- Escanear productos mediante código de barras
- Agregar productos al carrito
- Registrar ventas
- Emitir comprobantes
    - Boleta
    - Factura
- Cerrar caja
- Ver resumen de ventas

No puede:

- Modificar inventario
- Registrar productos
- Registrar trabajadores

---

# Módulos principales

## Login

Pantalla de autenticación donde el trabajador ingresa con:

- Usuario
- Contraseña

El sistema redirige automáticamente al panel correspondiente según su rol.

---

## Panel Administrador

Contiene módulos para:

- Gestión de trabajadores
- Gestión de productos
- Gestión de inventario
- Registro de ingreso de mercadería

---

## Panel Reponedor

Permite:

- Visualizar productos
- Detectar productos con stock bajo
- Reponer productos desde almacén hacia tienda

---

## Panel Cajero

Funciona como un **POS básico (Point of Sale)**.

Permite:

- Abrir caja
- Registrar ventas
- Escanear códigos de barras
- Agregar productos al carrito
- Emitir comprobantes
- Cerrar caja

---

# Base de datos

La aplicación utiliza **SQLite local** para almacenar toda la información.

Tablas principales:

- trabajadores
- productos
- movimientos_stock
- cajas
- ventas
- detalle_venta

---

# Funcionalidades implementadas

✔ Login por rol  
✔ CRUD de trabajadores  
✔ CRUD de productos  
✔ Gestión de inventario  
✔ Ingreso de mercadería al almacén  
✔ Reposición de productos hacia tienda  
✔ Registro de ventas  
✔ Control de caja  
✔ Generación de QR para trabajadores  
✔ Generación de código de barras para productos  
✔ Comunicación entre Activities y Fragments  
✔ Persistencia con SQLite

---

# Interfaz de usuario

La interfaz fue diseñada con un estilo moderno inspirado en interfaces móviles actuales:

- Layouts centrados
- Tarjetas (cards)
- Botones suaves
- Colores claros
- Diseño minimalista

---

# Objetivo del proyecto

Aplicar los siguientes conceptos de desarrollo móvil:

- Arquitectura MVVM
- Persistencia local con SQLite
- Diseño de interfaces con XML
- Uso de RecyclerView
- Comunicación entre componentes
- Implementación de lógica de negocio

---

# Autor

Proyecto desarrollado por:

**Jose Samuel Quispe Mamani**

Carrera: Ingeniería de Software con Inteligencia Artificial  
Institución: SENATI  
Año: 2026

---

# Licencia

Proyecto desarrollado con fines educativos.