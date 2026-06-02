# 𓋹 HieroWriter 𓋹

HieroWriter is a modern web application and compositor for rendering Ancient Egyptian Hieroglyphs using the standard **Manuel de Codage (MdC)** transliteration syntax. 

Built entirely with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform** targeting Web Assembly (**WasmJS**) and **JavaScript (JS)**, it features a responsive, papyrus-themed compositor interface that allows users to type hieroglyphic codes and generate crisp, high-resolution graphics.

---

## ✨ Key Features

- **Real-Time MdC Compositor**: Instantly parses and stacks hieroglyphs using standard MdC grammar:
  - `-` joins blocks horizontally (e.g., `A-B`)
  - `:` stacks blocks vertically (e.g., `A:B`)
  - `*` groups glyphs side-by-side inside vertical cells (e.g., `A*B`)
- **Crisp, High-Resolution PNG Export**: Saves rendered hieroglyphs as transparent PNGs.
  - **Sigmoidal Edge-Sharpening**: The exporter upscales the crop by 4x and applies a sigmoidal thresholding filter in JavaScript to yield vector-like, smooth, and crisp black outlines on transparent backgrounds.
- **Adaptive Layout**: Features a responsive layout that automatically adjusts to screen widths, splitting settings and previews side-by-side on desktop, or stacking them vertically on mobile viewports.
- **Dark Mode Support**: A custom theme switcher that blends seamlessly between a high-contrast dark theme and a classic light papyrus background.
- **Interactive Writing Examples**: Contains a pre-configured 3x3 grid of clickable vocabulary terms and famous names (like *Tutankhamun* or *Ptah*) that instantly load into the compositor.
- **Mobile App Companion**: Integrated link pointing to [Gero](https://play.google.com/store/apps/details?id=com.shadowings.gero), a Duolingo-like mobile app designed to teach Egyptian hieroglyphs.

---

## 🛠️ Project Structure

- **`/shared`**: Holds the shared Kotlin Multiplatform code:
  - `commonMain`: Holds the Compose UI views (`App.kt`), the dictionary database (`HieroRepository.kt`), and the layout engine for vertical/horizontal arrangements (`MdCWord.kt`).
  - `wasmJsMain` & `jsMain`: Platform-specific implementations (`ImageExporter`) executing low-level HTML5 canvas pixel extraction and DOM interactions.
- **`/webApp`**: Contains the web target entry point (`main.kt`), styling sheets (`styles.css`), and the HTML loader template.

---

## 🚀 Running and Building Locally

Ensure you have **JDK 17** or higher installed.

### Development Server
Run the local hot-reload Webpack server:
- **Wasm target** (Recommended for modern browsers):
  ```bash
  ./gradlew :webApp:wasmJsBrowserDevelopmentRun
  ```
- **JS target** (Fallback for older engines):
  ```bash
  ./gradlew :webApp:jsBrowserDevelopmentRun
  ```
The development app will be hosted at `http://localhost:8081/`.

### Production Build
Generate release-optimized bundles ready for static file hosting:
```bash
./gradlew :webApp:wasmJsBrowserDistribution :webApp:jsBrowserDistribution
```
The output assets will be built in:
- `webApp/build/dist/wasmJs/productionExecutable/`
- `webApp/build/dist/js/productionExecutable/`

---

## ☁️ CI/CD Deployment

A fully automated GitHub Actions workflow is located at `.github/workflows/deploy.yml`. 

On every push to the `main` branch, the workflow:
1. Sets up the JDK environment and Gradle cache.
2. Compiles the production WasmJS assets.
3. Deploys the static assets directly to **GitHub Pages** using GitHub's native actions deployment.

To activate, navigate to your repository's **Settings > Pages** and switch the deployment source to **GitHub Actions**.

---

## 🔗 Links

- **GitHub Repository**: [https://github.com/EmmanueleVilla/hiero_writer](https://github.com/EmmanueleVilla/hiero_writer)
- **Gero Mobile App**: [Download on Google Play Store](https://play.google.com/store/apps/details?id=com.shadowings.gero)