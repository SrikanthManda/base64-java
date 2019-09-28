package base64;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assumptions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Base64 Tests:")
class Base64Test {
	// TestInstance life-cycle set to PER_CLASS as all main class methods are static

	TestInfo testInfo;
	TestReporter testRrptr;

	@BeforeAll
	static void startTests() {
		System.out.println("Starting tests of Base64 class ...");
	}

//	@BeforeEach
//	void setUp() {
//		System.out.println("Starting a test of Base64 class ...");
//		Base64 classUnderTest = new Base64();
//		All methods of Base64 are static. So no need to setUp.
//	}

	@BeforeEach
	void init(TestInfo testInfo, TestReporter testRrptr) {
		testRrptr.publishEntry("Running " + testInfo.getDisplayName() + " of tag(s) " + testInfo.getTags());
	}

	@AfterAll
	static void endTests() {
		System.out.println("Tests of Base64 class completed.");
	}

	@Test
	@DisplayName("Conversion of Base64 characters to their 6 bit binary representations using a native API")
	@Tag("Unit")
	void to6BitBinSeqNativeTest() {
		assertThrows(InvalidBase64Character.class, () -> Base64.to6BitBinSeqNative(64));

		assertAll(() -> assertEquals(Base64.to6BitBinSeqNative(02), "000010"),
				() -> assertEquals(Base64.to6BitBinSeqNative(03), "000011"),
				() -> assertEquals(Base64.to6BitBinSeqNative(32), "100000"));
	}

	@Test
	@DisplayName("Conversion of Base64 characters to their 6 bit binary representations:")
	@Tag("Unit")
	void to6BitBinSeqTest() {
		assertEquals(Base64.to6BitBinSeq(00), "000000");
		assertEquals(Base64.to6BitBinSeq(01), "000001");

		assertAll(() -> assertEquals(Base64.to6BitBinSeq(02), "000010"),
				() -> assertEquals(Base64.to6BitBinSeq(03), "000011"),
				() -> assertEquals(Base64.to6BitBinSeq(04), "000100"),
				() -> assertEquals(Base64.to6BitBinSeq(07), "000111"),
				// () -> assertEquals(Base64.to6BitBinSeq(08), "001000"),
				() -> assertEquals(Base64.to6BitBinSeq(15), "001111"),
				() -> assertEquals(Base64.to6BitBinSeq(16), "010000"),
				() -> assertEquals(Base64.to6BitBinSeq(31), "011111"),
				() -> assertEquals(Base64.to6BitBinSeq(32), "100000"),
				() -> assertEquals(Base64.to6BitBinSeq(63), "111111"));

	}

	@Nested
	@DisplayName("Conversion of 8 bit binary representation strings to the characters they represent: ")
	@Tag("Unit")
	class toCharTest {

		@Test
		@DisplayName("Positive Scenarios: ")
		void toCharPositiveTests() {
			assertEquals(Base64.toChar("01111110"), '~');
			assertEquals(Base64.toChar("01100000"), '`');
		}

		@Test
		@DisplayName("Negative Scenarios: ")
		void toCharNegativeTests() {
			assertNotEquals(Base64.toChar("01100000"), '0');
		}
	}

	@Test
	@DisplayName("Decoding Base64 string:")
	@Tag("Unit")
	void decodeTest() {
		assertThrows(InvalidBase64Character.class, () -> Base64.decode("-"));

		assertEquals(Base64.decode("cGxlYXN1cmUu"), "pleasure.");
		assertEquals(Base64.decode("YXN1cmUu"), "asure.");
		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3VyZS4="), "any carnal pleasure.");
		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3VyZQ=="), "any carnal pleasure");
		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3Vy"), "any carnal pleasur");
		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhc3U="), "any carnal pleasu");
		assertEquals(Base64.decode("YW55IGNhcm5hbCBwbGVhcw=="), "any carnal pleas");

		assertEquals(Base64.decode("TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz\n" +
				"IHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2Yg\r" +
				"dGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGlu\r\n" +
				"dWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRo\n" +
				"ZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4="),
				"Man is distinguished, not only by his reason, but by this singular passion from other animals, " +
				"which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable " +
				"generation of knowledge, exceeds the short vehemence of any carnal pleasure.");
	}

	@Test
	@DisplayName("Main:")
	@Tag("Integration")
	void mainTest() {
		String[] oneArgInput = {"random string"};
		String[] twoArgInput = {"do what", "random string"};
		String[] decodeInvalidArgs = {"decode", "string invalid due to spaces"};

		assertThrows(IllegalArgumentException.class, () -> Base64.main(oneArgInput));
		assertThrows(IllegalArgumentException.class, () -> Base64.main(twoArgInput));
		assertThrows(InvalidBase64Character.class, () -> Base64.main(decodeInvalidArgs));

		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		// private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;
		// private final PrintStream originalErr = System.err;
		String[] encodeArgs = {"encode", "random string"};

		System.setOut(new PrintStream(outContent));
		// System.setErr(new PrintStream(errContent));
		Base64.main(encodeArgs);
		assertEquals("Well, encode function is yet to be implemented", outContent.toString());
		System.setOut(originalOut);
		//System.setErr(originalErr);
	}

	@Test
	@DisplayName("Main Success:")
	@Tag("Integration")
	void mainTest2() {
		String[] decodeValidArgs = {"decode", "YW55IGNhcm5hbCBwbGVhc3VyZS4="};
		
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;
		
		System.setOut(new PrintStream(outContent));
		Base64.main(decodeValidArgs);
		assertEquals("any carnal pleasure.", outContent.toString());
		System.setOut(originalOut);
	}

	@Disabled
	@Test
	@DisplayName("Encoding Base64 string:")
	@Tag("Unit")
	void encodeTest() {
		fail("Test cases yet to be written");
	}
}
