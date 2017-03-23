<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.weatherinfo.core.WeatherDataObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	List<WeatherDataObject> eList = (List<WeatherDataObject>) request.getAttribute("weatherResponse");
	request.setAttribute("eList", eList);
%>

<html>
<head>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script
	src="http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.js"
	type="text/javascript"></script>

<style type="text/css">
@import
	url("http://www.google.com/uds/solutions/dynamicfeed/gfdynamicfeedcontrol.css")
	;

#feedControl {
	margin-top: 10px;
	margin-left: 26px;
	margin-right: auto;
	width: 540px;
	font-size: 12px;
	color: #9CADD0;
	height: 300px;
}
</style>
<script type="text/javascript">
	function load() {
		var feed = "http://feeds.bbci.co.uk/news/world/rss.xml";
		new GFdynamicFeedControl(feed, "feedControl");

	}
	google.load("feeds", "1");
	google.setOnLoadCallback(load);
</script>
<title>Response JSP Page</title>

<style>
div.form {
	background-color: #087810;
	color: white;
	height: 80px;
}

table {
	table-layout: fixed;
	background-color: #BBBEB7;
	width: 392px;
	height: 286px;
}

td {
	font-family: monospace;
	font-size: 70;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

tr:hover {
	background-color: #569108;
}

table.forecastTable {
	table-layout: fixed;
	width: 324px;
	height: 80px;
}

#table_info {
	background-image: url(./images/grad.gif);
	background-repeat: no-repeat;
	background-position: center center;
	width: 30%;
	margin: 20%;
	height: 300px;
}

#table_info_two {
	background-image: url(./images/grad.gif);
	background-repeat: no-repeat;
}

#map {
	height: 80%;
	width: 30%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
}

div.a2a_custom_style {
	line-height: 32px;
	margin-left: 26px;
}

div.form {
	background-color: #087810;
	color: white;
	height: 80px;
}

div.header {
	height: 120px;
	background-color: green;
}

div.bodystyle {
	margin-top: 2px;
	height: 300px;
}

div.map {
	height: 316px;
	background-color: #ffee23;
	width: 30%;
	margin-left: 2px;
	margin-top: 2px;
	margin-bottom: 2px;
}

div.footer {
	height: 120px;
	background-color: #ffee23;
}

img {
    max-width: 100%;
    height: auto;
}

div.item {
    width: 320px;
    height: 420px;
    height: auto;
    float: left;
    margin: 3px;
    padding: 3px;
}
</style>
</head>

<body>
	<div class="header">
		<div id="header_div"
			style="float: left; width: 70%; font-family: fantasy;">
			<h1 class="header_h1" align="left" style="padding-left: 16px;">Weather
				Info</h1>
		</div>

		<div id="form" style="float: left; width: 30%; font-family: fantasy;">
			<p style="font-family: fantasy; padding-left: 8px;">Weather info
				fetched from www.openweatherinfo.org</p>
		</div>
	</div>

	<div class="bodystyle">
		<div id="table_info"
			style="float: left; height: 285px; width: 30%; padding-right: 4px; margin: 1px;">
			<table>
				<%
					WeatherDataObject wObj = eList.get(0);
				%>
				<tr>
					<td width="40%">Date</td>
					<td width="60%" style="padding-left: 50px;"><%=wObj.getDate()%></td>
				</tr>
				<tr>
					<td>Temperature</td>
					<td style="padding-left: 50px;"><%=wObj.getTemperature()%></td>
				</tr>
				<tr>
					<td>Min-Temperature</td>
					<td style="padding-left: 50px;"><%=wObj.getMinTemp()%></td>
				</tr>
				<tr>
					<td>Max-Temperature</td>
					<td style="padding-left: 50px;"><%=wObj.getMaxTemp()%></td>
				</tr>
				<tr>
					<td>Humidity</td>
					<td style="padding-left: 50px;"><%=wObj.getHumidity()%></td>
				</tr>
				<tr>
					<td>Description</td>
					<td style="padding-left: 50px;"><%=wObj.getDescription()%></td>
				</tr>
				<tr>
					<td>Wind speed</td>
					<td style="padding-left: 50px;"><%=wObj.getWindSpeed()%></td>
				</tr>
				<tr>
					<td>City Name</td>
					<td style="padding-left: 50px;"><%=wObj.getCityName()%></td>
				</tr>
			</table>
		</div>

		<div
			style="float: left; height: 316px; width: 38%; padding-left: 4px; background-color: #ffffff; margin-left: 2px; margin-top: 2px; margin-bottom: 2px;">
			<%
				WeatherDataObject wObjMain = eList.get(0);
			%>
			<h1 style="margin-left: 26px; margin-right: auto; font-family: monospace; font-size: 100;">
				<%=wObjMain.getCityName()%>
				<i class="material-icons">cloud</i>
				<br>
				<%=wObjMain.getTemperature()%>
				F
			</h1>

			<div style="margin-left: 22px;">
				<!-- AddToAny BEGIN -->
				<div class="a2a_kit a2a_kit_size_32 a2a_default_style">
					<a class="a2a_dd" href="https://www.addtoany.com/share"></a> <a
						class="a2a_button_facebook"></a> <a class="a2a_button_twitter"></a>
					<a class="a2a_button_google_plus"></a>
				</div>
				<script async src="https://static.addtoany.com/menu/page.js"></script>
				<!-- AddToAny END -->

			</div>
			
			<!-- <div class="item">
    			<img src="tram.jpg">
 			</div> -->

			<div id="body">
				<div id="feedControl">Loading...</div>
			</div>
		</div>
		<div id="table_info_two"
			style="float: right; height: 840px; width: 24%; background-color: green; padding-right: 4px; margin-top: 2px; margin-bottom: 2px;">
			<table class="forecastTable">
				<%
					for (WeatherDataObject wObjForecast : eList) {
				%>
				<tr>
					<td>Date</td>
					<td style="padding: 20px;"><%=wObjForecast.getDate()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>

	<div class="map_class" id="map">
		<h1>A map goes here...</h1>
	</div>

	<script>
		// Note: This example requires that you consent to location sharing when
		// prompted by your browser. If you see the error "The Geolocation service
		// failed.", it means you probably did not give permission for the browser to
		// locate you.

		function initMap() {
			var map = new google.maps.Map(document.getElementById('map'), {
				center : {
					lat : -34.397,
					lng : 150.644
				},
				zoom : 15
			});
			var infoWindow = new google.maps.InfoWindow({
				map : map
			});

			// Try HTML5 geolocation.
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					var pos = {
						lat : position.coords.latitude,
						lng : position.coords.longitude
					};

					infoWindow.setPosition(pos);
					infoWindow.setContent('Location found.');
					map.setCenter(pos);
				}, function() {
					handleLocationError(true, infoWindow, map.getCenter());
				});
			} else {
				// Browser doesn't support Geolocation
				handleLocationError(false, infoWindow, map.getCenter());
			}
		}

		function handleLocationError(browserHasGeolocation, infoWindow, pos) {
			infoWindow.setPosition(pos);
			infoWindow
					.setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.'
							: 'Error: Your browser doesn\'t support geolocation.');
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDzJz8a9IM3y8P8uTZoBGWzl7lNTaeTeYE&callback=initMap">
		
	</script>

	<div class="footer">
		<h2 style="position: center">Here will be the footer..</h2>
	</div>
</body>
</html>