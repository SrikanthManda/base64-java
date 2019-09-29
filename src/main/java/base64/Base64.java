package base64;

public final class Base64 {

	private Base64() {};

	private static final String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789"
			+ "+" + "/";

	static final Exception InvalidBase64Character = null;

	public static String to6BitBinSeqNative(int num) throws InvalidBase64Character {
		if (num > 63) {
			throw new InvalidBase64Character("Invalid character in Base64 encoded string.");
		} else {
			String binaryNum = Integer.toBinaryString(num);
			int numBinBits = binaryNum.length();
			if (numBinBits < 6) {
				binaryNum = String.format("%" + (6 - numBinBits) + "s", ' ').replace(' ', '0') + binaryNum;
			}
			return binaryNum;
		}
	}

	public static String to6BitBinSeq(int num) {
		StringBuilder binaryNum = new StringBuilder();
		int numBinBits = 1;
		while (num >= 2) {
			binaryNum.append(num % 2);
			num /= 2;
			numBinBits++;
		}
		binaryNum.append(num);
		if (numBinBits < 6) {
			binaryNum.append(String.format("%" + (6 - numBinBits) + "s", ' ').replace(' ', '0'));
		}
		return binaryNum.reverse().toString();
	}

	public static char toChar(CharSequence inBinaryChrSeq) {
		int charCode = 0;
		for (int i = 0; i < 8; i++) {
			charCode += Integer.parseInt(String.valueOf(inBinaryChrSeq.charAt(i))) * Math.pow(2, (8 - i) - 1);
		}
		return (char) charCode;
	}

	public static String decode(CharSequence inTxt) throws InvalidBase64Character {
		StringBuilder outTxt = new StringBuilder();
		StringBuilder binaryTxt = new StringBuilder();

		int inTxtLen = inTxt.length();
		for (int i = 0; i < inTxtLen; i++) {
			char x = inTxt.charAt(i);
			if (x == '\n' || x == '\r') {
				continue;
			}
			int j = base64Chars.indexOf(x);
			if (j == -1) {
				if (x == '=') {
					break;
				} else {
					throw new InvalidBase64Character("Base64 encoded string has invalid character: " + x);
				}
			} else {
				binaryTxt.append(to6BitBinSeq(j));
			}
		}
		int binaryTxtLen = binaryTxt.length();
		for (int i = 0; i + 8 <= binaryTxtLen; i += 8) {
			outTxt.append(toChar(binaryTxt.subSequence(i, i + 8)));
		}
		return outTxt.toString();
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Invalid number of arguments: [encode|decode] <string>");
		} else {
			// String decode = new String("decode");
			String decode = "decode";
			if (!args[0].equals(decode) && !args[0].equals("encode")) {
				throw new IllegalArgumentException("Invalid operation: not \"encode\" or \"decode\"");
			} else if (args[0].equals(decode)) {
				System.out.print(decode(args[1]));
			} else {
				System.out.print("Well, encode function is yet to be implemented");
			}
		}
	}
}