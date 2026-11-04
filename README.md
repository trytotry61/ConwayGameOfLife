# Conway's Game of Life

Клеточный автомат Джона Конвея на Java + JavaFX с **торической топологией** (поле замкнуто в тор - противоположные края являются соседями).

##  Ключевые особенности
- **Правила (B3/S23):** клетка выживает при 2–3 соседях, рождается при 3.
- **Тор:** соседи заворачиваются через края поля по формуле `(index + delta + size) % size`.
- **Разделение на слои:** backend (`com.conway.backend`) не зависит от JavaFX; frontend (`com.conway.frontend`) - GUI.
- **Интерактив:** клик по клетке меняет её состояние, слайдеры скорости и плотности, изменение размеров поля (3×3 … 100×100).

## ️ Требования
- JDK 11+ (рекомендуется 17+)
- JavaFX SDK (`javafx.controls`, `javafx.graphics`)

##  Запуск
```bash
javac --module-path <JAVAFX_SDK>/lib --add-modules javafx.controls,javafx.graphics -d out src/com/conway/**/*.java

java --module-path <JAVAFX_SDK>/lib --add-modules javafx.controls,javafx.graphics -cp out com.conway.frontend.MainApp