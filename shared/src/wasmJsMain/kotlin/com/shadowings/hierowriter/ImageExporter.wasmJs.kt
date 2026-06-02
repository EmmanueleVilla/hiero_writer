@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
package com.shadowings.hierowriter

@JsFun("""
(x, y, w, h, fileName) => {
    console.log("exportImage invoked with parameters:", { x, y, w, h, fileName });
    
    function findCanvas(root) {
        if (!root) return null;
        if (root.tagName === 'CANVAS') return root;
        if (root.shadowRoot) {
            const canvas = findCanvas(root.shadowRoot);
            if (canvas) return canvas;
        }
        const children = root.children;
        if (children) {
            for (let i = 0; i < children.length; i++) {
                const canvas = findCanvas(children[i]);
                if (canvas) return canvas;
            }
        }
        return null;
    }
    
    const canvas = findCanvas(document.body) || findCanvas(document);
    if (!canvas) {
        console.error("Canvas element not found!");
        return;
    }
    
    try {
        const rect = canvas.getBoundingClientRect();
        const dpr = window.devicePixelRatio || 1;
        
        console.log("Canvas client size:", rect.width, "x", rect.height, "at", rect.left, ",", rect.top);
        console.log("Canvas internal size:", canvas.width, "x", canvas.height, "dpr:", dpr);
        
        // Calculate coordinates in canvas's internal backbuffer coordinates (physical pixels)
        let sx = x - (rect.left * dpr);
        let sy = y - (rect.top * dpr);
        let sw = w;
        let sh = h;
        
        console.log("Initial calculated source crop in physical pixels:", { sx, sy, sw, sh });
        
        // Clamp the source crop coordinates to the actual boundaries of the canvas
        // to prevent DOMException: IndexSizeError in browsers (like Safari/Chrome/Firefox)
        if (sx < 0) {
            sw += sx;
            sx = 0;
        }
        if (sy < 0) {
            sh += sy;
            sy = 0;
        }
        if (sx + sw > canvas.width) {
            sw = canvas.width - sx;
        }
        if (sy + sh > canvas.height) {
            sh = canvas.height - sy;
        }
        
        console.log("Clamped source crop in physical pixels:", { sx, sy, sw, sh });
        
        if (sw <= 0 || sh <= 0) {
            console.error("Invalid dimensions for crop after clamping:", sw, sh);
            return;
        }
        
        const tempCanvas = document.createElement('canvas');
        tempCanvas.width = sw;
        tempCanvas.height = sh;
        const ctx = tempCanvas.getContext('2d');
        if (!ctx) {
            console.error("Could not get 2D context for temporary canvas");
            return;
        }
        
        ctx.drawImage(canvas, sx, sy, sw, sh, 0, 0, sw, sh);
        
        try {
            const imgData = ctx.getImageData(0, 0, sw, sh);
            const data = imgData.data;
            for (let i = 0; i < data.length; i += 4) {
                const r = data[i];
                const g = data[i + 1];
                const b = data[i + 2];
                
                // Estimate opacity of the black glyph based on background color FFF1D6 (255, 241, 214)
                let alpha = 1.0 - ((r / 255) + (g / 241) + (b / 214)) / 3.0;
                if (alpha < 0.02) {
                    alpha = 0;
                } else if (alpha > 0.98) {
                    alpha = 1;
                }
                
                // Write pure black with calculated alpha
                data[i] = 0;
                data[i + 1] = 0;
                data[i + 2] = 0;
                data[i + 3] = Math.round(alpha * 255);
            }
            ctx.putImageData(imgData, 0, 0);
        } catch (e) {
            console.warn("Failed to process transparency:", e);
        }
        
        const dataUrl = tempCanvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.download = fileName;
        link.href = dataUrl;
        
        // Append to DOM to ensure click is executed in all browsers
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        
        console.log("Download triggered successfully for:", fileName);
    } catch (err) {
        console.error('Error drawing canvas and exporting:', err);
    }
}
""")
private external fun exportCanvasRegionJs(x: Float, y: Float, w: Float, h: Float, fileName: String)

actual fun exportImage(x: Float, y: Float, width: Float, height: Float, fileName: String) {
    exportCanvasRegionJs(x, y, width, height, fileName)
}

@JsFun("(text) => { navigator.clipboard.writeText(text); }")
private external fun copyToClipboardJs(text: String)

actual fun copyToClipboard(text: String) {
    copyToClipboardJs(text)
}
