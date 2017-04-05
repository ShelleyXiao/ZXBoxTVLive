package zx.com.zxvboxtvlive.utils;

import android.text.TextUtils;
import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinUtils implements Comparator<Object> {
	/**
	 * 比较两个字符串
	 */
	public int compare(Object o1, Object o2) {
		String[] name1 = (String[]) o1;
		String[] name2 = (String[]) o2;
		String str1 = getPingYin(name1[0]);
		String str2 = getPingYin(name2[0]);
		int flag = str1.compareTo(str2);
		return flag;
	}

	/**
	 * 将字符串中的中文转化为拼音,其他字符不变
	 * 
	 * @param inputString
	 * @return
	 */
	public static String getPingYin(String inputString) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] input = inputString.trim().toCharArray();// 把字符串转化成字符数组
		String output = "";

		try {
			for (int i = 0; i < input.length; i++) {
				// \\u4E00是unicode编码，判断是不是中文
				if (Character.toString(input[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					// 将汉语拼音的全拼存到temp数组
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(
							input[i], format);

//					Logger.getLogger().i(temp[0]);
					// 取拼音的第一个读音
					output += temp[0].toUpperCase().charAt(0);
				}
				// 大写字母转化成小写字母
				else if (input[i] > 'A' && input[i] < 'Z') {
					output += Character.toString(input[i]);
//					output = output.toLowerCase();
				}
//				output += Character.toString(input[i]);
			}
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}
		return output;
	}


	public static String processTVPinyinLog(String tvName) {
		if(TextUtils.isEmpty(tvName)) {
			return null;
		}

		if(tvName.contains("CCTV")) {
			String regEx="[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(tvName);

			return "cctv-" + m.replaceAll("").trim();
		}

		String pinyin = PinyinUtils.getPingYin(tvName).toUpperCase();
		if(pinyin.contains("WS")) {

			return pinyin.substring(0, 2);
		}

		return null;
	}
}
