package netImproved;

import java.io.IOException;

public abstract class test {
	static void updateProgress(double progressPercentage) {
		final int width = 50; // progress bar width in chars

		System.out.print("\r[");
		int i = 0;
		int temp=0;
		for (; i <= (int)(progressPercentage); i++) {
			System.out.print("@");
			temp=i;
		}
		for (; i < width; i++) {
			System.out.print(" ");
		}
		System.out.print("] "+ temp +"%");
	}

	public static void main(String[] args) {
		try {
			for (double progressPercentage = 0.0; progressPercentage < 100.0; progressPercentage += 1.0) {
				updateProgress(progressPercentage);
				Thread.sleep(20);
			}
		} catch (InterruptedException e) {}
	}
}


