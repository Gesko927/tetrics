package com.jovann.tetrics.model;

public class Point implements Comparable<Point> {
	private Integer x;
	private Integer y;
	
	public Point(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public int compareTo(Point point2) {
		if (this.x == point2.getX() && this.y == point2.getY())
			return 0;
		else
			return 1;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object==this)
			return true;
		if (!(object instanceof Point))
			return false;
		
		Point otherPoint = (Point) object;
		
		return (this.x == otherPoint.getX() && this.y == otherPoint.getY())?true:false;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	
}
