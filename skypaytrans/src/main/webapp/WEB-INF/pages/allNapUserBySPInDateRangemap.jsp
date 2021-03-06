<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCUefKYeWbZtdax9fnCdEHOLk9hOCia-NQ&sensor=true">
    </script>
    <script type="text/javascript">
      var map;
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(28.4978459, 77.4070798),
          zoom: 5,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map_canvas"),
            mapOptions);
      }
    </script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>    
  </head>
  <body onload="initialize()">
    <div id="map_canvas" style="width:100%; height:100%"></div>
    <script type="text/javascript">
     
    $(document).ready(function() {
        $.getJSON("../admin/getAllNAPUserBySPLatLng", function(json1) {
          $.each(json1, function(key, data) {
            var latLng = new google.maps.LatLng(data.lat, data.lng); 
            // Creating a marker and putting it on the map
            var marker = new google.maps.Marker({
                position: latLng,
                title: data.title + "  (" +data.phNumber+")"
            });
            marker.setMap(map);
          });
        });
      });
    </script>
  </body>
</html>