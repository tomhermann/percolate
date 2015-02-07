var options = {
        xaxis: { mode: "time" }
};

var readings = [];
var averageReadings = [];
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
            
            while (readings.data.length > maxPoints) { 
            	readings.data.shift(); 
            }
            while (smoothedReadings.data.length > maxPoints) { 
            	smoothedReadings.data.shift(); 
            }
            if(plot) { 
                // Create the plot if it's not there already 
                plot.setData([readings, smoothedReadings]); 
                plot.setupGrid(); 
                plot.draw();
            } else if(readings.data.length > 10) { 
                // Update the plot 
                plot = $.plot("#chart", [readings, smoothedReadings], { 
                    xaxis:{ 
                        mode: "time", 
                        timeformat: "%H:%M:%S", 
                        minTickSize: [2, "second"], 
                    }, 
                    yaxis: { 
                        min: -1, 
                        max: 20 
                    } 
                }, options); 
                plot.setupGrid();
                plot.draw(); 
            } 
        });
    });
}
