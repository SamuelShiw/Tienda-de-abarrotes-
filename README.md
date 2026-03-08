# AbarrotesApp

Aplicación móvil Android desarrollada para la gestión interna de una tienda de abarrotes.

El sistema permite controlar trabajadores, productos, inventario, reposición de stock, caja y ventas, utilizando almacenamiento local con SQLite, arquitectura MVVM y navegación basada en roles.

Este proyecto fue desarrollado con fines educativos para aplicar conceptos de desarrollo móvil en Android, persistencia local, lógica de negocio y comunicación entre Activities y Fragments.

---

## Objetivo del proyecto

Desarrollar una aplicación Android que implemente:

- almacenamiento local con SQLite
- operaciones CRUD
- comunicación entre Activities y Fragments
- persistencia correcta de datos
- lógica de negocio por roles
- funcionamiento estable y sin errores lógicos principales

---

## Tecnologías utilizadas

- Android Studio
- Java
- SQLite
- XML
- RecyclerView
- Fragments
- MVVM
- Material Components
- ZXing Android Embedded

---

## Arquitectura del proyecto

El proyecto está organizado con una arquitectura basada en **MVVM (Model - View - ViewModel)** para separar responsabilidades y facilitar el mantenimiento.

### Estructura principal

```text
com.tienda.abarrotes
│
├── data
│   ├── dao
│   ├── db
│   └── repository
│
├── model
│
├── ui
│   ├── login
│   ├── admin
│   ├── reponedor
│   ├── cajero
│   ├── adapters
│   └── common
│
├── utils
│
└── viewmodel
```
## Roles del sistema

La aplicación maneja tres roles principales.

# Administrador

Tiene acceso completo al sistema.

Puede:

registrar trabajadores

editar trabajadores

desactivar trabajadores

visualizar QR del trabajador

registrar productos

editar productos

desactivar productos

visualizar detalle de producto

generar código interno y código de barras

ingresar mercadería al almacén

visualizar inventario

ver movimientos de stock

# Reponedor

Encargado del abastecimiento interno.

Puede:

ver productos disponibles

revisar stock en almacén y tienda

identificar productos con stock bajo

mover productos del almacén a la tienda

ver historial de reposiciones

No puede:

registrar trabajadores

registrar productos

modificar precios

vender productos

# Cajero

Encargado del proceso de venta.

Puede:

abrir caja

escanear código de barras con cámara

buscar productos por código de barras

agregar productos al carrito

registrar ventas

emitir boleta o factura

cerrar caja

ver resumen de caja

No puede:

modificar inventario

registrar trabajadores

registrar productos

## Módulos del sistema
1. Login

Pantalla de autenticación por usuario y contraseña.

Funciones:

validación de credenciales

redirección según rol

mantenimiento de sesión

Usuarios demo iniciales:

admin / admin123

reponedor / reponedor123

cajero / cajero123

2. Gestión de trabajadores

Módulo exclusivo del administrador.

Funciones:

registrar trabajador

editar trabajador

desactivar trabajador

listar trabajadores

generar QR lógico por DNI y rol

visualizar QR del trabajador

Datos registrados:

nombres

apellidos

DNI

teléfono

usuario

contraseña

rol

estado

fecha de registro

3. Gestión de productos

Módulo exclusivo del administrador.

Funciones:

registrar producto

editar producto

desactivar producto

listar productos

ver detalle del producto

generar código interno automático

generar código de barras automático

visualizar barcode del producto

Datos registrados:

nombre

categoría

marca

unidad de medida

precio de compra

precio de venta

stock en almacén

stock en tienda

stock mínimo

estado

fecha de registro

4. Inventario

Módulo del administrador.

Funciones:

visualizar inventario

ingresar mercadería solo al almacén

registrar movimientos de stock

ver historial de movimientos

Regla lógica aplicada:

el administrador solo ingresa mercadería al almacén

no puede enviar directamente productos a tienda

5. Reposición

Módulo del reponedor.

Funciones:

ver productos

ver productos con stock bajo

mover stock del almacén a la tienda

registrar reposiciones

ver historial de reposiciones

Regla lógica aplicada:

el reponedor mueve stock de:

ALMACÉN → TIENDA

6. Caja

Módulo del cajero.

Funciones:

abrir caja

registrar monto inicial

validar que solo exista una caja abierta por cajero

cerrar caja

registrar monto final

visualizar resumen del turno

Regla lógica aplicada:

no se puede vender sin caja abierta

7. POS y ventas

Módulo principal del cajero.

Funciones:

buscar producto por código de barras

escanear producto con cámara

agregar productos al carrito

quitar productos del carrito

calcular subtotal

calcular IGV

calcular total

elegir comprobante

boleta

factura

registrar venta

registrar detalle de venta

descontar stock de tienda

actualizar total de la caja

Regla lógica aplicada:

el cajero vende desde stock en tienda

no desde almacén

Base de datos

La aplicación utiliza SQLite local.

Tablas principales

trabajadores

productos

movimientos_stock

cajas

ventas

detalle_venta

Flujo lógico del stock

La aplicación respeta el flujo real del negocio:

Administrador → almacén
Reponedor → tienda
Cajero → venta

Esto evita inconsistencias y mantiene una lógica operativa coherente.

Funcionalidades implementadas

login por rol

control de sesión

CRUD de trabajadores

CRUD de productos

generación de QR para trabajadores

generación de código de barras para productos

inventario con SQLite

ingreso de mercadería al almacén

reposición a tienda

apertura y cierre de caja

POS de ventas

carrito de compras

emisión de boleta o factura

escaneo con cámara

persistencia local

comunicación entre Activities y Fragments

Interfaz de usuario

La interfaz fue diseñada con una estética limpia y moderna inspirada en interfaces móviles actuales:

fondos claros

tarjetas con bordes suaves

botones destacados

campos de entrada redondeados

diseño centrado y minimalista

navegación simple por paneles

Dependencias destacadas

Para el escaneo de códigos se utilizó:

com.journeyapps:zxing-android-embedded

com.google.zxing:core

Prueba funcional recomendada
Flujo completo sugerido

ingresar como administrador

registrar producto

ingresar mercadería al almacén

ingresar como reponedor

mover stock a tienda

ingresar como cajero

abrir caja

escanear código de barras o ingresar código manual

agregar producto al carrito

registrar venta

cerrar caja

revisar resumen

Estado actual del proyecto

El sistema se encuentra funcional en su versión académica V1.

Incluye:

estructura completa

base de datos local

flujo por roles

operaciones CRUD

lógica de stock

control de caja

ventas

scanner con cámara

barcode visual

QR visual del trabajador

Posibles mejoras futuras

integración completa de fotografías de trabajadores

reportes avanzados

búsqueda por nombre de producto

filtros visuales

impresión real de comprobantes

sincronización en la nube

panel estadístico

mejora visual adicional del dashboard

## Autor

Jose Samuel Quispe Mamani

Carrera: Ingeniería de Software con Inteligencia Artificial
Institución: SENATI
Año: 2026

# Propósito académico

Este proyecto fue desarrollado con fines educativos para demostrar competencias en:

programación Android

diseño de interfaces

persistencia con SQLite

arquitectura MVVM

implementación de lógica de negocio

desarrollo de aplicaciones móviles funcionales

# Licencia

Proyecto desarrollado con fines educativos.