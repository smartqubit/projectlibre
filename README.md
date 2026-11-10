# projectlibre

The Open Source replacement of Microsoft Project

![ProjectLibre_Gantt_Chart_Ribbon_UI](http://a.fsdn.com/con/app/proj/projectlibre/screenshots/ProjectLibre_Gantt.jpeg)

Features
--------

* Microsoft Project replacement
* Gantt Chart
* New and updated version of OpenProj with hundreds of updates/bug fixes
* Earned Value Costing
* PERT Charts
* Project Management software
* RBS Chart
* New version of OpenProj

Follow @projectlibre

## Project Structure

This project is a multi-module Java application managed by Apache Ant. The repository is divided into several directories:

* **`openproj_build/`**: Contains the Ant build scripts (`build.xml`, `build.properties`) and packaging resources (installers, icons, etc.).
* **`openproj_contrib/`**: Contains third-party dependencies and libraries required by the project.
* **`openproj_core/`** & **`projectlibre_core/`**: Contain the core business logic, data models, and scheduling engine for the application.
* **`openproj_ui/`**: Contains the user interface source code (menus, dialogs, Gantt chart views, etc.).
* **`openproj_reports/`**: Contains the reporting functionalities.
* **`openproj_exchange/`** & **`projectlibre_exchange/`**: Contain the code responsible for import/export capabilities (such as opening and saving Microsoft Project files).

## Compilation

The project uses Apache Ant for its build system. To compile the project, you must have Java Development Kit (JDK) and Apache Ant installed.

1. Open a terminal and navigate to the `openproj_build` directory:
   ```bash
   cd openproj_build
   ```
2. Run the `compile` target using Ant:
   ```bash
   ant compile
   ```
   *Alternatively, running `ant` without any arguments will default to the `dist` target, which compiles the code and builds the distribution jar.*

## Execution

After compiling the project, you can run ProjectLibre directly from the generated distribution folder or by executing the JAR file.

1. First, build the executable directory format by running:
   ```bash
   cd openproj_build
   ant dir
   ```
2. Navigate to the newly generated `packages/projectlibre-<version>` directory:
   ```bash
   cd packages/projectlibre-*
   ```
3. Run the application using the provided launch scripts:
   * **Linux/macOS**: `./projectlibre.sh`
   * **Windows**: `projectlibre.bat`

Alternatively, you can run the JAR file directly from the `openproj_build/dist` directory using Java:
```bash
java -jar dist/projectlibre.jar
```

## Exporting as a Package

The build script supports exporting ProjectLibre into several package formats (ZIP, TAR.GZ, RPM, DEB, Mac App, MSI). These are executed via specific Ant targets from within the `openproj_build` directory.

Here are the most common packaging targets:

* **TAR.GZ Archive** (Linux/macOS/Unix):
  ```bash
  ant tar
  ```
* **ZIP Archive** (Windows):
  ```bash
  ant zip
  ```
* **Debian Package** (`.deb` for Ubuntu/Debian):
  ```bash
  ant deb
  ```
* **RPM Package** (`.rpm` for Fedora/CentOS/RHEL):
  ```bash
  ant rpm
  ```
* **Mac OS X App** (`.app` format):
  ```bash
  ant mac-new
  ```
* **Windows Installer** (Wix Toolset required):
  ```bash
  ant wix
  ```

To build all available packages at once, run:
```bash
ant all
```

All generated packages and installers will be placed inside the `openproj_build/packages/` directory.
