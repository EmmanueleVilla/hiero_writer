package com.shadowings.hierowriter

actual fun exportImage(x: Float, y: Float, width: Float, height: Float, fileName: String) {
    var export = js("""
        function(x, y, w, h, fileName) {
            console.log("exportImage invoked with parameters:", { x, y, w, h, fileName });
            
            function findCanvas(root) {
                if (!root) return null;
                if (root.tagName === 'CANVAS') return root;
                if (root.shadowRoot) {
                    var canvas = findCanvas(root.shadowRoot);
                    if (canvas) return canvas;
                }
                var children = root.children;
                if (children) {
                    for (var i = 0; i < children.length; i++) {
                        var canvas = findCanvas(children[i]);
                        if (canvas) return canvas;
                    }
                }
                return null;
            }
            
            var canvas = findCanvas(document.body) || findCanvas(document);
            if (!canvas) {
                console.error("Canvas element not found!");
                return;
            }
            
            try {
                var rect = canvas.getBoundingClientRect();
                var dpr = window.devicePixelRatio || 1;
                
                console.log("Canvas client size:", rect.width, "x", rect.height, "at", rect.left, ",", rect.top);
                console.log("Canvas internal size:", canvas.width, "x", canvas.height, "dpr:", dpr);
                
                // Calculate coordinates in canvas's internal backbuffer coordinates (physical pixels)
                var sx = x - (rect.left * dpr);
                var sy = y - (rect.top * dpr);
                var sw = w;
                var sh = h;
                
                console.log("Initial calculated source crop in physical pixels:", { sx: sx, sy: sy, sw: sw, sh: sh });
                
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
                
                console.log("Clamped source crop in physical pixels:", { sx: sx, sy: sy, sw: sw, sh: sh });
                
                if (sw <= 0 || sh <= 0) {
                    console.error("Invalid dimensions for crop after clamping:", sw, sh);
                    return;
                }
                
                var tempCanvas = document.createElement('canvas');
                tempCanvas.width = sw;
                tempCanvas.height = sh;
                var ctx = tempCanvas.getContext('2d');
                if (!ctx) {
                    console.error("Could not get 2D context for temporary canvas");
                    return;
                }
                
                ctx.drawImage(canvas, sx, sy, sw, sh, 0, 0, sw, sh);
                
                // Upscale image by 4x to output high resolution crisp PNG
                var scaleFactor = 4;
                var targetW = sw * scaleFactor;
                var targetH = sh * scaleFactor;
                
                var scaleCanvas = document.createElement('canvas');
                scaleCanvas.width = targetW;
                scaleCanvas.height = targetH;
                var scaleCtx = scaleCanvas.getContext('2d');
                if (!scaleCtx) {
                    console.error("Could not get 2D context for scaling canvas");
                    return;
                }
                
                scaleCtx.imageSmoothingEnabled = true;
                scaleCtx.imageSmoothingQuality = 'high';
                scaleCtx.drawImage(tempCanvas, 0, 0, sw, sh, 0, 0, targetW, targetH);
                
                try {
                    var imgData = scaleCtx.getImageData(0, 0, targetW, targetH);
                    var data = imgData.data;
                    for (var i = 0; i < data.length; i += 4) {
                        var r = data[i];
                        var g = data[i + 1];
                        var b = data[i + 2];
                        
                        // Estimate opacity of the black glyph based on background color FFF1D6 (255, 241, 214)
                        var alpha = 1.0 - ((r / 255) + (g / 241) + (b / 214)) / 3.0;
                        
                        // Sigmoidal sharpening function to get extremely crisp vector-like edges at 4x resolution
                        alpha = 1.0 / (1.0 + Math.exp(-24 * (alpha - 0.5)));
                        if (alpha < 0.05) {
                            alpha = 0;
                        } else if (alpha > 0.95) {
                            alpha = 1;
                        }
                        
                        // Write pure black with calculated alpha
                        data[i] = 0;
                        data[i + 1] = 0;
                        data[i + 2] = 0;
                        data[i + 3] = Math.round(alpha * 255);
                    }
                    scaleCtx.putImageData(imgData, 0, 0);
                } catch (e) {
                    console.warn("Failed to process transparency and scaling:", e);
                }
                
                var dataUrl = scaleCanvas.toDataURL('image/png');
                var link = document.createElement('a');
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
    export(x, y, width, height, fileName)
}

actual fun copyToClipboard(text: String) {
    val copy = js("function(t) { navigator.clipboard.writeText(t); }")
    copy(text)
}

actual fun openUrl(url: String) {
    val open = js("function(u) { window.open(u, '_blank'); }")
    open(url)
}
