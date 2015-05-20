import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {
	AES aes = new AES(128, null, null);

	@Test
	public void SubBytesTest() {
		byte[] input = new byte[]{
				(byte)0x19, (byte)0x3D, (byte)0xE3, (byte)0xBE, 
				(byte)0xA0, (byte)0xF4, (byte)0xE2, (byte)0x2B, 
				(byte)0x9A, (byte)0xC6, (byte)0x8D, (byte)0x2A,
				(byte)0xE9, (byte)0xF8, (byte)0x48, (byte)0x08
		};

		byte[] output = new byte[]{
				(byte)0xD4, (byte)0x27, (byte)0x11, (byte)0xAE, 
				(byte)0xE0, (byte)0xBF, (byte)0x98, (byte)0xF1, 
				(byte)0xB8, (byte)0xB4, (byte)0x5D, (byte)0xE5,
				(byte)0x1E, (byte)0x41, (byte)0x52, (byte)0x30
		};
		
		aes.SubBytes(input, false);
		for (int i=0; i<16; i++)
			assertEquals(input[i], output[i]);
	}

	@Test
	public void ShiftRowsTest() {
		byte[] input = new byte[]{
				(byte)0xD4, (byte)0x27, (byte)0x11, (byte)0xAE, 
				(byte)0xE0, (byte)0xBF, (byte)0x98, (byte)0xF1, 
				(byte)0xB8, (byte)0xB4, (byte)0x5D, (byte)0xE5,
				(byte)0x1E, (byte)0x41, (byte)0x52, (byte)0x30
		};

		byte[] output = new byte[]{
				(byte)0xD4, (byte)0xBF, (byte)0x5D, (byte)0x30, 
				(byte)0xE0, (byte)0xB4, (byte)0x52, (byte)0xAE, 
				(byte)0xB8, (byte)0x41, (byte)0x11, (byte)0xF1,
				(byte)0x1E, (byte)0x27, (byte)0x98, (byte)0xE5
		};
		
		aes.ShiftRows(input, false);
		for (int i=0; i<16; i++)
			assertEquals(input[i], output[i]);
	}

	@Test
	public void MixColumnsTest() {
		byte[] input = new byte[]{
				(byte)0xD4, (byte)0xBF, (byte)0x5D, (byte)0x30, 
				(byte)0xE0, (byte)0xB4, (byte)0x52, (byte)0xAE, 
				(byte)0xB8, (byte)0x41, (byte)0x11, (byte)0xF1,
				(byte)0x1E, (byte)0x27, (byte)0x98, (byte)0xE5
		};

		byte[] output = new byte[]{
				(byte)0x04, (byte)0x66, (byte)0x81, (byte)0xE5, 
				(byte)0xE0, (byte)0xCB, (byte)0x19, (byte)0x9A, 
				(byte)0x48, (byte)0xF8, (byte)0xD3, (byte)0x7A,
				(byte)0x28, (byte)0x06, (byte)0x26, (byte)0x4C
		};
		
		aes.MixColumns(input, false);
		for (int i=0; i<16; i++)
			assertEquals(input[i], output[i]);
	}

	@Test
	public void AddRoundKeyTest() {
		byte[] input = new byte[]{
				(byte)0x32, (byte)0x43, (byte)0xF6, (byte)0xA8, 
				(byte)0x88, (byte)0x5A, (byte)0x30, (byte)0x8D, 
				(byte)0x31, (byte)0x31, (byte)0x98, (byte)0xA2,
				(byte)0xE0, (byte)0x37, (byte)0x07, (byte)0x34
		};
		
		byte[] output = new byte[]{
				(byte)0x19, (byte)0x3D, (byte)0xE3, (byte)0xBE, 
				(byte)0xA0, (byte)0xF4, (byte)0xC6, (byte)0xF8, 
				(byte)0x9A, (byte)0xC6, (byte)0x8D, (byte)0x2A,
				(byte)0xE9, (byte)0xF8, (byte)0x48, (byte)0x08
		};
		
		aes.KeyExpansion(new byte[]{
				(byte)0x2B, (byte)0x7E, (byte)0x15, (byte)0x16, 
				(byte)0x28, (byte)0xAE, (byte)0xD2, (byte)0xA6, 
				(byte)0xAB, (byte)0xF7, (byte)0x15, (byte)0x88,
				(byte)0x09, (byte)0xCF, (byte)0x4F, (byte)0x3C
		});
		
		aes.AddRoundKey(input, 0);
		for (int i=0; i<16; i++)
			assertEquals(input[i], output[i]);
	}

	@Test
	public void StandardTestVector() {
		fail("Not yet implemented");
	}
}