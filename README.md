<p>
  <img src="https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png" width="300"/>
</p>

---
# SpeedFast – Polimorfismo en Java (Semana 1)

Proyecto académico desarrollado para la asignatura **Desarrollo Orientado a Objetos II**, correspondiente a la **Semana 1**, cuyo objetivo es aplicar los conceptos de **polimorfismo**, **sobrescritura** y **sobrecarga de métodos** en Java, utilizando un caso contextualizado.

---

## 📌 Contexto del problema

**SpeedFast** es una empresa de reparto a domicilio que ofrece distintos tipos de pedidos:

- **Comida**: requiere repartidor con mochila térmica.
- **Encomienda**: requiere validación de peso y embalaje.
- **Compra Express**: se asigna al repartidor más cercano con disponibilidad inmediata.

Cada tipo de pedido presenta reglas distintas para la asignación del repartidor, las cuales se modelan mediante **Programación Orientada a Objetos**, evitando estructuras rígidas como `if/else`.

---

## 🎯 Objetivos del proyecto

- Aplicar **herencia** mediante una superclase común (`Pedido`).
- Implementar **polimorfismo por sobrescritura** en subclases especializadas.
- Implementar **polimorfismo por sobrecarga** mediante múltiples firmas del método `asignarRepartidor`.
- Demostrar el uso de **referencias del tipo base** para ejecutar comportamiento dinámico.
- Mantener un diseño **claro, extensible y alineado a buenas prácticas**.

---

## 🧩 Conceptos aplicados

- Programación Orientada a Objetos (POO)
- Herencia (`extends`)
- Polimorfismo en tiempo de ejecución (sobrescritura)
- Polimorfismo en tiempo de compilación (sobrecarga)
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

- **Pedido**  
  Superclase que define atributos comunes, validaciones, y dos versiones del método:
    - `asignarRepartidor()`
    - `asignarRepartidor(String nombreRepartidor)`

- **PedidoComida / PedidoEncomienda / PedidoCompraXpress**  
  Subclases que **sobrescriben ambos métodos** para aplicar la lógica específica de cada tipo de pedido.

- **Main**  
  Clase de prueba que:
    - Instancia los pedidos.
    - Utiliza referencias del tipo `Pedido`.
    - Demuestra polimorfismo y sobrecarga en ejecución.

---

## ▶️ Ejecución del proyecto

1. Abrir el proyecto en **IntelliJ IDEA**.
2. Verificar que el SDK de Java esté configurado.
3. Ejecutar la clase:

```
com.app.Main
```

---

## 🖥️ Ejemplo de salida por consola

```
=== Polimorfismo (Sobrescritura) ===

[Pedido Comida]
Asignando repartidor...
→ Verificando mochila térmica... OK

[Pedido Encomienda]
Asignando repartidor...
→ Validando peso y embalaje... OK

[Pedido Compra Xpress]
Asignando repartidor...
→ Repartidor más cercano con disponibilidad inmediata encontrado.

=== Sobrecarga (asignarRepartidor(String)) ===

[Pedido Comida]
Asignando repartidor...
→ Verificando mochila térmica... OK
→ Pedido asignado a Juan Pérez
```

---

## 📊 Diagrama UML

```

                         ┌───────────────────────────────┐
                         │        Pedido (superclase)    │
                         ├───────────────────────────────┤
                         │ - idPedido: int               │
                         │ - direccionEntrega: String    │
                         │ - tipoPedido: TipoPedido      │
                         ├───────────────────────────────┤
                         │ + Pedido(id:int, dir:String,  │
                         │        tipo:TipoPedido)       │
                         │ + asignarRepartidor(): void   │
                         │ + asignarRepartidor(nombre:   │
                         │        String): void          │
                         │ # imprimirEncabezado(titulo:  │
                         │        String): void          │
                         │ # validarNombre(nombre:       │
                         │        String): void          │
                         │ + getIdPedido(): int          │
                         │ + getDireccionEntrega():String│
                         │ + getTipoPedido(): TipoPedido │
                         │ + toString(): String          │
                         └───────────────▲───────────────┘
                                         │
            ┌────────────────────────────┼──────────────────────────────────┐
            │                            │                                  │
┌───────────────────────────┐  ┌───────────────────────────┐  ┌─────────────────────────────┐
│      PedidoComida         │  │    PedidoEncomienda       │  │     PedidoCompraXpress      │
├───────────────────────────┤  ├───────────────────────────┤  ├─────────────────────────────┤
│ + PedidoComida(id, dir)   │  │ + PedidoEncomienda(id,dir)│  │ + PedidoCompraXpress(id,dir)│
│ + asignarRepartidor():void│  │ + asignarRepartidor():void│  │ + asignarRepartidor(): void │
│ + asignarRepartidor(      │  │ + asignarRepartidor(      │  │ + asignarRepartidor(        │
│     nombre:String):void   │  │     nombre:String):void   │  │     nombre:String): void    │
└───────────────────────────┘  └───────────────────────────┘  └─────────────────────────────┘


                   ┌──────────────────────────┐
                   │   TipoPedido «enum»      │
                   ├──────────────────────────┤
                   │ COMIDA                   │
                   │ ENCOMIENDA               │
                   │ COMPRA_XPRESS            │
                   └──────────────────────────┘

Pedido ───────────────► TipoPedido   (usa)


┌───────────────────────────────┐
│         Main (com.app)        │
├───────────────────────────────┤
│ + main(args:String[]): void   │
└───────────────┬───────────────┘
│ dependencia (usa)
▼
Pedido
```
---

## 👨‍💻 Autor

**Victor Valenzuela**  
Asignatura: Desarrollo Orientado a Objetos II  
Semana 1 – Polimorfismo en Java
