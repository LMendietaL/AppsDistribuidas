import http.server
import socketserver
import json

# Datos ficticios de información geográfica
geonames_data = {
    "New York": {"country": "USA", "population": 8398748},
    "Paris": {"country": "France", "population": 2140526},
    "Tokyo": {"country": "Japan", "population": 13929286},
    "London": {"country": "England", "population": 13929286},
    # Agrega más ciudades y sus datos aquí
}

class GeonamesHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path.startswith('/geonames/'):
            ciudad = self.path[10:]
            if ciudad in geonames_data:
                data = geonames_data[ciudad]
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.end_headers()
                self.wfile.write(json.dumps(data).encode())
            else:
                self.send_response(404)
                self.end_headers()
                self.wfile.write("Ciudad no encontrada.".encode())
        else:
            super().do_GET()

# Configuración del servidor
with socketserver.TCPServer(("", 9091), GeonamesHandler) as httpd:
    print("Servidor Geonames en el puerto 9091")
    httpd.serve_forever()
