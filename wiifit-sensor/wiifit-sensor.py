#!/usr/bin/python
import sys
import cwiid
import json
import time
import requests
from time import sleep

def main():
   print 'Put Wii Balance Board in discoverable mode now (press red sync button)...'
   wiimote = None
   connectionAttempts = 1
   
   while not wiimote:
      try:
         connectionAttempts += 1
         wiimote = cwiid.Wiimote()
      except RuntimeError:
          if(connectionAttempts > 10):
             quit()
             break
          print "Retrying connection to balance board, attempt: %d of 10" % connectionAttempts

   wiimote.rpt_mode = cwiid.RPT_BALANCE | cwiid.RPT_BTN
   wiimote.request_status()
   balance_calibration = wiimote.get_balance_cal()
     
   while wiimote:
      wiimote.request_status()
      payload = {}
      payload['id'] = '1'
      payload['weight'] = calcweight(wiimote.state['balance'], balance_calibration) / 100.0
      try:
         response = requests.post('http://crazypowerful.com/perculate/readings', data=json.dumps(payload), headers={'content-type' : 'application/json'})
      except ConnectionError:
         print "Connection Error"
	  print "response: (%d), data: %s" % ( response.status_code, payload)
      sleep(0.5)
   
   wiimote.close()
   return 0

def calcweight( readings, balance_calibration ):
   """
   Determine the weight of the user on the board in hundredths of a kilogram
   """
   weight = 0
    
   sensors = ['right_top', 'right_bottom', 'left_top', 'left_bottom']   

   for sensor in sensors:
      reading = readings[sensor]
      calibration = balance_calibration[sensors.index(sensor)]

      # 1700 appears to be the step the calibrations are against.
      # 17kg per sensor is 68kg, 1/2 of the advertised Japanese weight limit.

      if(reading < calibration[1]):
         weight += 1700 * (reading - calibration[0]) / (calibration[1] - calibration[0])
      else:
         weight += 1700 * (reading - calibration[1]) / (calibration[2] - calibration[1]) + 1700
   return weight

if __name__ == "__main__":
   sys.exit(main())
