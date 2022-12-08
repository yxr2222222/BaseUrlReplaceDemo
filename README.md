## README

### 简介

在我们开发过程中，经常会遇到在开发调试过程中需要切换BaseUrl测试的情况，因为这个情况而单独打一个包是相对低效的事情。所以我们可以借助这个SDK，快速接入BaseUrl替换的功能，提高效率。在正式接入之前，请知晓该SDK仅支持OkHttp3网络框架。

### 接入流程

1. 项目根目录的 build.gradle 中添加:

   ```java
   buildscript {
       repositories {
           ...
             maven { url 'https://jitpack.io' }
       }
   }
   
   allprojects {
       repositories {
           ...
             maven { url 'https://jitpack.io' }
       }
   }
   ```

2. 添加依赖（需自行导入OkHttp和RecyclerView依赖）

   ```java
   implementation 'com.github.yxr2222222:BaseUrlReplaceDemo:v1.0.0.202212081'
   ```

3. 进行初始化

   ```java
   BaseUrlReplaceSdk.getInstance().init(this
               , new BaseUrlReplaceConfig.Builder()
                       // 正式服务器地址
                       .formalBaseUrl("https://api.release.com")
                       // 测试服务器地址
                       .debugBaseUrl("http://api.test.com")
                       // 添加其他用于快速选择的地址
                       .otherBaseUrl("http://192.168.1.7:8088")
                       // 设置回调，可以不设置
                       .onBaseUrlReplaceCallback(new OnBaseUrlReplaceCallback() {
                           @Override
                           public String getCustomFormalBaseUrl() {
                               // 获取自定义正式服务器地址，在一些需要自定义场景下可以进行自定义
                               // 返回如果是空或者不是以http://或https://开头的则默认使用formalBaseUrl
                               return null;
                           }
   
                           @Override
                           public void onBaseUrlReplace(String newBaseUrl) {
                               // BaseUrl正在被替换，newBaseUrl为替换之后的地址
                           }
                       })
                       .build());
   ```

4. 添加BaseUrlReplaceInterceptor拦截器

   ```java
   OkHttpClient httpClient = new OkHttpClient.Builder()
               // 在创建OkHttpClient时加入BaseUrlReplaceInterceptor拦截器
               .addInterceptor(new BaseUrlReplaceInterceptor())
               .build();
   ```
   
4. 打开自带的BaseUrl设置界面

   ```java
   BaseUrlReplaceSdk.getInstance().startBaseUrlReplaceEditActivity(this);
   ```
   
4. BaseUrlReplaceSdk其他提供的方法

   ```java
   // 修改测试地址
   BaseUrlReplaceSdk.getInstance().updateDebugUrl("http://www.baidu.com");
   
   // 获取BaseUrl
   BaseUrlReplaceSdk.getInstance().getBaseUrl();
   
   // 获取配置
   BaseUrlReplaceSdk.getInstance().getReplaceConfig();
   ```
   
4. 某个接口不想被替换可以让这个接口中携带BaseUrlReplaceConfig.IGNORE_REPLACE_BASE_URL参数，该参数不为空即可忽略

   ```java
   // 示例1
   "http://wwww.baidu.com?" + BaseUrlReplaceConfig.IGNORE_REPLACE_BASE_URL + "=true"
     
   // 示例2
   @GET("/getHost")
   fun getHostData(
       @Query(BaseUrlReplaceConfig.IGNORE_REPLACE_BASE_URL) ignoreReplaceHost: Boolean = true
   ): Observable<Result<ApiResponse<HostResponse>>>
   ```
   
   