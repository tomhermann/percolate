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
        
        var data = [];
        var stompClient = null;

        function connect() {
            var socket = new SockJS('/potws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                
                stompClient.subscribe('/topic/readings', function(reading) {
                    showReading(JSON.parse(reading.body));
                });
            });
        }

        function showReading(reading) {
            var point = [reading.creationDate, reading.weight]; 
            data.push(point);
            $.plot("#chart", [ data ], options);
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
    });
</script>
</body>
</html>