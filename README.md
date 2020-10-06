# pp-views
PixelPerfect Views, make your design of the app, same for different devices. To developed by the UpGames Android team.

### QuickStart
--------------------------
Call PerfectDesignIniter from Application class and from first Activity for setup base screen params.

```kotlin
override fun onCreate() {
    ...
    PerfectDesignIniter.init(this)
} 
``` 

For Activity
```kotlin
override fun onStart() {
    ...
    PerfectDesignIniter.onStart(this)
} 
``` 

And if you use a keyboard, for example into chat with adjustPan, need to save keyboard height
```kotlin
fun onOpenKeyboard(height: Int) {
    PerfectDesignIniter.onOpenKeyboard(height)
} 
``` 