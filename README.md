<p align="center">
  <img src="https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png" width="300"/>
</p>

---
# SpeedFast – Sistema de Gestión de Entregas (OOP, Concurrencia, GUI & JDBC)

---

## 📌 Contexto del problema

**SpeedFast** es una empresa de reparto a domicilio que gestiona distintos tipos de pedidos, cada uno con reglas propias para su asignación y estimación de tiempo de entrega:

- **Pedido de Comida**: considera tiempos asociados a preparación y traslado.
- **Pedido de Encomienda**: incorpora tiempos adicionales por validación y manipulación.
- **Pedido Express**: prioriza rapidez, con tiempos base reducidos.

Este proyecto evolucionó desde una simulación en consola hasta una **aplicación de escritorio completa**, capaz de gestionar pedidos, coordinar repartidores mediante programación concurrente, y persistir toda la información transaccional en una base de datos relacional.

---

## 🎯 Objetivos y Evolución del Proyecto

El sistema fue desarrollado de manera iterativa, cumpliendo con los siguientes hitos:
1. **Diseño Orientado a Objetos**: Creación de una arquitectura sólida usando clases abstractas, herencia, polimorfismo e interfaces para modelar las reglas de negocio.
2. **Concurrencia**: Implementación de hilos (`Runnable`) para simular la ejecución de múltiples entregas en paralelo sin bloqueos.
3. **Interfaz Gráfica (GUI)**: Desarrollo de ventanas interactivas y modulares utilizando Java Swing.
4. **Persistencia de Datos (CRUD)**: Integración con base de datos MySQL mediante JDBC y el patrón DAO (Data Access Object) para garantizar el almacenamiento seguro de la información.

---

## 🧩 Conceptos y Tecnologías aplicadas

- **Java SE 17+**
- **POO**: Abstracción (`abstract`), Herencia (`extends`), Polimorfismo, Interfaces.
- **Concurrencia**: `Thread`, `Runnable`, `ExecutorService`.
- **Patrones de Diseño**: Data Access Object (DAO), Modelo-Vista-Controlador (MVC adaptado).
- **Frontend de Escritorio**: Java Swing (`JFrame`, `JPanel`, `JTable`, `JComboBox`).
- **Base de Datos**: MySQL 8.0+, JDBC (Java Database Connectivity), sentencias preparadas (`PreparedStatement`).
- **Documentación**: JavaDoc y control de versiones con GitHub.

---

## 🗂️ Estructura del proyecto

El código está organizado en capas lógicas para asegurar alta cohesión y bajo acoplamiento:

```
src
└── main
    └── java
        └── com
            ├── app
            │   └── Main.java (Punto de entrada y arranque de UI)
            ├── controlador
            │   └── ControladorDeEnvios.java
            ├── dao
            │   ├── ConexionDB.java (Gestión de conexión JDBC)
            │   ├── EntregaDAO.java
            │   ├── PedidoDAO.java
            │   └── RepartidorDAO.java
            ├── interfaces
            │   ├── Cancelable.java
            │   ├── Despachable.java
            │   └── Rastreable.java
            ├── model
            │   ├── Entrega.java (Entidad transaccional)
            │   ├── EstadoPedido.java (Enum: PENDIENTE, EN_REPARTO, ENTREGADO)
            │   ├── Pedido.java (Clase Abstracta)
            │   ├── PedidoComida.java
            │   ├── PedidoCompraXpress.java
            │   ├── PedidoEncomienda.java
            │   └── Repartidor.java (Implementa Runnable)
            └── ui
                ├── VentanaEntregas.java
                ├── VentanaPedidos.java
                ├── VentanaPrincipal.java
                └── VentanaRepartidores.java
                
```

---
## 🏗️ Arquitectura de Base de Datos

El sistema se apoya en una base de datos MySQL (`speedfast_db`) conformada por:

* **`repartidores`**: Almacena el ID y nombre del personal.
* **`pedidos`**: Guarda las direcciones, tipo de pedido y su estado actual.
* **`entregas`**: Tabla transaccional que relaciona `pedidos` y `repartidores`, registrando la fecha y hora exacta del despacho.

---

## ▶️ Ejecución y Configuración del proyecto

Para ejecutar este proyecto en un entorno local, sigue estos pasos:

1. **Base de Datos**:
    * Abre MySQL Server (Workbench, XAMPP, etc.).
    * Ejecuta el script SQL incluido en el proyecto para crear la base de datos `speedfast_db` y sus tablas.
2. **Configurar Credenciales**:
    * Abre la clase `com.dao.ConexionDB.java`.
    * Modifica los parámetros `USER` y `PASSWORD` según la configuración de tu motor de base de datos local.
3. **Dependencias**:
    * Asegúrate de tener agregado el `mysql-connector-j-8.0.x.jar` en las librerías de tu IDE (IntelliJ IDEA / Eclipse).
4. **Ejecución**:
    * Corre la clase `com.app.Main` para iniciar el menú principal gráfico.

---

## 📊 Diagrama UML 

```
                 ┌─────────────────────────────────┐
                 │          <<enum>>               │
                 │        EstadoPedido             │
                 ├─────────────────────────────────┤
                 │ + PENDIENTE                     │
                 │ + EN_REPARTO                    │
                 │ + ENTREGADO                     │
                 └───────────────┬─────────────────┘
                                 │ (usa)
                 ┌───────────────▼───────────────┐
                 │       Pedido (abstract)       │
                 ├───────────────────────────────┤
                 │ - idPedido : int              │
                 │ - direccionEntrega : String   │
                 │ - distanciaKm : double        │
                 │ - tipoPedido : TipoPedido     │
                 │ - estado : EstadoPedido       │
                 │ - repartidor : String         │
                 │ - cancelado : boolean         │
                 ├───────────────────────────────┤
                 │ + mostrarResumen()            │
                 │ + asignarRepartidor()         │
                 │ + asignarRepartidor(String)   │
                 │ + calcularTiempoEntrega()     │
                 │ + despachar()                 │
                 │ + cancelar()                  │
                 └───────────────▲───────────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                        │                        │
┌───────┴────────────┐ ┌─────────┴────────────┐ ┌─────────┴────────────┐
│   PedidoComida     │ │  PedidoEncomienda    │ │  PedidoCompraXpress  │
└────────────────────┘ └──────────────────────┘ └──────────────────────┘

┌───────────────────────────────┐        ┌───────────────────────────────┐
│     Repartidor (Runnable)     │        │       Entrega (Entidad)       │
├───────────────────────────────┤        ├───────────────────────────────┤
│ - id : int                    │        │ - id : int                    │
│ - nombre : String             │        │ - idPedido : int              │
│ - pedidosAsignados : List     │  ───►  │ - idRepartidor : int          │
├───────────────────────────────┤ (une)  │ - fecha : Date                │
│ + run()                       │        │ - hora : Time                 │
└───────────────────────────────┘        └───────────────────────────────┘
```

---

## 👨‍💻 Autor

**Víctor Valenzuela**  
Escuela de Informática y Telecomunicaciones  
**Duoc UC**
