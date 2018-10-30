package cn.kbyte.utils.phone;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Created by fengjiajie on 16/10/12.
 */
public class PhoneNumberGeo {

  private static final String[] PHONE_NUMBER_TYPE = {null, "移动", "联通", "电信", "电信虚拟运营商", "联通虚拟运营商", "移动虚拟运营商"};
  private static final int INDEX_SEGMENT_LENGTH = 9;
  private static final int DATA_FILE_LENGTH_HINT = 3747505;
  private static final String PHONE_DAT_FILE_PATH = "phone-number-geo/phone.dat";

  private static byte[] dataByteArray;
  private static int indexAreaOffset;
  private static int phoneRecordCount;

  private ByteBuffer byteBuffer;

  private static synchronized void initData() {
    if (dataByteArray == null) {
      ByteArrayOutputStream byteData = new ByteArrayOutputStream(DATA_FILE_LENGTH_HINT);
      byte[] buffer = new byte[1024];

      int readBytesLength;
      try (InputStream inputStream = PhoneNumberGeo.class.getClassLoader().getResourceAsStream(PHONE_DAT_FILE_PATH)) {
        while ((readBytesLength = inputStream.read(buffer)) != -1) {
          byteData.write(buffer, 0, readBytesLength);
        }
      } catch (Exception e) {
        System.err.println("Can't find phone.dat in classpath: " + PHONE_DAT_FILE_PATH);
        e.printStackTrace();
        throw new RuntimeException(e);
      }

      dataByteArray = byteData.toByteArray();

      ByteBuffer byteBuffer = ByteBuffer.wrap(dataByteArray);
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      int dataVersion = byteBuffer.getInt();
      indexAreaOffset = byteBuffer.getInt();
      phoneRecordCount = (dataByteArray.length - indexAreaOffset) / INDEX_SEGMENT_LENGTH;

      // print data version
      // System.out.println(dataVersion);
      // print record count
      // System.out.println(phoneRecordCount);
    }
  }

  public PhoneNumberGeo() {
    initData();

    byteBuffer = ByteBuffer.wrap(dataByteArray);
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
  }

  public PhoneNumberInfo lookup(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.length() > 11 || phoneNumber.length() < 7) {
      return null;
    }
    int phoneNumberPrefix;
    try {
      phoneNumberPrefix = Integer.parseInt(phoneNumber.substring(0, 7));
    } catch (Exception e) {
      return null;
    }
    int left = 0;
    int right = phoneRecordCount;
    while (left <= right) {
      int middle = (left + right) >> 1;
      int currentOffset = indexAreaOffset + middle * INDEX_SEGMENT_LENGTH;
      if (currentOffset >= dataByteArray.length) {
        return null;
      }

      byteBuffer.position(currentOffset);
      int currentPrefix = byteBuffer.getInt();
      if (currentPrefix > phoneNumberPrefix) {
        right = middle - 1;
      } else if (currentPrefix < phoneNumberPrefix) {
        left = middle + 1;
      } else {
        int infoBeginOffset = byteBuffer.getInt();
        int phoneType = byteBuffer.get();

        int infoLength = -1;
        for (int i = infoBeginOffset; i < indexAreaOffset; ++i) {
          if (dataByteArray[i] == 0) {
            infoLength = i - infoBeginOffset;
            break;
          }
        }

        String infoString = new String(dataByteArray, infoBeginOffset, infoLength, StandardCharsets.UTF_8);
        String[] infoSegments = infoString.split("\\|");

        PhoneNumberInfo phoneNumberInfo = new PhoneNumberInfo();
        phoneNumberInfo.setPhoneNumber(phoneNumber);
        phoneNumberInfo.setProvince(infoSegments[0]);
        phoneNumberInfo.setCity(infoSegments[1]);
        phoneNumberInfo.setZipCode(infoSegments[2]);
        phoneNumberInfo.setAreaCode(infoSegments[3]);
        phoneNumberInfo.setPhoneType(PHONE_NUMBER_TYPE[phoneType]);
        return phoneNumberInfo;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    PhoneNumberGeo phoneNumberGeo = new PhoneNumberGeo();
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      System.out.println(phoneNumberGeo.lookup(scanner.next()));
    }
  }
}


