# License Viewer
License Viewer is a library to show legal information of your Android app.

![Shows list of licenses](images/1.png) ![Shows text of license](images/2.png)

## Usage
0. Add dependency:
project `build.gradle`
```
repositories {
    ...
    maven {
        url  "http://dl.bintray.com/kofuk/maven" 
    }
    ...
}
```
module `build.gradle`
```
compile 'com.chronoscoper.library:license-viewer:1.3'
```

1. Put your license text files in your `assets/license` folder. (File name must be `LICENSE_NAME.txt` format)

2. Open licenses from your java code.
```
public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Opens the list of licenses
        LicenseViewer.open(this, "Licenses");
    }
}
```
Title will be displayed as an Activity title in the ActionBar.

## License
This library is licensed under the Apache License 2.0.
For more details, please read [LICENSE](LICENSE).
