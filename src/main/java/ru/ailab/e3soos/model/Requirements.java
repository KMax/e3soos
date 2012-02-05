package ru.ailab.e3soos.model;

public class Requirements {
	
	public static class ImageQuality {
		public static final int GEOMETRICALLY_LIMITED = 0;
		public static final int INTERSTITIAL = 1;
		public static final int DIFFRACTION = 2;
	}
	
	public static class EntrancePupilPosition {
		public static final int INSIDE = 0;
		public static final int FORWARD = 1;
		public static final int BACKWARD = 2;
	} 
	
	private float aperture; //1:<aperture>
	private float arc; //в градусах
	private float focalDistance; //в мм
	private float chromaticInterval; //в нм
	private int imageQuality;
	private float backFocalSegment; //в мм
	private int pupilPosition;
	
	/**
	 * Светосила.
	 * 
	 * @return
	 */
	public float getAperture() {
		return aperture;
	}
	
	/**
	 * Светосила.
	 * 
	 * @param aperture
	 */
	public void setAperture(float aperture) {
		this.aperture = aperture;
	}
	
	/**
	 * Угол поля зрения.
	 * 
	 * @return
	 */
	public float getArc() {
		return arc;
	}
	
	/**
	 * Угол поля зрения.
	 * 
	 * @param arc
	 */
	public void setArc(float arc) {
		this.arc = arc;
	}
	
	/**
	 * Фокусное расстояние.
	 * 
	 * @return
	 */
	public float getFocalDistance() {
		return focalDistance;
	}
	
	/**
	 * Фокусное расстояние.
	 * 
	 * @param focalDistance
	 */
	public void setFocalDistance(float focalDistance) {
		this.focalDistance = focalDistance;
	}
	
	/**
	 * Видимый хроматический диапазон.
	 * 
	 * @return
	 */
	public float getChromaticInterval() {
		return chromaticInterval;
	}
	
	/**
	 * Фокусное расстояние.
	 * 
	 * @param chromaticInterval
	 */
	public void setChromaticInterval(float chromaticInterval) {
		this.chromaticInterval = chromaticInterval;
	}
	
	/**
	 * Качество изображения.
	 * 
	 * @return
	 */
	public int getImageQuality() {
		return imageQuality;
	}
	
	/**
	 * Качество изображения.
	 * 
	 * @param imageQuality
	 */
	public void setImageQuality(int imageQuality) {
		this.imageQuality = imageQuality;
	}
	
	/**
	 * Задний фокальный отрезок.
	 * 
	 * @return
	 */
	public float getBackFocalSegment() {
		return backFocalSegment;
	}
	
	/**
	 * Задний фокальный отрезок.
	 * 
	 * @param backFocalSegment
	 */
	public void setBackFocalSegment(float backFocalSegment) {
		this.backFocalSegment = backFocalSegment;
	}
	
	/**
	 * Положение входного зрачка.
	 * 
	 * @return
	 */
	public int getPupilPosition() {
		return pupilPosition;
	}
	
	/**
	 * Положение входного зрачка.
	 * 
	 * @param pupilPosition
	 */
	public void setPupilPosition(int pupilPosition) {
		this.pupilPosition = pupilPosition;
	}
}
