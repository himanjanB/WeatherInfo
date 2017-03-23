package com.weatherinfo.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReadWeatherJSON {
	private static final String API_KEY = "c1f0237dea1a3379b73c666f39793ff0";

	public static void main(String[] args) throws MalformedURLException, IOException {

		List<WeatherDataObject> weatherDataList = new ArrayList<WeatherDataObject>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the name of the city: ");
		String cityName = scan.nextLine();
		System.out.println("Enter the number of days: ");
		int numberOfDays = scan.nextInt();
		scan.nextLine();
		weatherDataList = readJSONData(cityName, numberOfDays);
		printArray(weatherDataList);
		scan.close();
	}

	private static void printArray(List<WeatherDataObject> weatherDataList) {
		for (WeatherDataObject wObj : weatherDataList) {
			System.out.println(
					wObj.getDate() + ", " + wObj.getTemperature() + ", " + wObj.getMaxTemp() + ", " + wObj.getMinTemp()
							+ ", " + wObj.getHumidity() + ", " + wObj.getWindSpeed() + ", " + wObj.getDescription());
		}
	}

	public static List<WeatherDataObject> readJSONData(String cityName, int numberOfDays)
			throws MalformedURLException, IOException {
		List<WeatherDataObject> tempList = new ArrayList<WeatherDataObject>();
		StringBuilder URL = new StringBuilder("http://api.openweathermap.org/data/2.5/");

		if (numberOfDays > 1) {
			URL.append("forecast?q=").append(cityName).append("&cnt=").append(numberOfDays).append("&appid=")
					.append(API_KEY);
		} else {
			URL = URL.append("weather?q=").append(cityName).append("&appid=").append(API_KEY);
		}

		URL url = new URL(URL.toString());
		URI uri = null;
		try {
			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
					url.getQuery(), url.getRef());
		} catch (URISyntaxException e1) {
			System.out.println("Error in forming the URI.");
			e1.printStackTrace();
		}
		url = uri.toURL();
		System.out.println("URL is: " + url);
		JSONObject jsonObject = null;
		try {
			jsonObject = readJSONFromURL(url);
		} catch (IOException e) {
			System.out.println("Error in initializing JSON Object.");
			WeatherDataObject wObj = new WeatherDataObject();
			wObj.setDescription("City not found");
			tempList.add(wObj);
			return tempList;
		}

		try {
			System.out.println("JSON Object formed properly.");
			if (jsonObject.get("cod") != null) {
				System.out.println(jsonObject.get("cod"));
				double code = jsonObject.getDouble("cod");
				System.out.println(code);
				if (code != 200) {
					System.out.println("Returning error message. City not found.");
					double errorMessage = jsonObject.getDouble("message");
					WeatherDataObject wObj = new WeatherDataObject();
					wObj.setDescription(errorMessage + "");
					tempList.add(wObj);
					return tempList;
				}
			}

			if (numberOfDays > 1) {
				JSONObject cityObject = (JSONObject) jsonObject.get("city");
				System.out.println("Selected city is " + cityObject.get("name"));
				JSONArray listOfWeatherRecords = (JSONArray) jsonObject.get("list");

				Iterator<?> iterator = listOfWeatherRecords.iterator();
				int index = 0;

				while (index < numberOfDays && iterator.hasNext()) {
					WeatherDataObject wObj = new WeatherDataObject();
					index++;
					JSONObject currObject = (JSONObject) iterator.next();
					JSONObject main = currObject.getJSONObject("main");
					double temperature = main.getDouble("temp");
					double min_temp = main.getDouble("temp_min");
					double max_temp = main.getDouble("temp_max");
					int humidity = main.getInt("humidity");

					JSONArray description = currObject.getJSONArray("weather");
					JSONObject descriptionObject = description.getJSONObject(0);

					String descriptions = descriptionObject.getString("description");

					JSONObject windObj = currObject.getJSONObject("wind");
					double windSpeed = windObj.getDouble("speed");

					String date = currObject.getString("dt_txt");

					wObj.setDate(date);
					wObj.setTemperature(temperature);
					wObj.setMinTemp(min_temp);
					wObj.setMaxTemp(max_temp);
					wObj.setHumidity(humidity);
					wObj.setDescription(descriptions);
					wObj.setWindSpeed(windSpeed);

					tempList.add(wObj);
				}
			} else {
				WeatherDataObject wObj = new WeatherDataObject();
				JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
				JSONObject weatherObj = (JSONObject) weatherArray.get(0);
				String description = weatherObj.getString("description");

				JSONObject mainObject = (JSONObject) jsonObject.get("main");
				double temperature = mainObject.getDouble("temp");
				double min_temp = mainObject.getDouble("temp_min");
				double max_temp = mainObject.getDouble("temp_max");
				int humidity = mainObject.getInt("humidity");
				int date = (Integer) jsonObject.get("dt");
				JSONObject windObj = jsonObject.getJSONObject("wind");
				double windSpeed = windObj.getDouble("speed");
				String nameOfCity = jsonObject.getString("name");

				wObj.setDate(date + "");
				wObj.setTemperature(temperature);
				wObj.setMinTemp(min_temp);
				wObj.setMaxTemp(max_temp);
				wObj.setHumidity(humidity);
				wObj.setDescription(description);
				wObj.setWindSpeed(windSpeed);
				wObj.setCityName(nameOfCity);

				tempList.add(wObj);

			}
		} catch (Exception e) {
			System.out.println("In catch block @@@@@@@@@@@@");
			WeatherDataObject wObj = new WeatherDataObject();
			wObj.setDescription("City not found");
			tempList.add(wObj);
			return tempList;
		}
		return tempList;
	}

	private static JSONObject readJSONFromURL(URL url) throws MalformedURLException, IOException {
		InputStream inputStream = null;
		try {
			inputStream = new URL(url.toString()).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = br.readLine();
			JSONObject jsonObject = new JSONObject(jsonText);
			return jsonObject;
		} catch (MalformedURLException mue) {
			System.out.println("Malformed URL Exception");
			mue.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IO Exception");
			ioe.printStackTrace();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return null;
	}
}
