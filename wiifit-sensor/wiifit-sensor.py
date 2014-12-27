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
   named_calibration = { 'right_top': balance_calibration[0],
                         'right_bottom': balance_calibration[1],
                         'left_top': balance_calibration[2],
                         'left_bottom': balance_calibration[3],
                       }

   while wiimote:
      wiimote.request_status()
      payload = {}
      payload['id'] = '1'
      payload['timestamp'] = time.strftime("%Y-%m-%dT%H:%M:%S", time.gmtime()) #iso8601-esque
      payload['weight'] = calcweight(wiimote.state['balance'], named_calibration) / 100.0
      
      response = requests.post('http://crazypowerful.com/percolate/pot', data=json.dumps(payload), headers={'content-type' : 'application/json'})
      print "response: (%d),  data: %s" % ( response.status_code, payload)
      sleep(0.5)
   
   wiimote.close()
   return 0

def calcweight( readings, calibrations ):
    """
    Determine the weight of the user on the board in hundredths of a kilogram
    """
    weight = 0
    for sensor in ('right_top', 'right_bottom', 'left_top', 'left_bottom'):
        reading = readings[sensor]
        calibration = calibrations[sensor]
        #if reading < calibration[0]:
        #   print "Warning, %s reading below lower calibration value" % sensor
        if reading > calibration[2]:
            print "Warning, %s reading above upper calibration value" % sensor
        # 1700 appears to be the step the calibrations are against.
        # 17kg per sensor is 68kg, 1/2 of the advertised Japanese weight limit.
        if reading < calibration[1]:
            weight += 1700 * (reading - calibration[0]) / (calibration[1] - calibration[0])
        else:
            weight += 1700 * (reading - calibration[1]) / (calibration[2] - calibration[1]) + 1700

    return weight

if __name__ == "__main__":
	sys.exit(main())
