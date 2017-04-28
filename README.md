# License Viewer
License Viewer is a library to show legal informations of your Android app.

![Shows list of licenses](images/1.png) ![Shows text of license](images/2.png)

## How to use
0. Add following:
root build.gradle
```
repositories {
    ...
    maven {
        url  "http://dl.bintray.com/kofuk/maven" 
    }
    ...
}
```
module build.gradle
```
compile 'com.chronoscoper.library:license-viewer:1.1'
```

1. Put your license text files in your `assets/license` folder. (File name must be `LICENSE_NAME.txt` format)

2. Call `LicenseViewer.open(Context context, String title)`.
Title will be displayed as an Activity title in the ActionBar.

## License
This library is licensed under the Apache License 2.0.
For more details, please read [LICENSE](LICENSE).
