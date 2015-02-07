<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
    <title>Pot Graph</title>
    <script src="<@spring.url '/js/'/>sockjs-0.3.4.js"></script>
    <script src="<@spring.url '/js/'/>stomp.js"></script>
    <script src="<@spring.url '/js/'/>jquery.min.js"></script>
    <script src="<@spring.url '/js/'/>flot/jquery.flot.min.js"></script>
    <script src="<@spring.url '/js/'/>flot/jquery.flot.time.min.js"></script>
    <script src="<@spring.url '/js/'/>graph.js"></script>                                
</head>
<body>

<div>
    <div id="chart-container" style="width: 1280px; height: 700px;">
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