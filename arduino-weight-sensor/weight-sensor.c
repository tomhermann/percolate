// Arduino as load cell amplifier
// by Christian Liljedahl 
// christian.liljedahl.dk

// Load cells are linear. So once you have established two data pairs, you can interpolate the rest.

// Step 1: Upload this sketch to your arduino board

// You need two loads of well know weight. In this example A = 10 kg. B = 30 kg
// Put on load A 
// read the analog value showing (this is analogvalA)
// put on load B
// read the analog value B

// Enter you own analog values here
float loadA = 10; // kg
int analogvalA = 200; // analog reading taken with load A on the load cell

float loadB = 30; // kg 
int analogvalB = 600; // analog reading taken with load B on the load cell

// Upload the sketch again, and confirm, that the kilo-reading from the serial output now is correct, using your known loads

float analogValueAverage = 0;

// How often do we do readings?
long time = 0; // 
int timeBetweenReadings = 200; // We want a reading every 200 ms;

void setup() {
  Serial.begin(9600);
}

void loop() {
  int analogValue = analogRead(0);

  // running average - We smooth the readings a little bit
  analogValueAverage = 0.99*analogValueAverage + 0.01*analogValue;

  // Is it time to print? 
  if(millis() > time + timeBetweenReadings){
    float load = analogToLoad(analogValueAverage);

    Serial.print("analogValue: ");
    Serial.println(analogValueAverage);
    Serial.print("             load: ");
    Serial.println(load,5);
    time = millis();
  }
}

float analogToLoad(float analogval){
  // using a custom map-function, because the standard arduino map function only uses int
  float load = mapfloat(analogval, analogvalA, analogvalB, loadA, loadB);
  return load;
}

float mapfloat(float x, float in_min, float in_max, float out_min, float out_max) {
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}