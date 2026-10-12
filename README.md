# Two-Dimensional Ray Marching (`Ray Marching Simulation`)

A graphical Java simulation that explores sphere-tracing and ray marching techniques in a 2-dimensional plane. The program populates an environment with random geometric objects and calculates ray trajectories using iterative distance-to-shape computations.

## Key Features & Controls

### Interactive Controls
* **Mouse Motion:** Move your cursor across the panel to reposition the ray-marching camera in real time.
* **Left / Right Arrow Keys:** Rotate the camera's viewing angle dynamically across the plane.
* **Control Buttons:**
  * **Reset:** Clears the environment and reinitializes random shapes and camera position.
  * **Spin:** Toggles continuous automated camera rotation using a Java Swing `Timer`.
  * **Help:** Displays usage instructions and guidelines via dialog popups.
  * **Music:** Toggles background audio playback.
  * **Quit:** Exits the application.

### Core Algorithms
* **Sphere-Tracing Ray Marching:** Iteratively calculates the minimum distance between the current march point and all obstacles until hitting a collision threshold or exiting canvas bounds.
* **Collision Objects:** Generates randomized subclasses (`RectangleObject`, `CircleObject`, and `TriangleObject`) extending an abstract `CollisionObject` class.
* **Polar Coordinate Projection:** Computes step intervals using angle trigonometry to advance rays across both x and y axes.

## Tech Stack & Structure
* **Language:** Java
* **Frameworks/Libraries:** Java Swing (`JFrame`, `JPanel`, `JButton`, `Timer`), Java AWT (`Graphics2D`, `Color`, `MouseEvent`, `KeyEvent`), and Java Sound API (`Clip`, `AudioInputStream`).
* **Core Classes:**
  * `myFrame.java`: Manages the application frame, user interface buttons, mouse motion listeners, key adapters, and drawing loops.
  * `Camera.java`: Represents the origin point of the ray, tracking coordinates and viewing angles.
  * `CollisionObject` & Subclasses: Object-oriented definitions handling boundary containment and precise distance formulas.
