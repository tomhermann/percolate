<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <title>Pot Readings</title>
    <link rel="stylesheet" href="<@spring.url '/css/'/>coffee.css">
    <script src="<@spring.url '/js/'/>sockjs-0.3.4.js"></script>
    <script src="<@spring.url '/js/'/>stomp.js"></script>
    <script src="<@spring.url '/js/'/>jquery.min.js"></script>

    <script type="text/javascript">
        var EMPTY_POT = 1.33;
        var FULL_POT = 3.4;

        var data = [];
        var stompClient = null;

        function connect() {
            var socket = new SockJS('/potws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                stompClient.subscribe('/topic/averageReadings', function(reading) {
                    showReading(JSON.parse(reading.body));
                });
                stompClient.subscribe('/topic/brewTime', function(brewTime) {
                    updateTimestamp(JSON.parse(brewTime.body));
                });
            });
        }

        function updateTimestamp(brewTime) {
            var date = new Date(brewTime.timestamp);
            var hours = date.getHours();
            var minutes = date.getMinutes();
            if(minutes < 10) {
                minutes = "0" + minutes;
            }
            $('#timestamp').text(" " + hours + ":" + minutes + " ");
            if(brewTime.reading) {
                FULL_POT = brewTime.reading.weight;
            }
        }

        function showReading(reading) {
            var potTopPx = readingToPixels(reading);
            if(typeof(potTopPx) == 'number') {
                $('#coffee').css('top', potTopPx);
                $('#last-brew').text('');
            } else {
                console.log(potTopPx);
            }
        }

        function readingToPixels(reading) {
            var coffeeWeight = reading.weight - EMPTY_POT;
            if(coffeeWeight < 0) {
                return 'pot is removed';
            }

            var fillPercent = 100 * (coffeeWeight / (FULL_POT - EMPTY_POT));

            if(fillPercent > 100) {
                return 'dispensing...';
            }
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
            <div id="last-brew"><strong>Last Brewed:</strong><span id="timestamp"> ? </span><button id="brew_now_button">Now</button></div>
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

        $("#brew_now_button").click(function(e) {
            e.stopPropagation();
            var brewTime = {timestamp: new Date().getTime()};
            $.ajax({
                type: "POST",
                url: "/pot/brewed",
                data: JSON.stringify(brewTime),
                contentType: "application/json"
            });
        });
    </script>
</body>
</html>