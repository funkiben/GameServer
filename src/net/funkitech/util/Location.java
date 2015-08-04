package net.funkitech.util;


public class Location implements java.io.Serializable {

	private static final long serialVersionUID = 4628820845628799113L;
	
	private double x;
	private double y;
	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void set(Location location) {
		x = location.x;
		y = location.y;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Location add(double x, double y) {
		return new Location(this.x + x, this.y + y);
	}
	
	public Location add(Location loc) {
		return add(loc.getX(), loc.getY());
	}
	
	public Location subtract(double x, double y) {
		return add(-x, -y);
	}
	
	public Location subtract(Location loc) {
		return subtract(loc.getX(), loc.getY());
	}
	
	public Location multiply(double m) {
		return new Location(x * m, y * m);
	}
	
	public Location divide(double d) {
		return multiply(1 / d);
	}
	
	public boolean isZero() {
		return x == 0 && y == 0;
	}
	
	public Location rotate(double deg) {
		deg = Math.toRadians(deg);
		double s = Math.sin(deg);
		double c = Math.cos(deg);
		return new Location(x * c - y * s, x * s + y * c);
	}
	
	public double angleBetween(Location location) {
		return Math.atan2(y - location.getY(), x - location.getX()) * 180.0 / Math.PI;
	}
	
	public double distanceSqrt(Location location) {
		return Math.pow(x - location.getX(), 2) + Math.pow(y - location.getY(), 2);
	}
	
	public double distance(Location location) {
		return Math.sqrt(distanceSqrt(location));
	}
	
	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double lengthSqrt() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	public Location normalize() {
		double length = length();

        x /= length;
        y /= length;
        
        return new Location(x, y);
	}
	
	@Override
	public String toString() {
		return x + "," + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location)) {
			return false;
		}
		
		Location location = (Location) obj;
		
		return location.getX() == x && location.getY() == y;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
	    
	    hash = 79 * hash + (int) (Double.doubleToLongBits(x) ^ Double.doubleToLongBits(x) >>> 32);
	    hash = 79 * hash + (int) (Double.doubleToLongBits(y) ^ Double.doubleToLongBits(y) >>> 32);
	    
	    return hash;
	}
	
	public Location clone() {
		return new Location(x, y);
	}
	
	public static Location parse(String str) {
		String[] nums = str.split(",");
		
		double x = Double.parseDouble(nums[0]);
		double y = Double.parseDouble(nums[1]);
		
		return new Location(x, y);
		
	}


}
