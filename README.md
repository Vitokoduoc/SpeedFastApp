<p>
  <img src="https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png" width="300"/>
</p>

---
# SpeedFast – Abstracción y Herencia en Java

Proyecto académico desarrollado para la asignatura **Desarrollo Orientado a Objetos II**, cuyo objetivo es diseñar e implementar una estructura **robusta, reutilizable y coherente**, aplicando los principios de **abstracción**, **herencia** y **polimorfismo** en Java, utilizando un caso contextualizado.

---

## 📌 Contexto del problema

**SpeedFast** es una empresa de reparto a domicilio que gestiona distintos tipos de pedidos, cada uno con reglas propias para estimar su tiempo de entrega:

- **Comida**: considera tiempos asociados a preparación y traslado.
- **Encomienda**: incorpora tiempos adicionales por validación y manipulación.
- **Compra Express**: prioriza rapidez, con tiempos base reducidos.

Estas diferencias se modelan mediante **Programación Orientada a Objetos**, evitando estructuras rígidas y favoreciendo un diseño extensible.

---

## 🎯 Objetivos del proyecto

- Diseñar una **clase abstracta** que represente el concepto general de pedido.
- Reutilizar atributos y comportamientos comunes mediante **herencia**.
- Aplicar **polimorfismo por sobrescritura** para especializar el cálculo del tiempo de entrega.
- Garantizar un diseño **claro, modular y alineado a buenas prácticas**.
- Facilitar la futura extensión del sistema sin modificar la estructura base.

---

## 🧩 Conceptos aplicados

- Programación Orientada a Objetos (POO)
- Abstracción (`abstract`)
- Herencia (`extends`)
- Polimorfismo (sobrescritura de métodos)
- Encapsulamiento
- Validaciones de entrada
- Uso de `enum` para evitar valores mágicos
- Documentación JavaDoc

---

## 🗂️ Estructura del proyecto

```
src
└── main
    └── java
        └── com
            ├── app
            │   └── Main.java
            └── model
                ├── Pedido.java
                ├── PedidoComida.java
                ├── PedidoEncomienda.java
                └── PedidoCompraXpress.java
```

---

## 🏗️ Diseño general

- **Pedido (clase abstracta)**  
  Define los atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`) y provee:
    - Un método implementado para mostrar el resumen del pedido.
    - Un método abstracto `calcularTiempoEntrega()` que debe ser definido por cada subclase.

- **PedidoComida / PedidoEncomienda / PedidoCompraXpress**  
  Clases concretas que heredan de `Pedido` y **sobrescriben** el método
  `calcularTiempoEntrega()` aplicando reglas específicas según el tipo de pedido.

- **Main**  
  Clase de ejecución que:
    - Instancia distintos tipos de pedidos.
    - Utiliza referencias del tipo base (`Pedido`).
    - Demuestra el comportamiento polimórfico al ejecutar métodos comunes.

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
[PedidoComida]
Pedido #001
Dirección: Av. Central 123
Distancia: 4 km
Tiempo estimado de entrega: 23 minutos

[PedidoEncomienda]
Pedido #002
Dirección: Calle Norte 456
Distancia: 6 km
Tiempo estimado de entrega: 29 minutos

[PedidoCompraXpress]
Pedido #003
Dirección: Pasaje Sur 789
Distancia: 7 km
Tiempo estimado de entrega: 15 minutos
```

---

## 📊 Diagrama UML (simplificado)

```
                 ┌───────────────────────────────┐
                 │       Pedido (abstract)       │
                 ├───────────────────────────────┤
                 │ - idPedido: int               │
                 │ - direccionEntrega: String    │
                 │ - distanciaKm: double         │
                 │ - tipoPedido: TipoPedido      │
                 ├───────────────────────────────┤
                 │ + mostrarResumen(): void      │
                 │ + calcularTiempoEntrega():int │
                 └───────────────▲───────────────┘
                                 │
          ┌──────────────────────┼────────────────────────┐
          │                      │                        │
┌────────────────────┐ ┌──────────────────────┐ ┌────────────────────────┐
│   PedidoComida     │ │  PedidoEncomienda    │ │  PedidoCompraXpress    │
├────────────────────┤ ├──────────────────────┤ ├────────────────────────┤
│ + calcularTiempo() │ │ + calcularTiempo()   │ │ + calcularTiempo()     │
└────────────────────┘ └──────────────────────┘ └────────────────────────┘
```

---

## 👨‍💻 Autor

**Víctor Valenzuela**  
Escuela de Informática y Telecomunicaciones  
Duoc UC
