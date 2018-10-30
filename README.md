## 手机号码归属地本地解析(Java)

参照 ls0f/phone 实现的 Java 版本。原库以及其他语言版本地址：

* python: https://github.com/ls0f/phone
* go: https://github.com/xluohome/phonedata

2018.10.30 升级到了 xluohome 提供的最新版库。

非常感谢 ls0f、xluohome 提供号码归属地库。

如该代码或库文件侵犯了您的权益，请联系我删除，谢谢。

#### 使用

```java
PhoneNumberGeo phoneNumberGeo = new PhoneNumberGeo();
PhoneNumberInfo phoneNumberInfo = phoneNumberGeo.lookup("13006150000");

// PhoneNumberInfo{phoneNumber='13006150000', province='湖北', city='武汉', zipCode='430000', areaCode='027', phoneType='联通'}
```

## License
#### [MIT](https://opensource.org/licenses/mit-license.php)
