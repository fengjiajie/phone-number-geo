package cn.kbyte.utils.phone;

public class PhoneNumberInfo {
  private String phoneNumber;
  private String province;
  private String city;
  private String zipCode;
  private String areaCode;
  private String phoneType;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public String getPhoneType() {
    return phoneType;
  }

  public void setPhoneType(String phoneType) {
    this.phoneType = phoneType;
  }

  @Override public String toString() {
    return "PhoneNumberInfo{" +
        "phoneNumber='" + phoneNumber + '\'' +
        ", province='" + province + '\'' +
        ", city='" + city + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", areaCode='" + areaCode + '\'' +
        ", phoneType='" + phoneType + '\'' +
        '}';
  }
}
