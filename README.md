# Onion Lad Adventures 🧅

Es un videojuego de plataformas en 2D desarrollado en Java utilizando la librería **LibGDX**. 
Este proyecto ha sido desarrollado siguiendo el paradigma de **Programación Orientada a Objetos (POO)** y cumple con los siguientes requisitos.

## Características Principales 
- **Personaje Principal y Niveles**: Controlas a "Onion Lad", cuyo objetivo es atravesar múltiples niveles de izquierda a derecha.
- **HUD Integrado**: Interfaz en pantalla que muestra la puntuación, las vidas restantes y el nivel actual en tiempo real.
- **Ciclo de Menús Completo**:
  - Menú Principal para empezar partida o ver las puntuaciones.
  - Pantalla de Instrucciones con los controles.
  - Pantalla de Configuración para mutear música y sonidos (las preferencias se guardan de forma permanente).
  - Pantallas dinámicas de *Game Over* y *Victoria* que te devuelven al menú sin necesidad de reiniciar la aplicación.
- **Interacción con NPCs**: Existen 3 tipos de enemigos con características de velocidad y patrulla distintas (`NORMAL`, `FAST`, y `TANK`).
- **Recursos Audiovisuales**: El juego cuenta con animaciones para los personajes y sistema de gestión de audio para música y efectos de sonido.

## Funcionalidades Extra 
1. **Top 10 Puntuaciones**: Sistema de ranking que guarda permanentemente el nombre y la puntuación de las mejores partidas usando `Preferences`.
2. **Generador de Niveles Automático**: Sistema dinámico que lee archivos `.tmx` (generados con TiledMap Editor). Permite crear niveles infinitos y poblar el mapa de monedas, enemigos y zonas de muerte sin tocar una sola línea de código Java.
3. **Soporte Ilimitado de Niveles**: El gestor de recursos y la lógica están programados para detectar de forma automática hasta 20 niveles. Si quieres añadir un nivel, simplemente arrastra un archivo `nivel3.tmx` a la carpeta `maps` y el juego lo cargará y transicionará automáticamente.
4. **Menú de Pausa In-Game**: Durante la partida es posible pulsar la tecla `ESCAPE` para congelar la lógica del juego, desplegando un sub-menú para mutear el sonido en caliente, volver al menú principal o salir al escritorio.

## Estructura y Arquitectura 
El proyecto se divide en las siguientes capas y gestores:
- `com.juego.domain`: Clases puras de objetos (Modelos) como `Player`, `Enemy`, y los enums de `EnemyType`.
- `com.juego.manager`: Lógica de negocio (Controladores).
  - `LevelManager`: Procesa el archivo TMX y extrae la geometría y objetos.
  - `LogicManager`: Procesa las físicas matemáticas, la gravedad y las colisiones basándose en el TiledMap.
  - `RenderManager` y `SoundManager`: Clases dedicadas de audio e imagen.
- `com.juego.screen`: Pantallas de la interfaz gráfica implementando Scene2D.

## Controles del Juego
- `A` / `D` : Moverse a izquierda y derecha.
- `W` : Saltar (La altura del salto depende de la configuración de gravedad en el código).
- `ESCAPE` : Pausar el juego.

## Compilación y Ejecución
Para descargar y arrancar el juego desde la terminal, sigue estos pasos:

1. Clona el repositorio en tu ordenador:
```bash
git clone https://github.com/raulpra/PMDM2-EV.git
```
2. Sitúate en el directorio principal del proyecto descargado:
```bash
cd PMDM2-EV
```
3. Ejecuta el juego usando Gradle:
```bash
./gradlew lwjgl3:run
```
