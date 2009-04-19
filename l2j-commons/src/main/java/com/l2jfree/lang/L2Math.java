/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.lang;

/**
 * @author NB4L1
 */
public final class L2Math
{
	private L2Math()
	{
	}
	
	public static int min(int n1, int n2, int n3)
	{
		return Math.min(n1, Math.min(n2, n3));
	}
	
	public static long min(long n1, long n2, long n3)
	{
		return Math.min(n1, Math.min(n2, n3));
	}
	
	public static float min(float n1, float n2, float n3)
	{
		return Math.min(n1, Math.min(n2, n3));
	}
	
	public static double min(double n1, double n2, double n3)
	{
		return Math.min(n1, Math.min(n2, n3));
	}
	
	public static int max(int n1, int n2, int n3)
	{
		return Math.max(n1, Math.max(n2, n3));
	}
	
	public static long max(long n1, long n2, long n3)
	{
		return Math.max(n1, Math.max(n2, n3));
	}
	
	public static float max(float n1, float n2, float n3)
	{
		return Math.max(n1, Math.max(n2, n3));
	}
	
	public static double max(double n1, double n2, double n3)
	{
		return Math.max(n1, Math.max(n2, n3));
	}
	
	public static int limit(int min, int value, int max)
	{
		return Math.max(min, Math.min(value, max));
	}
	
	public static long limit(long min, long value, long max)
	{
		return Math.max(min, Math.min(value, max));
	}
	
	public static float limit(float min, float value, float max)
	{
		return Math.max(min, Math.min(value, max));
	}
	
	public static double limit(double min, double value, double max)
	{
		return Math.max(min, Math.min(value, max));
	}
	
	public static double calculateDistance(int x1, int y1, int x2, int y2)
	{
		final int diffX = x1 - x2;
		final int diffY = y1 - y2;
		
		return Math.sqrt(diffX * diffX + diffY * diffY);
	}
	
	public static double calculateDistance(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		final int diffX = x1 - x2;
		final int diffY = y1 - y2;
		final int diffZ = z1 - z2;
		
		return Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
	}
	
	public static boolean isDistanceLessThan(int x1, int y1, int x2, int y2, int limit)
	{
		final int diffX = x1 - x2;
		final int diffY = y1 - y2;
		
		return diffX * diffX + diffY * diffY < limit * limit;
	}
	
	public static boolean isDistanceLessThan(int x1, int y1, int z1, int x2, int y2, int z2, int limit)
	{
		final int diffX = x1 - x2;
		final int diffY = y1 - y2;
		final int diffZ = z1 - z2;
		
		return diffX * diffX + diffY * diffY + diffZ * diffZ < limit * limit;
	}
	
	/**
	 * @param base the base
	 * @param exponent the <b>NON-NEGATIVE INTEGER</b> exponent
	 * @return <code>base<sup>exponent</sup></code>
	 * @throws IllegalArgumentException if the exponent is negative
	 */
	public static int pow(final int base, int exponent)
	{
		if (exponent < 0)
			throw new IllegalArgumentException("Exponent must be non-negative!");
		
		int result = 1;
		
		while (exponent-- > 0)
			result *= base;
		
		return result;
	}
	
	/**
	 * @param base the base
	 * @param exponent the <b>NON-NEGATIVE INTEGER</b> exponent
	 * @return <code>base<sup>exponent</sup></code>
	 * @throws IllegalArgumentException if the exponent is negative
	 */
	public static double pow(final double base, int exponent)
	{
		if (exponent < 0)
			throw new IllegalArgumentException("Exponent must be non-negative!");
		
		double result = 1.0;
		
		while (exponent-- > 0)
			result *= base;
		
		return result;
	}
}
