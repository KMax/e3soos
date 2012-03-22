package ru.ifmo.ailab.e3soos.facts;

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
    private float apertureSpeed;
    private float angularField;
    private float focalLength;
    private float spectralRange;
    private ImageQuality imageQuality;
    private float backFocalDistance;
    private EntrancePupilPosition entrancePupilPosition;
    private float[] waveLengths;

    /**
     * Светосила.
     *
     * @return
     */
    public float getApertureSpeed() {
        return apertureSpeed;
    }

    /**
     * Светосила.
     *
     * @param aperture
     */
    public void setApertureSpeed(float aperture) {
        this.apertureSpeed = aperture;
    }

    /**
     * Уголовое поле.
     *
     * @return
     */
    public float getAngularField() {
        return angularField;
    }

    /**
     * Угол поля зрения.
     *
     * @param arc
     */
    public void setAngularField(float arc) {
        this.angularField = arc;
    }

    /**
     * Фокусное расстояние.
     *
     * @return
     */
    public float getFocalLength() {
        return focalLength;
    }

    /**
     * Фокусное расстояние.
     *
     * @param focalLength
     */
    public void setFocalLength(float focalLength) {
        this.focalLength = focalLength;
    }

    /**
     * Видимый хроматический диапазон.
     *
     * @return
     */
    public float getSpectralRange() {
        return spectralRange;
    }

    public void setWaveLengths(float[] waveLengths) {
        this.waveLengths = waveLengths;
        this.spectralRange = Math.abs(waveLengths[1] - waveLengths[0]);
    }

    public float[] getWaveLengths() {
        return this.waveLengths;
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
     * Величина заднего фокального отрезка.
     *
     * @return
     */
    public float getBackFocalDistance() {
        return backFocalDistance;
    }

    /**
     * Величина заднего фокального отрезка.
     *
     * @param backFocalSegment
     */
    public void setBackFocalDistance(float backFocalSegment) {
        this.backFocalDistance = backFocalSegment;
    }

    /**
     * Положение входного зрачка.
     *
     * @return
     */
    public EntrancePupilPosition getEntrancePupilPosition() {
        return entrancePupilPosition;
    }

    /**
     * Положение входного зрачка.
     *
     * @param pupilPosition
     */
    public void setEntrancePupilPosition(
            EntrancePupilPosition pupilPosition) {
        this.entrancePupilPosition = pupilPosition;
    }
}
