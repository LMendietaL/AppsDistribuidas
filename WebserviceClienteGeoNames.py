import requests

# URL del servidor Geonames
geonames_url = 'http://localhost:9091'

def obtener_info_geonames(ciudad):
    url = f'{geonames_url}/geonames/{ciudad}'
    response = requests.get(url)
    
    if response.status_code == 200:
        data = response.json()
        return f'Información de {ciudad} - País: {data["country"]}, Población: {data["population"]}'
    elif response.status_code == 404:
        return f'Ciudad no encontrada: {ciudad}'
    else:
        return f'Error en la solicitud: Código {response.status_code}'

# Ejemplos de uso
ciudades = ["New York", "Paris", "Tokyo", "London"]
for ciudad in ciudades:
    resultado = obtener_info_geonames(ciudad)
    print(resultado)
