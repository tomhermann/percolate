<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <title>Pot Readings</title>
    <script src="<@spring.url '/js/'/>sockjs-0.3.4.js"></script>
    <script src="<@spring.url '/js/'/>stomp.js"></script>
    <script src="<@spring.url '/js/'/>jquery.min.js"></script>
    <script src="<@spring.url '/js/'/>flot/jquery.flot.min.js"></script>
    <script src="<@spring.url '/js/'/>flot/jquery.flot.time.min.js"></script>
                                      
    <script type="text/javascript">
        var options = {
            xaxis: { mode: "time" }
        };
        
        var readings = [];
        var averageReadings = [];
        var stompClient = null;
	
        function connect() {
            var socket = new SockJS('/potws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                
                stompClient.subscribe('/topic/readings', function(reading) {
                    updateSeriesWithValue(readings, reading);
                });
                
                stompClient.subscribe('/topic/averageReadings', function(reading) {
                    updateSeriesWithValue(averageReadings, reading);
                });
            });
        }

		function updateSeriesWithValue(series, reading) {
            var parsed = JSON.parse(reading.body);
            var oldestAllowedReading = parseInt(parsed.creationDate) - (1 * 60 * 1000);
            series.push([parsed.creationDate, parsed.weight]);
            drawChart();
		}
		
        function drawChart() {
            $.plot("#chart", [{ label: "readings",  data: readings}, { label: "mavg(readings)",  data: averageReadings} ], options);
        }
    </script>
</head>
<body>

<div>
    <div id="chart-container" style="width: 1300px; height: 900px;">
        <p id="chart" style="width: 100%; height: 100%;"></p>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        connect();
        drawChart();
    });
</script>
</body>
</html>