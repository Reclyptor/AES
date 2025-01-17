import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;

// Emilio Torres Jr.
// AES Encryption Algorithm

public class AES {
	// CONSTANTS //
	private static final byte[] Sbox = new byte[]{
			(byte)0x63, (byte)0x7C, (byte)0x77, (byte)0x7B, (byte)0xF2, (byte)0x6B, (byte)0x6F, (byte)0xC5, (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2B, (byte)0xFE, (byte)0xD7, (byte)0xAB, (byte)0x76,
			(byte)0xCA, (byte)0x82, (byte)0xC9, (byte)0x7D, (byte)0xFA, (byte)0x59, (byte)0x47, (byte)0xF0, (byte)0xAD, (byte)0xD4, (byte)0xA2, (byte)0xAF, (byte)0x9C, (byte)0xA4, (byte)0x72, (byte)0xC0,
			(byte)0xB7, (byte)0xFD, (byte)0x93, (byte)0x26, (byte)0x36, (byte)0x3F, (byte)0xF7, (byte)0xCC, (byte)0x34, (byte)0xA5, (byte)0xE5, (byte)0xF1, (byte)0x71, (byte)0xD8, (byte)0x31, (byte)0x15,
			(byte)0x04, (byte)0xC7, (byte)0x23, (byte)0xC3, (byte)0x18, (byte)0x96, (byte)0x05, (byte)0x9A, (byte)0x07, (byte)0x12, (byte)0x80, (byte)0xE2, (byte)0xEB, (byte)0x27, (byte)0xB2, (byte)0x75,
			(byte)0x09, (byte)0x83, (byte)0x2C, (byte)0x1A, (byte)0x1B, (byte)0x6E, (byte)0x5A, (byte)0xA0, (byte)0x52, (byte)0x3B, (byte)0xD6, (byte)0xB3, (byte)0x29, (byte)0xE3, (byte)0x2F, (byte)0x84,
			(byte)0x53, (byte)0xD1, (byte)0x00, (byte)0xED, (byte)0x20, (byte)0xFC, (byte)0xB1, (byte)0x5B, (byte)0x6A, (byte)0xCB, (byte)0xBE, (byte)0x39, (byte)0x4A, (byte)0x4C, (byte)0x58, (byte)0xCF,
			(byte)0xD0, (byte)0xEF, (byte)0xAA, (byte)0xFB, (byte)0x43, (byte)0x4D, (byte)0x33, (byte)0x85, (byte)0x45, (byte)0xF9, (byte)0x02, (byte)0x7F, (byte)0x50, (byte)0x3C, (byte)0x9F, (byte)0xA8,
			(byte)0x51, (byte)0xA3, (byte)0x40, (byte)0x8F, (byte)0x92, (byte)0x9D, (byte)0x38, (byte)0xF5, (byte)0xBC, (byte)0xB6, (byte)0xDA, (byte)0x21, (byte)0x10, (byte)0xFF, (byte)0xF3, (byte)0xD2,
			(byte)0xCD, (byte)0x0C, (byte)0x13, (byte)0xEC, (byte)0x5F, (byte)0x97, (byte)0x44, (byte)0x17, (byte)0xC4, (byte)0xA7, (byte)0x7E, (byte)0x3D, (byte)0x64, (byte)0x5D, (byte)0x19, (byte)0x73,
			(byte)0x60, (byte)0x81, (byte)0x4F, (byte)0xDC, (byte)0x22, (byte)0x2A, (byte)0x90, (byte)0x88, (byte)0x46, (byte)0xEE, (byte)0xB8, (byte)0x14, (byte)0xDE, (byte)0x5E, (byte)0x0B, (byte)0xDB,
			(byte)0xE0, (byte)0x32, (byte)0x3A, (byte)0x0A, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5C, (byte)0xC2, (byte)0xD3, (byte)0xAC, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xE4, (byte)0x79,
			(byte)0xE7, (byte)0xC8, (byte)0x37, (byte)0x6D, (byte)0x8D, (byte)0xD5, (byte)0x4E, (byte)0xA9, (byte)0x6C, (byte)0x56, (byte)0xF4, (byte)0xEA, (byte)0x65, (byte)0x7A, (byte)0xAE, (byte)0x08,
			(byte)0xBA, (byte)0x78, (byte)0x25, (byte)0x2E, (byte)0x1C, (byte)0xA6, (byte)0xB4, (byte)0xC6, (byte)0xE8, (byte)0xDD, (byte)0x74, (byte)0x1F, (byte)0x4B, (byte)0xBD, (byte)0x8B, (byte)0x8A,
			(byte)0x70, (byte)0x3E, (byte)0xB5, (byte)0x66, (byte)0x48, (byte)0x03, (byte)0xF6, (byte)0x0E, (byte)0x61, (byte)0x35, (byte)0x57, (byte)0xB9, (byte)0x86, (byte)0xC1, (byte)0x1D, (byte)0x9E,
			(byte)0xE1, (byte)0xF8, (byte)0x98, (byte)0x11, (byte)0x69, (byte)0xD9, (byte)0x8E, (byte)0x94, (byte)0x9B, (byte)0x1E, (byte)0x87, (byte)0xE9, (byte)0xCE, (byte)0x55, (byte)0x28, (byte)0xDF,
			(byte)0x8C, (byte)0xA1, (byte)0x89, (byte)0x0D, (byte)0xBF, (byte)0xE6, (byte)0x42, (byte)0x68, (byte)0x41, (byte)0x99, (byte)0x2D, (byte)0x0F, (byte)0xB0, (byte)0x54, (byte)0xBB, (byte)0x16};
	private static final byte[] Ibox = new byte[]{
			(byte)0x52, (byte)0x09, (byte)0x6A, (byte)0xD5, (byte)0x30, (byte)0x36, (byte)0xA5, (byte)0x38, (byte)0xBF, (byte)0x40, (byte)0xA3, (byte)0x9E, (byte)0x81, (byte)0xF3, (byte)0xD7, (byte)0xFB,
			(byte)0x7C, (byte)0xE3, (byte)0x39, (byte)0x82, (byte)0x9B, (byte)0x2F, (byte)0xFF, (byte)0x87, (byte)0x34, (byte)0x8E, (byte)0x43, (byte)0x44, (byte)0xC4, (byte)0xDE, (byte)0xE9, (byte)0xCB,
			(byte)0x54, (byte)0x7B, (byte)0x94, (byte)0x32, (byte)0xA6, (byte)0xC2, (byte)0x23, (byte)0x3D, (byte)0xEE, (byte)0x4C, (byte)0x95, (byte)0x0B, (byte)0x42, (byte)0xFA, (byte)0xC3, (byte)0x4E,
			(byte)0x08, (byte)0x2E, (byte)0xA1, (byte)0x66, (byte)0x28, (byte)0xD9, (byte)0x24, (byte)0xB2, (byte)0x76, (byte)0x5B, (byte)0xA2, (byte)0x49, (byte)0x6D, (byte)0x8B, (byte)0xD1, (byte)0x25,
			(byte)0x72, (byte)0xF8, (byte)0xF6, (byte)0x64, (byte)0x86, (byte)0x68, (byte)0x98, (byte)0x16, (byte)0xD4, (byte)0xA4, (byte)0x5C, (byte)0xCC, (byte)0x5D, (byte)0x65, (byte)0xB6, (byte)0x92,
			(byte)0x6C, (byte)0x70, (byte)0x48, (byte)0x50, (byte)0xFD, (byte)0xED, (byte)0xB9, (byte)0xDA, (byte)0x5E, (byte)0x15, (byte)0x46, (byte)0x57, (byte)0xA7, (byte)0x8D, (byte)0x9D, (byte)0x84,
			(byte)0x90, (byte)0xD8, (byte)0xAB, (byte)0x00, (byte)0x8C, (byte)0xBC, (byte)0xD3, (byte)0x0A, (byte)0xF7, (byte)0xE4, (byte)0x58, (byte)0x05, (byte)0xB8, (byte)0xB3, (byte)0x45, (byte)0x06,
			(byte)0xD0, (byte)0x2C, (byte)0x1E, (byte)0x8F, (byte)0xCA, (byte)0x3F, (byte)0x0F, (byte)0x02, (byte)0xC1, (byte)0xAF, (byte)0xBD, (byte)0x03, (byte)0x01, (byte)0x13, (byte)0x8A, (byte)0x6B,
			(byte)0x3A, (byte)0x91, (byte)0x11, (byte)0x41, (byte)0x4F, (byte)0x67, (byte)0xDC, (byte)0xEA, (byte)0x97, (byte)0xF2, (byte)0xCF, (byte)0xCE, (byte)0xF0, (byte)0xB4, (byte)0xE6, (byte)0x73,
			(byte)0x96, (byte)0xAC, (byte)0x74, (byte)0x22, (byte)0xE7, (byte)0xAD, (byte)0x35, (byte)0x85, (byte)0xE2, (byte)0xF9, (byte)0x37, (byte)0xE8, (byte)0x1C, (byte)0x75, (byte)0xDF, (byte)0x6E,
			(byte)0x47, (byte)0xF1, (byte)0x1A, (byte)0x71, (byte)0x1D, (byte)0x29, (byte)0xC5, (byte)0x89, (byte)0x6F, (byte)0xB7, (byte)0x62, (byte)0x0E, (byte)0xAA, (byte)0x18, (byte)0xBE, (byte)0x1B,
			(byte)0xFC, (byte)0x56, (byte)0x3E, (byte)0x4B, (byte)0xC6, (byte)0xD2, (byte)0x79, (byte)0x20, (byte)0x9A, (byte)0xDB, (byte)0xC0, (byte)0xFE, (byte)0x78, (byte)0xCD, (byte)0x5A, (byte)0xF4,
			(byte)0x1F, (byte)0xDD, (byte)0xA8, (byte)0x33, (byte)0x88, (byte)0x07, (byte)0xC7, (byte)0x31, (byte)0xB1, (byte)0x12, (byte)0x10, (byte)0x59, (byte)0x27, (byte)0x80, (byte)0xEC, (byte)0x5F,
			(byte)0x60, (byte)0x51, (byte)0x7F, (byte)0xA9, (byte)0x19, (byte)0xB5, (byte)0x4A, (byte)0x0D, (byte)0x2D, (byte)0xE5, (byte)0x7A, (byte)0x9F, (byte)0x93, (byte)0xC9, (byte)0x9C, (byte)0xEF,
			(byte)0xA0, (byte)0xE0, (byte)0x3B, (byte)0x4D, (byte)0xAE, (byte)0x2A, (byte)0xF5, (byte)0xB0, (byte)0xC8, (byte)0xEB, (byte)0xBB, (byte)0x3C, (byte)0x83, (byte)0x53, (byte)0x99, (byte)0x61,
			(byte)0x17, (byte)0x2B, (byte)0x04, (byte)0x7E, (byte)0xBA, (byte)0x77, (byte)0xD6, (byte)0x26, (byte)0xE1, (byte)0x69, (byte)0x14, (byte)0x63, (byte)0x55, (byte)0x21, (byte)0x0C, (byte)0x7D};
	private static final byte[] Rcon = new byte[]{
			(byte)0x8D, (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x80, (byte)0x1B, (byte)0x36};

	// FIELDS //
	int Nb;   // Number of 32-bit words (columns) comprising the State.
	int Nk;   // Number of 32-bit words (columns) comprising the Cipher Key.
	int Nr;   // Number of rounds 
	byte[] K; // The Cipher Key
	byte[] W; // The Expanded Key
	BufferedInputStream bis;
	BufferedOutputStream bos;

	// CONSTRUCTORS //
	public AES(int strength, BufferedInputStream bis, BufferedOutputStream bos) {
		switch (strength) {
		case 128:
			this.Nk = 4;
			this.Nr = 10;
			break;
		case 192:
			this.Nk = 6;
			this.Nr = 12;
			break;
		case 256:
			this.Nk = 8;
			this.Nr = 14;
			break;
		default:
			this.Nk = 4;
			this.Nr = 10;
			break;
		}

		this.Nb = 4;
		this.K = new byte[4*Nk];
		this.W = new byte[4*Nb*(Nr+1)];
		this.bis = bis;
		this.bos = bos;
	}

	// PRIVATE METHODS //
	public void KeyExpansion(byte[] cipherkey) {
		System.arraycopy(cipherkey, 0, W, 0, 4*Nk);
		byte[] temp = new byte[4];

		int i = Nk;
		while (i < Nb*(Nr+1)) {
			System.arraycopy(W, 4*(i-1), temp, 0, temp.length);
			if (i % Nk == 0)
				temp = XORWord(SubWord(RotWord(temp)), new byte[]{Rcon[i/Nk],(byte)0x00,(byte)0x00,(byte)0x00});
			else if (Nk > 6 && i % Nk == 4)
				temp = SubWord(temp);
			W[4*i    ] = (byte) (W[4*(i-Nk)    ] ^ temp[0]);
			W[4*i + 1] = (byte) (W[4*(i-Nk) + 1] ^ temp[1]);
			W[4*i + 2] = (byte) (W[4*(i-Nk) + 2] ^ temp[2]);
			W[4*i + 3] = (byte) (W[4*(i-Nk) + 3] ^ temp[3]);
			i++;
		}
	}

	public void AddRoundKey(byte[] state, int round) {
		for (int i=0; i<Nb; i++) {
			state[4*i    ] ^= W[round*4*Nb + (4*i    )];
			state[4*i + 1] ^= W[round*4*Nb + (4*i + 1)];
			state[4*i + 2] ^= W[round*4*Nb + (4*i + 2)];
			state[4*i + 3] ^= W[round*4*Nb + (4*i + 3)];
		}
	}

	public void SubBytes(byte[] state, boolean inverse) {
		for (int i=0; i<4*Nb; i++)
			state[i] = inverse ? Ibox[state[i] & 0xFF] : Sbox[state[i] & 0xFF];
	}

	public void ShiftRows(byte[] state, boolean inverse) {
		byte[] clone = state.clone();
		for (int i=1; i<4*Nb; i++)
			state[i] = inverse ? clone[(13*i) % (4*Nb)] : clone[(5*i) % (4*Nb)];
	}

	public void MixColumns(byte[] state, boolean inverse) {
		byte[] temp = new byte[4*Nb];
		for (int col=0; col<Nb; col++)
			if (inverse) {
				temp[0 + 4*col] = (byte) (FFMult((byte)0x0E, state[    4*col]) ^ FFMult((byte)0x0B, state[1 + 4*col]) ^ FFMult((byte)0x0D, state[2 + 4*col]) ^ FFMult((byte)0x09, state[3 + 4*col]));
				temp[1 + 4*col] = (byte) (FFMult((byte)0x09, state[    4*col]) ^ FFMult((byte)0x0E, state[1 + 4*col]) ^ FFMult((byte)0x0B, state[2 + 4*col]) ^ FFMult((byte)0x0D, state[3 + 4*col]));
				temp[2 + 4*col] = (byte) (FFMult((byte)0x0D, state[    4*col]) ^ FFMult((byte)0x09, state[1 + 4*col]) ^ FFMult((byte)0x0E, state[2 + 4*col]) ^ FFMult((byte)0x0B, state[3 + 4*col]));
				temp[3 + 4*col] = (byte) (FFMult((byte)0x0B, state[    4*col]) ^ FFMult((byte)0x0D, state[1 + 4*col]) ^ FFMult((byte)0x09, state[2 + 4*col]) ^ FFMult((byte)0x0E, state[3 + 4*col]));
			}
			else {
				temp[0 + 4*col] = (byte) (FFMult((byte)0x02, state[    4*col]) ^ FFMult((byte)0x03, state[1 + 4*col]) ^ state[2 + 4*col] ^ state[3 + 4*col]);
				temp[1 + 4*col] = (byte) (FFMult((byte)0x02, state[1 + 4*col]) ^ FFMult((byte)0x03, state[2 + 4*col]) ^ state[    4*col] ^ state[3 + 4*col]);
				temp[2 + 4*col] = (byte) (FFMult((byte)0x02, state[2 + 4*col]) ^ FFMult((byte)0x03, state[3 + 4*col]) ^ state[    4*col] ^ state[1 + 4*col]);
				temp[3 + 4*col] = (byte) (FFMult((byte)0x03, state[    4*col]) ^ FFMult((byte)0x02, state[3 + 4*col]) ^ state[1 + 4*col] ^ state[2 + 4*col]);
			}
		System.arraycopy(temp, 0, state, 0, 4*Nb);
	}

	private byte FFMult(byte x, byte y) {
		byte a = x;
		byte b = y;
		byte r = 0;
		byte t;
		while (a != 0) {
			if ((a & 1) != 0)
				r = (byte)(r ^ b);
			t = (byte)(b & 0x80);
			b = (byte)(b << 1);
			if (t != 0)
				b = (byte)(b ^ 0x1B);
			a = (byte)((a & 0xFF) >> 1);
		}
		return r;
	}

	private byte[] RotWord(byte[] word) {
		byte[] shifted = new byte[4];
		for (int i=0; i<4; i++)
			shifted[i] = word[(i+1)%4];
		return shifted;
	}

	private byte[] SubWord(byte[] word) {
		for (int i=0; i<4; i++)
			word[i] = Sbox[word[i] & 0xFF];
		return word;
	}

	private byte[] XORWord(byte[] w1, byte[] w2) {
		byte[] xored = new byte[4];
		for (int i=0; i<4; i++)
			xored[i] = (byte) (w1[i] ^ w2[i]);
		return xored;
	}

	// PUBLIC METHODS //
	public void Key(byte[] cipherkey) {
		KeyExpansion(cipherkey);
	}

	public void Encrypt() {
		byte[] state = new byte[4*Nb];
		int round = 0;
		int bytesRead = 0;
		int totalBytesRead = 0;

		try {
			while ((totalBytesRead += (bytesRead = bis.read(state))) != -1) {
				if (totalBytesRead == state.length)
					AddRoundKey(state, round);

				for (round = 1; round < Nr; round++) {
					SubBytes(state, false);
					ShiftRows(state, false);
					MixColumns(state, false);
					AddRoundKey(state, round);
				}

				SubBytes(state, false);
				ShiftRows(state, false);
				AddRoundKey(state, round);

				bos.write(state);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Decrypt() {

	}

	// AUXILIARY METHODS //
	public void printState(byte[] state) {
		System.out.printf("%02X %02X %02X %02X\n", state[0], state[4], state[8],  state[12]);
		System.out.printf("%02X %02X %02X %02X\n", state[1], state[5], state[9],  state[13]);
		System.out.printf("%02X %02X %02X %02X\n", state[2], state[6], state[10], state[14]);
		System.out.printf("%02X %02X %02X %02X\n", state[3], state[7], state[11], state[15]);
	}
}