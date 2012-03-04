package ru.ifmo.ailab.e3soos.facts;

import java.util.Arrays;

public class Requirements {
	
	public enum ImageQuality {
            GEOMETRICALLY_LIMITED,
            INTERSTITIAL,
            DIFFRACTION
	}
	
	public enum EntrancePupilPosition {
            INSIDE,
            FORWARD,
            BACKWARD
	}
	
	private float aperture; //1:<aperture>
	private float arc; //в градусах
	private float focalDistance; //в мм
	private float chromaticInterval; //в нм
	private ImageQuality imageQuality;
	private float backFocalSegment; //в мм
	private EntrancePupilPosition pupilPosition;
	
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
        
        public void setChromaticInterval(float chromaticInterval) {
            this.chromaticInterval = chromaticInterval;
        }
	
	/**
	 * Качество изображения.
	 * 
	 * @return
	 */
	public ImageQuality getImageQuality() {
		return imageQuality;
	}
	
	/**
	 * Качество изображения.
	 * 
	 * @param imageQuality
	 */
	public void setImageQuality(ImageQuality imageQuality) {
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
	public EntrancePupilPosition getPupilPosition() {
		return pupilPosition;
	}
	
	/**
	 * Положение входного зрачка.
	 * 
	 * @param pupilPosition
	 */
	public void setPupilPosition(EntrancePupilPosition pupilPosition) {
		this.pupilPosition = pupilPosition;
	}
}
