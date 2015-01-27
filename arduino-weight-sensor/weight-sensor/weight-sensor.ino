// Step 1: Upload this sketch to your arduino board

// You need two loads of well know weight. In this example A = 10 kg. B = 30 kg
// Put on load A 
// read the analog value showing (this is analogvalA)
// put on load B
// read the analog value B

// Enter you own analog values here
//float loadA = 4.140; // kg
//int analogvalA = 32; // analog reading taken with load A on the load cell
//4140 g
// 3
float loadA = 0;
int analogvalA = 20.5;


float loadB = 6.180; // kg 
int analogvalB = 41; // analog reading taken with load B on the load cell

// Upload the sketch again, and confirm, that the kilo-reading from the serial output now is correct, using your known loads

float loadValueAverage =0;

int loadValueCount = 25;
float lastLoadValues[25];
int readingCount = 0;

// How often do we do readings?
long lastPrint = 0; // 
long lastReading = 0;
int timeBetweenReadings = 200; // We want a reading every 200 ms;
int timeBetweenPrints = 1000;

void setup() {
  Serial.begin(9600);
}

void loop() {
  int analogValue = analogRead(0);

// running average - We smooth the readings a little bit
//  analogValueAverage = 0.99*analogValueAverage + 0.01*analogValue;  
  
  if(millis() > lastReading + timeBetweenReadings) {
    lastLoadValues[readingCount] = analogToLoad(analogValue);
    readingCount++;
    if(readingCount > loadValueCount) {
      loadValueAverage = average(lastLoadValues, readingCount);
      readingCount = 0;
      lastReading = millis();
    }
  }
     // Is it time to print? 
  if(millis() > lastPrint + timeBetweenPrints){
    float load = analogToLoad(analogValue);
//    Serial.print("analogValue: ");
//    Serial.println(analogValue);
    Serial.print("load: ");
    Serial.println(load,5);
    lastPrint = millis();
  }
}

float analogToLoad(float analogval){
  // using a custom map-function, because the standard arduino map function only uses int
  float load = mapfloat(analogval, analogvalA, analogvalB, loadA, loadB);
  return load;
}

float average(float values[], int numberOfElements) {
  float sum = 0;
  for(int i=0; i<numberOfElements; i++) {
    sum+= values[i];
  }
  return sum / numberOfElements;
}

float mapfloat(float x, float in_min, float in_max, float out_min, float out_max) {
  
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}
