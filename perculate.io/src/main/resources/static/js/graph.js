var maxPoints = 100;
var stompClient = null;
var plot = null;

var readings = { 
    label: "Readings",
    lines: { 
        show: true 
    }, 
    points: { 
        show:true 
    }, 
    data: [] 
}; 

var smoothedReadings = { 
    label: "Smoothed Readings", 
    lines: { 
        show: true 
    }, 
    points: { 
        show:true 
    }, 
    data: [] 
}; 

function connect() {
    var socket = new SockJS('/potws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/readings', function(reading) {
            var parsed = JSON.parse(reading.body);
            readings.data.push([parsed.creationDate, parsed.weight]);
            smoothedReadings.data.push([parsed.creationDate, parsed.avgWeight]);
            
            removeOldPoints(readings.data);
            removeOldPoints(smoothedReadings.data);
            
            if(!plot) {
            	plot = $.plot("#chart", [readings, smoothedReadings], { 
                    xaxis:{ 
                        mode: "time", 
                        timeformat: "%H:%M:%S", 
                        minTickSize: [2, "second"], 
                    }
                }); 
            } else {
            	plot.setData([readings, smoothedReadings]); 
            }
            redrawPlot();
        });
    });
}

function redrawPlot() {
    plot.setupGrid();
    plot.draw(); 
}

function removeOldPoints(data) {
	while(data.length > maxPoints) {
		data.shift(); 
	}
}