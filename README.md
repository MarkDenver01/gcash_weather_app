# gcash_weather_app

![weather_app](https://user-images.githubusercontent.com/20502334/218387747-32d36071-16eb-435a-8e44-74c88cd53636.png)
![clean_arch_mvvm](https://user-images.githubusercontent.com/20502334/218391312-1baf500a-7aec-4458-a8e6-89f04d9e5c9b.png)


How to set up the public api key
1. Go to data folder > remote > client then open the ApiClient.java
2. Public API KEY: 8d9f418904b3834e5f8f7cc3689f0c5c

If in-case cuberto liquid swipe is error, please do the following:

1. Generate a Personal Access Token for GitHub

Inside you GitHub account:

Settings -> Developer Settings -> Personal Access Tokens -> Generate new token

Make sure you select the following scopes (“ read:packages”) and Generate a token
After Generating make sure to copy your new personal access token. You cannot see it again! The only option is to generate a new key.

2. Get your user id from:

API: https://api.github.com/users/<your_username>

3. Go to Project folder structure
4. Open github.properties
5. Input the following:

gpr.usr = your_user_id

gpr.key = your_access_token_key


4. Saved and rebuild the app

Following Used:
1. MVVM + Clean Architecture
2. DI / Depency Injection / Modularization
3. Data binding

Library used:
1. RxJava
2. Retrofit
3. OkHttp
4. Lottie
5. Cuberto Liquid Swipe

6. Room Database
7. Dependency injection

