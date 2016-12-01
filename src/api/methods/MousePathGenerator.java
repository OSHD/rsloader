








package api.methods;

import java.awt.*;


public class MousePathGenerator {
	private static final java.util.Random staticRandom = new java.util.Random();
	public static int msPerBit = 80;
	private static void adaptiveMidpoints(final java.util.Vector<Point> points) {
		int i = 0;
		while (i < points.size() - 1) {
			final Point a = points.get(i++);
			final Point b = points.get(i);
			if (Math.abs(a.x - b.x) > 1 || Math.abs(a.y - b.y) > 1) {
				if (Math.abs(a.x - b.x) != 0) {
					final double slope = (double) (a.y - b.y) / (double) (a.x - b.x);
					final double incpt = a.y - slope * a.x;
					for (int c = a.x < b.x ? a.x + 1 : b.x - 1; a.x < b.x ? c < b.x : c > a.x;
					     c += a.x < b.x ? 1 : -1) {
						points.add(i++, new Point(c, (int) Math.round(incpt + slope * c)));
					}
				} else {
					for (int c = a.y < b.y ? a.y + 1 : b.y - 1; a.y < b.y ? c < b.y : c > a.y;
					     c += a.y < b.y ? 1 : -1) {
						points.add(i++, new Point(a.x, c));
					}
				}
			}
		}
	}
	public static Point[] applyDynamism(final Point[] spline, final int msForMove, final int msPerMove) {
		final int numPoints = spline.length;
		final double msPerPoint = (double) msForMove / (double) numPoints;
		final double undistStep = msPerMove / msPerPoint;
		final int steps = (int) Math.floor(numPoints / undistStep);
		Point[] result = new Point[steps];
		final double[] gaussValues = gaussianTable(result.length);
		double currentPercent = 0;
		for (int i = 0; i < steps; i++) {
			currentPercent += gaussValues[i];
			final int nextIndex = (int) Math.floor(numPoints * currentPercent);
			if (nextIndex < numPoints) {
				result[i] = spline[nextIndex];
			} else {
				result[i] = spline[numPoints - 1];
			}
		}
		if (currentPercent < 1D) {
			result[steps - 1] = spline[numPoints - 1];
		}
		return result;
	}
	public static long fittsLaw(final double targetDist, final double targetSize) {
		return (long) (msPerBit * Math.log10(targetDist / targetSize + 1) / Math.log10(2));
	}
	private static double factorial(final int n) {
		double result = 1;
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
	private static double gaussian(double t) {
		t = 10D * t - 5D;
		return 1D / (Math.sqrt(5D) * Math.sqrt(2D * Math.PI)) * Math.exp(-t * t / 20D);
	}
	private static double[] gaussianTable(final int steps) {
		final double[] table = new double[steps];
		final double step = 1D / steps;
		double sum = 0;
		for (int i = 0; i < steps; i++) {
			sum += gaussian(i * step);
		}
		for (int i = 0; i < steps; i++) {
			table[i] = gaussian(i * step) / sum;
		}
		return table;
	}
	public static Point[] generateControls(final int sx, final int sy, final int ex, final int ey, int ctrlSpacing, int ctrlVariance) {
		final double dist = Math.sqrt((sx - ex) * (sx - ex) + (sy - ey) * (sy - ey));
		final double angle = Math.atan2(ey - sy, ex - sx);
		int ctrlPoints = (int) Math.floor(dist / ctrlSpacing);
		ctrlPoints = ctrlPoints * ctrlSpacing == dist ? ctrlPoints - 1 : ctrlPoints;
		if (ctrlPoints <= 1) {
			ctrlPoints = 2;
			ctrlSpacing = (int) dist / 3;
			ctrlVariance = (int) dist / 2;
		}
		final Point[] result = new Point[ctrlPoints + 2];
		result[0] = new Point(sx, sy);
		for (int i = 1; i < ctrlPoints + 1; i++) {
			final double radius = ctrlSpacing * i;
			final Point cur = new Point((int) (sx + radius * Math.cos(angle)), (int) (sy + radius * Math.sin(angle)));
			double percent = 1D - (double) (i - 1) / (double) ctrlPoints;
			percent = percent > 0.5 ? percent - 0.5 : percent;
			percent += 0.25;
			final int curVariance = (int) (ctrlVariance * percent);
			cur.x = (int) (cur.x + curVariance * 2 * staticRandom.nextDouble() - curVariance);
			cur.y = (int) (cur.y + curVariance * 2 * staticRandom.nextDouble() - curVariance);
			result[i] = cur;
		}
		result[ctrlPoints + 1] = new Point(ex, ey);
		return result;
	}
	public static Point[] generateSpline(final Point[] controls) {
		final double degree = controls.length - 1;
		final java.util.Vector<Point> spline = new java.util.Vector<Point>();
		boolean lastFlag = false;
		for (double theta = 0; theta <= 1; theta += 0.01) {
			double x = 0;
			double y = 0;
			for (double index = 0; index <= degree; index++) {
				final double probPoly = nCk((int) degree, (int) index) * Math.pow(theta, index) * Math.pow(1D - theta, degree - index);
				x += probPoly * controls[(int) index].x;
				y += probPoly * controls[(int) index].y;
			}
			final Point temp = new Point((int) x, (int) y);
			try {
				if (!temp.equals(spline.lastElement())) {
					spline.add(temp);
				}
			} catch (final Exception e) {
				spline.add(temp);
			}
			lastFlag = theta != 1.0;
		}
		if (lastFlag) {
			spline.add(new Point(controls[(int) degree].x, controls[(int) degree].y));
		}
		adaptiveMidpoints(spline);
		return spline.toArray(new Point[spline.size()]);
	}
	public static Point[] generatePath(final int x2, final int y2) {
		Point curr = Mouse.getLocation();
		int x1=curr.x;
		int y1=curr.y;
		if (x2 == -1 && y2 == -1) {
			return new Point[]{};
		}
		try {
			if (x2 == x1 && y2 == y1) {
				return new Point[]{};
			}
			final Point[] controls = generateControls(x1, y1, x2, y2, 50, 120);
			final Point[] spline = generateSpline(controls);
			final long timeToMove = fittsLaw(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)), 10);
			final Point[] path = applyDynamism(spline, (int) timeToMove, Mouse.getSpeed());
			return path;
		} catch (final Exception ignored) {
		}
		return new Point[]{};
	}
	private static double nCk(final int n, final int k) {
		return factorial(n) / (factorial(k) * factorial(n - k));
	}
}
