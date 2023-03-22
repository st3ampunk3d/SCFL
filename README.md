# Overview
### What This App Does
This program is the beginning of a SuperCross Fantasy League Mobile app. It parses rider and event details from [supercrosslive.com](http://supercrosslive.com)
and allows the user to rank the riders in the order they think the riders will place.

### Why I Wrote It
The primary reason for developing this software is to get a crash course in mobile app development. The goal is to develop a fully functional Android App. So far I've had to learn about gradle, Threading, Android view Fragments, and writing
custom adapters for GUI elements.

### See It in Action
Here is a [demo](https://cdnapisec.kaltura.com/index.php/extwidget/preview/partner_id/1157612/uiconf_id/42438192/entry_id/1_35dht3g2/embed/dynamic) where I show some of the code and the app running in an emulator.

Here is the [demo video](https://cdnapisec.kaltura.com/index.php/extwidget/preview/partner_id/1157612/uiconf_id/42438192/entry_id/1_mdimiiga/embed/dynamic) from the console app I made previously. The console app served as the base code for most of the app.

# Development Environment
### Android Studio
Android studio shares many of its features with IntelliJ. There are features specific to mobile development such as emulators for many mobile devices and a WYSWIG
editor for creating view layouts quickly.

### Kotlin v1.8.10
Kotlin is still a pretty new programming language. From what I understand, it expands and simplifies Java.
The basic syntax was easy but there are many advanced features

### JSoup - Java HTML Parser
JSoup is a Java library for HTML parsing. A great thing about Kotlin is that it is completely compatible with Java libraries.

# Useful Websites
This was a learning experience for me. I spent lots of time in tutorials and searching the web while I got the hang of it.
- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Android App Tutorial](https://developer.android.com/codelabs/build-your-first-android-app-kotlin#6)
- [Using JSoup](https://thetechstack.net/using-jsoup-with-kotlin-to-scrape-wiki-pages/)
- [Android Studio](https://developer.android.com/studio?gclid=CjwKCAjwzuqgBhAcEiwAdj5dRr2pXoUVhx6aZgmTbRHX3aP1YQTWCJwnsBVGBta2MOjychaJ6VFueBoC3R8QAvD_BwE&gclsrc=aw.ds)

# Future Work
This project is just the start of the app.

Some of the things that still need to happen are:
- Write the .json data to a file on the device
- Compare the User's picks to the actual event results and calculate points
- Increase performance by saving the events and results to .json and load the views from there. The HTML parsing is very slow and not necessary every time a page is loaded.
