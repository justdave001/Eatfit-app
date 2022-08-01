import requests
from urllib.parse import urlencode

# Ensure you have python3 installed, and have already run `pip3 install requests`.
# Then, paste this code into a new python file named `pizza.py` and run it in your terminal with:
# `python3 pizza.py`


BASE_URL = "https://mealme-4.mealme.ai"
SEARCH_ENDPOINT = "/restaurants/search/store"
MENU_ENDPOINT = "/restaurants/details/menu"
HEADERS = {"Id-Token": "david:4e5c1803-f9d8-466c-b985-3c8e21484d5e"}  # Enter your API Key here!


def get_pizza_menu(latitude: float, longitude: float) -> dict:
    print("Running...")
    # Request a list of stores
    search_parameters = urlencode({"query": "pizza", "latitude": latitude, "longitude": longitude})
    search_resp = requests.get(f"{BASE_URL}{SEARCH_ENDPOINT}?{search_parameters}", headers=HEADERS)
    if search_resp.status_code != 200:
        print(f"Search API Failed with status {search_resp.status_code} and response: {search_resp.text}")
        return {}
    stores = search_resp.json().get("restaurants", [])
    for store in stores:
        for quote_id in store.get("quote_ids", []):
            # Fetch menu for this quote
            menu_parameters = urlencode({"quote_id": quote_id, "user_latitude": latitude, "user_longitude": longitude})
            menu_resp = requests.get(f"{BASE_URL}{MENU_ENDPOINT}?{menu_parameters}", headers=HEADERS)
            if menu_resp.status_code != 200:
                print(f"Menu API Failed with status {menu_resp.status_code} and response: {menu_resp.text}")
            else:
                # Successfully found an available menu!
                return menu_resp.json()
    print("No available pizza menu found nearby!")
    return {}


print(get_pizza_menu(latitude=37.7786357, longitude=-122.3918135))
