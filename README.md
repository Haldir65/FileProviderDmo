# FileProviderDemo
Simple showcase for Android FileProvider use
As of Android 7.0 ,due to the File System permission changes
> attempts to pass a file:// URI trigger a FileUriExposedException. 

Quote from Changes in [nougat](https://developer.android.com/about/versions/nougat/android-7.0-changes.html)

### install two Separate apps here
1. Server
   - Save bimap to sd card (*please grant storage permission*)
   - Generate Uri with FileProvider 
   - Send it to another App via Intent(startActivityForResult)
2. Client
   - Grab the Intent and read bitmap from contentProvider
   - Client set bitmap to imageView

### Based on 
- [Android doc](https://developer.android.com/training/secure-file-sharing/index.html?utm_campaign=android_series_sharingcontent_012116&utm_source=medium&utm_medium=blog)
- [Medium](https://medium.com/google-developers/sharing-content-between-android-apps-2e6db9d1368b#.oflhz5mmx)
- [FileProvider](https://developer.android.com/reference/android/support/v4/content/FileProvider.html)



