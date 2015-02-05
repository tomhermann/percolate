<!DOCTYPE html>
<html>
<head>
    <title>Pot Readings</title>
    <link rel="stylesheet" href="css/coffee.css">
    <script src="js/sockjs-0.3.4.js"></script>
    <script src="js/stomp.js"></script>
    <script src="js/jquery.min.js"></script>

    <script type="text/javascript">
        var EMPTY_POT = 1.33;
        var FULL_POT = 3.4;

        var data = [];
        var stompClient = null;

        function connect() {
            var socket = new SockJS('/potws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                stompClient.subscribe('/topic/readings', function(reading) {
                    showReading(JSON.parse(reading.body));
                });
            });
        }

        function showReading(reading) {
            $('#coffee').css('top', readingToPixels(reading));
        }

        function readingToPixels(reading) {
            var coffeeWeight = reading.weight - EMPTY_POT;
            if(coffeeWeight < 0) {
                return 200;
            }

            var fillPercent = 100 * (coffeeWeight / (FULL_POT - EMPTY_POT));
            return (100 - fillPercent) * 2;
        }
    </script>
</head>
<body>
    <div id="coffee-view">
        <div class="container">
            <div id="background"></div>
            <div id="coffee" style="top:200px"></div>
            <div id="inside"></div>
            <div id="last-brew"><strong>Last Brewed:</strong> 2:34:56</div>
        </div>
    </div>
    <div id="data-view" style="display:none">
        <div id="stats">
            <ul>
                <li>
                    <label>Weight:</label>
                    <span>10000</span>
                </li>
                <li>
                    <label>Time:</label>
                    <span>12:12:12</span>
                </li>
                <li>
                    <label>Flavor:</label>
                    <span>Coffee</span>
                </li>
                <li>
                    <label>Location:</label>
                    <span>6th Floor Secure</span>
                </li>
            </ul>

        </div>
    </div>
    <script>
        $(document).ready(function () {
            connect();
        });

        $("body").click(function () {
            $("#coffee-view").fadeToggle();
            $("#data-view").fadeToggle();
        });
    </script>
</body>
</html>