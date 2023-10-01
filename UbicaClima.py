import requests

# La clave API de Geonames
geonames_api_key = "luism001"

# La clave API de OpenWeatherMap
openweathermap_api_key = "4b9e85b5d03af286ac95eebf0d6c2966"

# Nombre de la ubicación que se desea obtener
ubicacion = "Ciudad de México"

# Realiza una solicitud a la API de Geonames
geonames_url = f"http://api.geonames.org/searchJSON?name={ubicacion}&maxRows=1&username={geonames_api_key}"
response_geonames = requests.get(geonames_url)

# Verifica si la solicitud a Geonames fue exitosa
if response_geonames.status_code == 200:
    data_geonames = response_geonames.json()
    
    # Realiza una solicitud a Geonames para obtener el codigo del pais
    countryCode_url = 'http://api.geonames.org/countryCodeJSON'
    params = {
    'lat' : data_geonames['geonames'][0]['lat'],
    'lng' : data_geonames['geonames'][0]['lng'],
    'username' : geonames_api_key
    }
    response_countryCode = requests.get(countryCode_url, params=params)

    #Realiza una solicitud a la API de OpenWeatherMap
    openweathermap_url = f"http://api.openweathermap.org/data/2.5/weather?q={ubicacion}&appid={openweathermap_api_key}"
    response_openweathermap = requests.get(openweathermap_url)

    # Verifica si la solicitud a OpenWeatherMap fue exitosa
    if response_openweathermap.status_code == 200:
        data_openweathermap = response_openweathermap.json()
        data_countryCode = response_countryCode.json()

        #Muestra los datos meteorológicos en pantalla
        country_code = data_countryCode['countryCode']
        temperatura = data_openweathermap['main']['temp'] - 273.15  # Convertir de Kelvin a Celsius
        descripcion_clima = data_openweathermap['weather'][0]['description']

        print(f"Temperatura en {ubicacion}, {country_code}: {temperatura:.2f}°C")
        print(f"Condiciones climaticas en {ubicacion}, {country_code}: {descripcion_clima}")
    else:
        print("Error al consultar la API de OpenWeatherMap")
else:
    print("Error al consultar la API de Geonames")