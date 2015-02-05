<!DOCTYPE html>
<html>
<head>
    <title>Pot Readings</title>
    <script src="js/sockjs-0.3.4.js"></script>
    <script src="js/stomp.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/flot/jquery.flot.min.js"></script>
    <script src="js/flot/jquery.flot.time.min.js"></script>
    
    <script type="text/javascript">
        var options = {
            xaxis: { mode: "time" }
        };
        
        var data = [];
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
        }

        function connect() {
            var socket = new SockJS('/potws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                
                stompClient.subscribe('/topic/readings', function(reading) {
                    showReading(JSON.parse(reading.body));
                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
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
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    
    <div id="chart-container" style="width: 1300px; height: 900px;">
        <p id="chart" style="width: 100%; height: 100%;"></p>
    </div>
</div>
</body>
</html>