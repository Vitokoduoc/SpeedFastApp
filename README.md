<p align="center">
  <img src="https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png" width="300"/>
</p>

---

# SpeedFast – Diseño Orientado a Objetos en Java – Nota Sumativa

---

## 📌 Contexto del problema

**SpeedFast** es una empresa de reparto a domicilio que gestiona distintos tipos de pedidos, cada uno con reglas propias para su asignación y estimación de tiempo de entrega:

- **Pedido de Comida**: considera tiempos asociados a preparación y traslado.
- **Pedido de Encomienda**: incorpora tiempos adicionales por validación y manipulación.
- **Pedido Express**: prioriza rapidez, con tiempos base reducidos.

Además, el sistema debe permitir **despachar**, **cancelar** pedidos y **mantener un historial** de entregas realizadas.

---

## 🎯 Objetivos del proyecto

- Diseñar una **clase abstracta** que represente el concepto general de pedido.
- Reutilizar atributos y comportamientos comunes mediante **herencia**.
- Aplicar **polimorfismo** mediante sobrescritura y sobrecarga de métodos.
- Implementar **interfaces** para desacoplar responsabilidades del sistema.
- Diseñar una estructura **modular, clara y alineada a buenas prácticas**.
- Simular el flujo completo del sistema desde una clase `Main`.

---

## 🧩 Conceptos aplicados

- Programación Orientada a Objetos (POO)
- Abstracción (`abstract`)
- Herencia (`extends`)
- Polimorfismo (sobrescritura y sobrecarga)
- Interfaces (`interface`)
- Encapsulamiento
- Validaciones de atributos
- Uso de `enum`
- Colecciones (`ArrayList`)
- Documentación **JavaDoc**

---

## 🗂️ Estructura del proyecto

```
src
└── main
    └── java
        └── com
            ├── app
            │   └── Main.java
            ├── model
            │   ├── Pedido.java
            │   ├── PedidoComida.java
            │   ├── PedidoEncomienda.java
            │   └── PedidoExpress.java
            ├── interfaces
            │   ├── Despachable.java
            │   ├── Cancelable.java
            │   └── Rastreable.java
            └── controlador
                └── ControladorDeEnvios.java
```

---

## 🏗️ Diseño general del sistema

### 🔹 Clase abstracta `Pedido`
Representa el concepto base de pedido y define:

**Atributos comunes**
- `idPedido`
- `direccionEntrega`
- `distanciaKm`
- `tipoPedido`
- `repartidor`
- `cancelado`

**Comportamientos**
- `mostrarResumen()` → método implementado.
- `calcularTiempoEntrega()` → método abstracto.
- `asignarRepartidor()` → método abstracto (asignación automática).
- `asignarRepartidor(String nombre)` → método sobrecargado (asignación manual).

Implementa las interfaces:
- `Despachable`
- `Cancelable`

---

### 🔹 Subclases concretas
- `PedidoComida`
- `PedidoEncomienda`
- `PedidoExpress`

Cada subclase:
- Sobrescribe `calcularTiempoEntrega()` con reglas propias.
- Sobrescribe `asignarRepartidor()` según el tipo de pedido.

---

### 🔹 Interfaces
- **Despachable**: define la operación de despacho.
- **Cancelable**: define la cancelación de un pedido.
- **Rastreable**: define la visualización del historial.

Estas interfaces permiten desacoplar responsabilidades y mejorar la mantenibilidad del sistema.

---

### 🔹 ControladorDeEnvios
Clase responsable de:
- Registrar pedidos en un historial (`ArrayList<Pedido>`).
- Mostrar el historial por consola.
- Implementar la interfaz `Rastreable`.

---

### 🔹 Clase `Main`
Clase de ejecución que simula el funcionamiento completo del sistema:

- Creación de distintos tipos de pedidos.
- Asignación automática y manual de repartidores.
- Cálculo del tiempo estimado.
- Despacho y cancelación de pedidos.
- Visualización del historial de entregas.

---

## ▶️ Ejecución del proyecto

1. Abrir el proyecto en **IntelliJ IDEA**.
2. Verificar que el SDK de Java esté correctamente configurado.
3. Ejecutar la clase:

```
com.app.Main
```

---

## 🖥️ Ejemplo de salida por consola

```
[PedidoEncomienda]
Pedido #102
Dirección: Av. Santa Rosa 567
Distancia: 7 km
Repartidor asignado: Daniela Tapia
Tiempo estimado: 30 minutos
Pedido despachado correctamente.

Cancelando Pedido Express #103...
→ Pedido cancelado exitosamente.

Historial:
- PedidoComida #101 – entregado por Luis Díaz
- PedidoEncomienda #102 – entregado por Daniela Tapia
```

---

## 📊 Diagrama UML (simplificado)

```
                 ┌───────────────────────────────┐
                 │       Pedido (abstract)       │
                 ├───────────────────────────────┤
                 │ - idPedido                    │
                 │ - direccionEntrega            │
                 │ - distanciaKm                 │
                 │ - tipoPedido                  │
                 │ - repartidor                  │
                 │ - cancelado                   │
                 ├───────────────────────────────┤
                 │ + mostrarResumen()             │
                 │ + asignarRepartidor()          │
                 │ + asignarRepartidor(String)    │
                 │ + calcularTiempoEntrega()      │
                 └───────────────▲───────────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
┌────────────────────┐ ┌──────────────────────┐ ┌──────────────────────┐
│   PedidoComida     │ │  PedidoEncomienda    │ │   PedidoExpress      │
└────────────────────┘ └──────────────────────┘ └──────────────────────┘
```

---

## 👨‍💻 Autor

**Víctor Valenzuela**  
Escuela de Informática y Telecomunicaciones  
**Duoc UC**
