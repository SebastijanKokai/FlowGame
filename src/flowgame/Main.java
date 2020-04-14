package flowgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static class FlowPoint implements Comparable<FlowPoint> {

		int x;
		int y;
		int color;
		int position;

		@Override
		public int compareTo(FlowPoint p) {
			return this.color - p.color;
		}

	}

	public static class PPoint {

		int x;
		int y;

	}

	public static void main(String[] args) throws FileNotFoundException {

		ArrayList<FlowPoint> list = new ArrayList<FlowPoint>();

		File file = new File("/home/sebastijan/workspaces/eclipse-workspace/FlowGame/level3/level3-7.in");
		Scanner reader = new Scanner(file);

		String line = reader.nextLine().toString();
		reader.close();
		String[] splitter = line.split(" ");

		int rows = Integer.parseInt(splitter[0]);
		int cols = Integer.parseInt(splitter[1]);
		int numberOfPositions = Integer.parseInt(splitter[2]);

		int[][] matrix = new int[rows][cols];

		int increment = 1;

		int z = 3;

		for (int i = 0; i < numberOfPositions; i++) {

			FlowPoint p = new FlowPoint();
			p.position = Integer.parseInt(splitter[z++]);
			p.color = Integer.parseInt(splitter[z++]);

			if (p.position % cols == 0) {
				p.x = p.position / cols;
				p.y = cols;
			} else {
				p.x = p.position / cols + 1;
				p.y = p.position % cols;
			}

			list.add(p);
		}

		int numberOfPaths = Integer.parseInt(splitter[z++]);

		for (int i = 0; i < numberOfPaths; i++) {

			int color = Integer.parseInt(splitter[z++]);
			int startingPosition = Integer.parseInt(splitter[z++]);
			int length = Integer.parseInt(splitter[z++]);

			ArrayList<PPoint> listDifferentColors = new ArrayList<PPoint>();
			ArrayList<PPoint> listPath = new ArrayList<PPoint>();

			for (int k = 0; k < list.size(); k++) {
				if (list.get(k).color != color) {
					PPoint p = new PPoint();
					p.x = list.get(k).x;
					p.y = list.get(k).y;
					listDifferentColors.add(p);
				}
			}

			int x;
			int y;

			if (startingPosition % cols == 0) {
				x = startingPosition / cols;
				y = cols;
			} else {
				x = startingPosition / cols + 1;
				y = startingPosition % cols;
			}

			int valid = 1;
			int index = -1;

			for (int j = 0; j < length; j++) {

				String s = splitter[z++];

				switch (s) {

				case "N":
					if (x - 1 > 0 && CheckSameColor(x - 1, y, listDifferentColors) && CheckSamePath(x - 1, y, listPath)) {
						PPoint p = new PPoint();
						p.x = x;
						p.y = y;
						listPath.add(p);
						x -= 1;
					} else {
						index = j + 1;
						valid = -1;
						j = length;
					}
					break;
				case "S":
					if (x + 1 <= rows && CheckSameColor(x + 1, y, listDifferentColors) && CheckSamePath(x + 1, y, listPath)) {
						PPoint p = new PPoint();
						p.x = x;
						p.y = y;
						listPath.add(p);
						x += 1;

					} else {
						index = j + 1;
						valid = -1;
						j = length;
					}
					break;
				case "E":
					if (y + 1 <= cols && CheckSameColor(x, y + 1, listDifferentColors) && CheckSamePath(x, y + 1, listPath)) {
						PPoint p = new PPoint();
						p.x = x;
						p.y = y;
						listPath.add(p);
						y += 1;
					} else {
						index = j + 1;
						valid = -1;
						j = length;
					}
					break;
				case "W":
					if (y - 1 > 0 && CheckSameColor(x, y - 1, listDifferentColors) && CheckSamePath(x, y - 1, listPath)) {
						PPoint p = new PPoint();
						p.x = x;
						p.y = y;
						listPath.add(p);
						y -= 1;
					} else {
						index = j + 1;
						valid = -1;
						j = length;
					}
					break;
				default:
					break;

				}
			}

			boolean check = false;

			if (index != -1) {
				System.out.println(valid + " " + index);
			} else {
				for (int m = 0; m < list.size(); m++) {

					if (x == list.get(m).x && y == list.get(m).y && color == list.get(m).color) {
						check = true;
					}
				}
				if(!check) {
					valid = -1;
				}
				System.out.println(valid + " " + length);
			}

		}

		/*
		 * Collections.sort(list);
		 * 
		 * String output = "";
		 * 
		 * for (int i = 0; i < list.size() - 1; i += 2) {
		 * 
		 * int md = Math.abs(list.get(i).x - list.get(i + 1).x) + Math.abs(list.get(i).y
		 * - list.get(i + 1).y); output += md + " "; }
		 * 
		 * 
		 * for(int i = 3; i < splitter.length; i++) {
		 * 
		 * int number = Integer.parseInt(splitter[i]);
		 * 
		 * for(int k = 0; k < rows; k++) {
		 * 
		 * for(int j = 0; j < cols; j++) { if(matrix[k][j] == number) { output += (k+1)
		 * + " " + (j+1) + " "; } } }
		 * 
		 * }
		 * 
		 * 
		 * System.out.println(output);
		 */

	}

	public static boolean CheckSameColor(int x, int y, ArrayList<PPoint> listDifferentColors) {

		for (int m = 0; m < listDifferentColors.size(); m++) {
			if (listDifferentColors.get(m).x == x && listDifferentColors.get(m).y == y) {
				return false;
			}
		}

		return true;
	}

	public static boolean CheckSamePath(int x, int y, ArrayList<PPoint> listPath) {

		if (listPath == null) {
			return true;
		}

		for (int m = 0; m < listPath.size(); m++) {
			if (listPath.get(m).x == x && listPath.get(m).y == y) {
				return false;
			}
		}

		return true;
	}

}
