bitcoinbiz
==========


This is the source code for the Bitcoin Businesses Android App . You can see the currently released version here : https://play.google.com/store/apps/details?id=com.ragmondo

I don't have enough time to develop this app properly so thought I would put it out to the community to do with as it will. 

The only condition is regarding the data access - if you do use the /json or /kml endpoints to access the bitcoin business directory, then you must include the server generated links at the end.

Other than that, feel free ! Let me know if you want push access btw.

_______________________________________

Getting started steps:

1. Fork this project

2. Install Google Play Services and add it as a libary project to this project:
http://developer.android.com/google/play-services/setup.html

3. Replace your local debug.keystore with the one in the /misc folder OR set a custom debug.keystore in the preferences/android/build and target the file in the /misc folder

Now you should be able to run and debug the project. 

Intellij user ? Follow the directions below in order to get play services correctly included in the app

http://stackoverflow.com/questions/13719263/unable-instantiate-android-gms-maps-mapfragment/13744765#13744765
