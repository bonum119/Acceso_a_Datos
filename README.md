Práctica 1: Gasolinera
Versión del JDK:
JDK 17 (mínimo JDK 14).

Ubicación y formato de los ficheros:
Se crean solos en la carpeta datos/, dentro del directorio desde el que se ejecuta el programa.
-datos/clientes.csv: id,nombre,telefono,matricula
-datos/pagos.csv: id,idCliente,fecha,importe,litros,combustible

Decisiones de diseño:
-ConsolaUI solo se ocupa del menú y de pedir datos.
-GasolineraGestor tiene la lógica del programa.
-GestorArchivos es una interfaz y GestorArchivosCSV la implementa, así la lógica no depende del formato de los ficheros.
-Cliente y Pago solo guardan datos.
-Cada alta se guarda en el momento en el fichero.
-Los IDs se calculan al arrancar (el mayor + 1), y los de clientes y pagos son independientes.


Qué cambiaría si se modificara el formato de almacenamiento:
Crear una clase nueva que implemente GestorArchivos (por ejemplo, GestorArchivosJSON).
Cambiar una línea en el constructor de GasolineraGestor: sustituir new GestorArchivosCSV() por la clase nueva.
ConsolaUI, Cliente, Pago y el resto de GasolineraGestor no hay que tocarlos.
